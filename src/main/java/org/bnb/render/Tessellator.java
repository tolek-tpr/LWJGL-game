package org.bnb.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Tessellator {

    private boolean enableColor = false;

    private int vertexCount = 0;

    private float[] vertices = new float[100000];

    private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(100000);

    public void flush(){
        vertexBuffer.clear();
        vertexBuffer.put(vertices);
        vertexBuffer.flip();

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        if (enableColor)
            GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

        if (enableColor)
            GL11.glInterleavedArrays(GL11.GL_C3F_V3F, 0, vertexBuffer);
        else
            GL11.glInterleavedArrays(GL11.GL_V3F, 0, vertexBuffer);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertexCount);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        if (enableColor)
            GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        clear();
    }

    private void clear(){
        vertexBuffer.clear();
        vertices = new float[12];
        vertexCount = 0;
        enableColor = false;
    }

    public void setColor(float r, float g, float b){
        enableColor = true;
        vertices[vertexCount] = r;
        vertexCount++;
        vertices[vertexCount] = g;
        vertexCount++;
        vertices[vertexCount] = b;
        vertexCount++;
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
