package com.hollowstring.airengine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface Camera {
    public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector3f direction = new Vector3f(0.0f, 0.0f, -1.0f);
    public Matrix4f projection = new Matrix4f();
    public Vector3f getTarget();
    public Vector3f getRotation();
    public Matrix4f getProjection();
    public Vector3f getPosition();
    public void setPosition(float x, float y, float z);
}
