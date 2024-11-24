package org.bnb.render;

import org.bnb.LWGClient;
import org.bnb.window.Window;
import org.joml.Matrix4f;

import static org.bnb.utils.SharedConstants.*;

@Deprecated
public class GameRenderer {

    private static GameRenderer instance;

    public static GameRenderer getInstance() {
        if (instance == null) instance = new GameRenderer();
        return instance;
    }

    private final Matrix4f projectionMatrix;

    private GameRenderer() {
        LWGClient client = LWGClient.getInstance();
        Window window = client.getWindow();

        float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
        projectionMatrix = new Matrix4f();
        projectionMatrix.perspective(DEFAULT_FOV, aspectRatio, DEFAULT_Z_NEAR, DEFAULT_Z_FAR);
    }

    public Matrix4f getProjectionMatrix() { return this.projectionMatrix; }

}
