package org.bnb.window;

import org.bnb.event.EventManager;
import org.bnb.event.KeyboardListener;
import org.bnb.render.Tesselator;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class Window {

    public static final double FPS = 60.0;

    private long window;
    private double width;
    private double height;


    public void run() {
        System.out.println("Starting GLFW window!");

        initGlfwWindow();
        loop();

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

        window = GLFW.glfwCreateWindow(300, 300, "Hello Window", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) throw new RuntimeException("Failed to create GLFW window!");

        GLFW.glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            this.handleInput(key, scanCode, mods);
        });
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            Window.this.width = width;
            Window.this.height = height;
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
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);
    }

    private void loop() {
        double millisPerUpdate = 1000.0D/FPS;
        double prev = System.currentTimeMillis();
        double steps = 0.0;

        GL.createCapabilities();
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
        GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        Tesselator t = new Tesselator();
        t.setVertex(0, 0, 0);
        t.setVertex(1, 0, 0);
        t.setVertex(1, 1, 0);
        t.setVertex(0, 1, 0);
        t.flush();

        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }
}
