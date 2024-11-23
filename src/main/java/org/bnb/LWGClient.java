package org.bnb;

import org.bnb.utils.LWGUtil;
import org.bnb.utils.ShaderProgram;
import org.bnb.window.Window;

public class LWGClient {

    // Singleton setup
    private static LWGClient instance;

    public static LWGClient getInstance() {
        if (instance == null) instance = new LWGClient();
        return instance;
    }

    // Actual code
    private final Window gameWindow;

    private LWGClient() {
        gameWindow = new Window();

//        ShaderProgram program = new ShaderProgram(LWGUtil.getResourceAsInputStream("game/shaders/ExampleShaderVertex.glsl"),
//                LWGUtil.getResourceAsInputStream("game/shaders/ExampleShaderFragment.glsl"));
//        program.use();
    }

    public Window getWindow() { return this.gameWindow; }

}
