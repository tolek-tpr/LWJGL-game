package org.bnb.events;

import org.bnb.LWGClient;
import org.bnb.Main;
import org.bnb.event.EventImpl;
import org.bnb.event.EventManager;
import org.bnb.event.KeyboardListener;
import org.bnb.utils.InputUtil;
import org.bnb.window.Window;
import org.lwjgl.glfw.GLFW;

public class Keyboard extends EventImpl implements KeyboardListener {

    @Override
    public void onEnable() {
        EventManager.getInstance().add(KeyboardListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(KeyboardListener.class, this);
    }

    @Override
    public void onKey(int keyCode, int scanCode, int mods) {
        Window window = LWGClient.getInstance().getWindow();
        boolean pressed = InputUtil.isKeyPressed(window.getHandle(), keyCode);

        if (pressed && keyCode == GLFW.GLFW_KEY_ESCAPE) {
            GLFW.glfwSetWindowShouldClose(window.getHandle(), true);
        }
    }

}
