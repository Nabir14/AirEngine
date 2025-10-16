package com.hollowstring.airengine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PerspectiveCamera implements Camera{
    public Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
    public Vector3f direction = new Vector3f(0.0f, 0.0f, -1.0f);
    public Matrix4f projection = new Matrix4f();
    public PerspectiveCamera(float FOV, int width, int height, float Near, float RenderDistance){
        projection = new Matrix4f().perspective((float) Math.toRadians(FOV), (float) width / height, Near, RenderDistance);
    }
    public Vector3f getRotation(){
        return this.rotation;
    }
    public Matrix4f getProjection(){
        return this.projection;
    }
    public Vector3f getTarget()
    {
        direction.x = (float) ((Math.cos(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x))));
        direction.y = (float) Math.sin(Math.toRadians(rotation.x));
        direction.z = (float) ((Math.sin(Math.toRadians(rotation.y)) * Math.cos(Math.toRadians(rotation.x))));
        return (new Vector3f().add(position).add(direction.normalize()));
    }
    public Vector3f getPosition(){
        return position;
    }
    public void setPosition(float x, float y, float z)
    {
        this.position = new Vector3f(x, y, z);
    }
    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }
}
