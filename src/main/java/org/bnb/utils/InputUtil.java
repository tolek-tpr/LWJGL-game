package org.bnb.utils;

import org.lwjgl.glfw.GLFW;

public class InputUtil {

    public static boolean isKeyPressed(long window, int keyCode) {
        return GLFW.glfwGetKey(window, keyCode) == GLFW.GLFW_PRESS;
    }

}
