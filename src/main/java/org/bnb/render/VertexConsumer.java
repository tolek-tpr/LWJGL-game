package org.bnb.render;

import org.bnb.utils.LWGUtil;

public class VertexConsumer {

    float[][] vertices = {};
    float[][] colors = {};
    float[][] textureMappings = {};

    private RenderType renderType = RenderType.TRIANGLES;

    public VertexConsumer vertex(float x, float y, float z) {
        vertices = LWGUtil.addFloatEntry(vertices, new float[]{ x, y, z });
        return this;
    }

    public VertexConsumer color(float r, float g, float b, float a) {
        colors = LWGUtil.addFloatEntry(colors, new float[]{ r, g, b, a });
        return this;
    }

    public VertexConsumer texture(int u1, int u2, int v1, int v2) {
        textureMappings = LWGUtil.addFloatEntry(textureMappings, new float[]{ u1, u2, v1, v2 });
        return this;
    }

    public float[][] getVertices() { return this.vertices; }
    public float[][] getColors() { return this.colors; }
    public float[][] getTextureMappings() { return this.textureMappings; }
    public RenderType getRenderType() { return this.renderType; }

    public VertexConsumer setRenderType(RenderType renderType) {
        this.renderType = renderType;
        return this;
    }

}
