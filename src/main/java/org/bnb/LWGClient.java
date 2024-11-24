package org.bnb;

import org.bnb.render.Mesh;
import org.bnb.render.RenderType;
import org.bnb.render.VertexConsumer;
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
    private final VertexConsumer vertexConsumer;

    private LWGClient() {
        this.vertexConsumer = new VertexConsumer();
        gameWindow = new Window();

//        vertexConsumer.setRenderType(RenderType.TRIANGLES);
//
//        vertexConsumer.vertex(1f, -1f, 0.0f)  .color(1, 1, 1, 1);
//        vertexConsumer.vertex(-1f, -1f, 0.0f) .color(1, 1, 0, 1);
//        vertexConsumer.vertex(-1f, 1f, 0.0f)  .color(1, 0, 1, 1);
    }

    public Window getWindow() { return this.gameWindow; }
    public VertexConsumer getVertexConsumers() { return this.vertexConsumer; }

}
