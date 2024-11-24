package org.bnb.window;

import org.bnb.event.EventManager;
import org.bnb.event.KeyboardListener;
import org.bnb.render.Mesh;
import org.bnb.render.Renderer;
import org.bnb.utils.LWGUtil;
import org.bnb.utils.ShaderProgram;
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

    public static final double FPS = 60.0;

    private long window;
    private double width;
    private double height;

    private final ArrayList<Mesh> meshes = new ArrayList<>();

    public void run() {
        System.out.println("Starting GLFW window!");

        try {
            initGlfwWindow();
            loop();
        } catch(Exception e) {}

        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public long getHandle() { return this.window; }

    private void initGlfwWindow() throws Exception {

        //program.use();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL20.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL20.GL_TRUE);

        window = GLFW.glfwCreateWindow(700, 600, "Hello Window", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) throw new RuntimeException("Failed to create GLFW window!");

        GLFW.glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            this.handleInput(key, scanCode, mods);
        });
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.this.width = width;
            Window.this.height = height;
            GL30.glViewport(0, 0, width, height);
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
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f,
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
        this.addMesh(new Mesh(positions, colors, indices));
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

            Renderer.getInstance().renderFrame();
            meshes.forEach(Mesh::render);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Mesh> addMesh(Mesh m) {
        this.meshes.add(m);
        return this.meshes;
    }

    public ArrayList<Mesh> getMeshes() { return this.meshes; }

}
