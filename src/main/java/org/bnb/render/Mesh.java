package org.bnb.render;

import org.bnb.utils.LWGUtil;
import org.bnb.utils.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private final int VAO, VBO, vertexCount;
    private final ShaderProgram program;

    public Mesh(float[] positions, float[] colors, int[] indices) {
        FloatBuffer verticesBuffer = null;
        FloatBuffer colorBuffer = null;
        IntBuffer indicesBuffer = null;
        int indVBO, colorVBO;

        program = new ShaderProgram(LWGUtil.getResourceAsInputStream("game/shaders/DefaultMeshShader.vs"),
                LWGUtil.getResourceAsInputStream("game/shaders/DefaultMeshShader.fs"));

        try {
            verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
            vertexCount = indices.length;
            verticesBuffer.put(positions).flip();

            VAO = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(VAO);

            VBO = GL30.glGenBuffers();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBO);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);

            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
            GL30.glEnableVertexAttribArray(0);

            indVBO = GL30.glGenBuffers();
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, indVBO);
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL30.GL_STATIC_DRAW);
            MemoryUtil.memFree(indicesBuffer);

            colorVBO = GL30.glGenBuffers();
            colorBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorBuffer.put(colors).flip();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, colorVBO);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, colorBuffer, GL30.GL_STATIC_DRAW);
            MemoryUtil.memFree(colorBuffer);

            GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);
            GL30.glEnableVertexAttribArray(1);

            GL30.glBindVertexArray(0);

            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL30.glDeleteBuffers(VBO);
            GL30.glDeleteBuffers(indVBO);
            GL30.glDeleteBuffers(colorVBO);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    public int getVAO() { return this.VAO; }
    public int getVertexCount() { return this.vertexCount; }

    public void render() {
        try {
            program.use();

            GL30.glBindVertexArray(this.getVAO());
            GL30.glEnableVertexAttribArray(0);
            GL30.glEnableVertexAttribArray(1);

            GL30.glDrawElements(GL30.GL_TRIANGLES, this.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

            // Restore state
            GL30.glDisableVertexAttribArray(0);
            GL30.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);

            program.unBind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
