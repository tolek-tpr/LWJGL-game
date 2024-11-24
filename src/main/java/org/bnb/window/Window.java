package org.bnb.window;

import org.bnb.event.EventManager;
import org.bnb.event.KeyboardListener;
import org.bnb.event.WindowListener;
import org.bnb.render.LWGObject;
import org.bnb.render.Mesh;
import org.bnb.render.VertexRenderer;
import org.bnb.utils.RenderUtils;
import org.bnb.utils.SharedConstants;
import org.joml.Matrix4f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;
import java.util.ArrayList;

public class Window {

    public static final double FPS = SharedConstants.DEFAULT_FPS;

    private long window;
    private double width = SharedConstants.DEFAULT_WINDOW_WIDTH;
    private double height = SharedConstants.DEFAULT_WINDOW_HEIGHT;

    private final ArrayList<LWGObject> drawableObjects = new ArrayList<>();
    private final Matrix4f projectionMatrix = RenderUtils.getProjectionMatrix(SharedConstants.DEFAULT_FOV, SharedConstants.DEFAULT_WINDOW_WIDTH, SharedConstants.DEFAULT_WINDOW_HEIGHT,
            SharedConstants.DEFAULT_Z_NEAR, SharedConstants.DEFAULT_Z_FAR);

    public void run() {
        System.out.println("Starting GLFW window!");

        try {
            initGlfwWindow();
            loop();
        } catch(Exception e) {
            e.printStackTrace();
        }

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public long getHandle() { return this.window; }

    private void initGlfwWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL20.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL20.GL_TRUE);

        window = GLFW.glfwCreateWindow(SharedConstants.DEFAULT_WINDOW_WIDTH, SharedConstants.DEFAULT_WINDOW_HEIGHT, "Hello Window", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) throw new RuntimeException("Failed to create GLFW window!");

        GLFW.glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> this.handleInput(key, scanCode, mods));
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.this.width = width;
            Window.this.height = height;
            GL30.glViewport(0, 0, width, height);
            EventManager.getInstance().fire(new WindowListener.WindowResizeEvent(this.getHandle(), width, height));
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            GLFW.glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        float[] positions = new float[]{
                -0.5f,  0.5f, -5.05f,
                -0.5f, -0.5f, -5.05f,
                0.5f, -0.5f, -5.05f,
                0.5f,  0.5f, -5.05f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };
        Mesh m = new Mesh(positions, colors, indices);
        LWGObject obj = new LWGObject(m);
        obj.setPosition(0.3F, 0.1F, 0.5F);
        obj.setRotation(0, 0, 20F);
        this.addDrawable(obj);
    }

    private void loop() {
        double millisPerUpdate = 1000.0D/FPS;
        double prev = System.currentTimeMillis();
        double steps = 0.0;

        GL20.glClearColor(0.1f, 0.5f, 0.5f, 1.0f);

        //Game Loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            double loopStartTime = System.currentTimeMillis();
            double elapsed = loopStartTime-prev;
            prev = System.currentTimeMillis();
            steps += elapsed;

            while (steps >= millisPerUpdate){
                update();
                steps-=millisPerUpdate;
            }
            render();
        }
    }

    //Input Listener Stuff
    private void handleInput(int keyCode, int scanCode, int mods){
        EventManager.getInstance().fire(new KeyboardListener.KeyboardEvent(keyCode, scanCode, mods));
    }

    //Update the game state
    private void update(){

    }

    //Rendering
    private void render(){
        try {
            GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        /*Tessellator t = new Tessellator();
        t.setColor(0, 1,0);
        t.setVertex(0, 0, 0);
        t.setColor(1, 0,0);
        t.setVertex(0.25f, 0, 0);
        t.setColor(1, 1,0);
        t.setVertex(0.5f, 0.5f, 0);
        t.setColor(1, 0, 1);
        t.setVertex(0.75f, 0.25f, 0);
        t.flush();*/

            VertexRenderer.getInstance().renderFrame();
            drawableObjects.forEach(object -> object.render(projectionMatrix));

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LWGObject> addDrawable(LWGObject m) {
        this.drawableObjects.add(m);
        return this.drawableObjects;
    }

    public ArrayList<LWGObject> getDrawableObjects() { return this.drawableObjects; }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }

}
