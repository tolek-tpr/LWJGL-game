package org.bnb.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Tesselator {

    private int vertexCount = 0;

    private float[] vertices = new float[12];

    private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(12);

    public void flush(){
        vertexBuffer.clear();
        vertexBuffer.put(vertices);
        vertexBuffer.flip();

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glInterleavedArrays(GL11.GL_V3F, 0, vertexBuffer);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertexCount);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        clear();
    }

    private void clear(){
        vertexBuffer.clear();
        vertices = new float[12];
        vertexCount = 0;
    }

    public void setVertex(float x, float y, float z){
        vertices[vertexCount] = x;
        vertexCount++;
        vertices[vertexCount] = y;
        vertexCount++;
        vertices[vertexCount] = z;
        vertexCount++;
    }
}
