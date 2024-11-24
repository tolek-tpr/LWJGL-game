package org.bnb.render;

import org.bnb.utils.RenderUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class LWGObject {

    private final Mesh mesh;

    private final Vector3f position;

    private float scale;

    private final Vector3f rotation;

    public LWGObject(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public void render(Matrix4f projectionMatrix) {
        Matrix4f worldMatrix =
                RenderUtils.getWorldMatrix(
                        this.getPosition(),
                        this.getRotation(),
                        this.getScale());

        this.mesh.render(projectionMatrix, worldMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Mesh getMesh() {
        return mesh;
    }

}