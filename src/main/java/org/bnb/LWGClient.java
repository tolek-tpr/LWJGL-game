package org.bnb;

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

    public LWGClient() {
        gameWindow = new Window();
        gameWindow.run();
    }

    public Window getWindow() { return this.gameWindow; }



}
