package org.bnb.render;

import org.bnb.LWGClient;
import org.bnb.window.Window;
import org.joml.Matrix4f;

public class GameRenderer {

    private static GameRenderer instance;

    public static GameRenderer getInstance() {
        if (instance == null) instance = new GameRenderer();
        return instance;
    }

    private GameRenderer() {
        float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
        System.out.println(aspectRatio);
        // FIX ME! Projection matrix stopps the box from rendering!
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
    }

    private static final float FOV = (float) Math.toRadians(45.0f);
    private static final float Z_NEAR = 0.05f;
    private static final float Z_FAR = 1000.0f;
    private Matrix4f projectionMatrix;
    private final LWGClient client = LWGClient.getInstance();
    private final Window window = client.getWindow();

    public Matrix4f getProjectionMatrix() { return this.projectionMatrix; }

}
