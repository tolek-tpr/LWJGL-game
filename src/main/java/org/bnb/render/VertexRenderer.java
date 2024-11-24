package org.bnb.render;

import org.bnb.LWGClient;
import org.bnb.utils.LWGUtil;
import org.bnb.utils.ShaderProgram;
import org.bnb.utils.SharedConstants;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class VertexRenderer {

    private static VertexRenderer instance;

    private VertexRenderer() {
        float[] colors = LWGUtil.flattenFloatArray(consumer.getColors());

        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();

        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(colors.length);
        colorBuffer.put(colors).flip();

        int vertexVBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vertexVBO);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        MemoryUtil.memFree(vertexBuffer);

        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
        GL30.glEnableVertexAttribArray(0);

        int colorVBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, colorVBO);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);

        MemoryUtil.memFree(colorBuffer);

        GL30.glVertexAttribPointer(1, 4, GL30.GL_FLOAT, false, 0, 0);
        GL30.glEnableVertexAttribArray(1);
        // index -> layout (location = index) in the shader
        // size -> how many coordinates there are (1-4)
        // type -> specifies the type of data in transfer
        // normalized -> specifies if the data should be normalized
        // stride -> Specifies the byte offset between consecutive generic vertex attributes.
        // offset -> Specifies an offset to the first component in the buffer.

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        program = new ShaderProgram(LWGUtil.getResourceAsInputStream("game/shaders/DefaultConsumerShader.vs"),
                LWGUtil.getResourceAsInputStream("game/shaders/DefaultConsumerShader.fs"));
    }

    public static VertexRenderer getInstance() {
        if (instance == null) instance = new VertexRenderer();
        return instance;
    }

    private final LWGClient client = LWGClient.getInstance();
    private final int VAO;
    private final VertexConsumer consumer = client.getVertexConsumers();

    private final float[] vertices = LWGUtil.flattenFloatArray(consumer.getVertices());

    private final ShaderProgram program;

    public void renderFrame() {
        try {
            program.use();

            GL30.glBindVertexArray(VAO);
            GL30.glEnableVertexAttribArray(0);

            GL30.glDrawArrays(RenderType.getGlFunc(consumer.getRenderType()), 0, vertices.length);

            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);

            program.unbind();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
