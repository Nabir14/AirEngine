package com.hollowstring.airengine.lightSource;

import com.hollowstring.airengine.object.Object;

import org.joml.Vector3f;
import org.lwjgl.opengl.*;

public class PointLight implements LightSourcew{
    public Vector3f position;
    public float r, g, b;
    public float lightEnergy;
    public PointLight(float r, float g, float b, float lightEnergy){
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.r = r;
        this.g = g;
        this.b = b;
        this.lightEnergy = lightEnergy;
    }
    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    public void applyLighting(Object obj){
        GL20.glUseProgram(obj.getMaterial().getShaderProgram());
        GL20.glUniform3f(GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "pointLightColor"), r * lightEnergy, g * lightEnergy, b * lightEnergy);
        GL20.glUniform3f(GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "pointLightPos"), position.x, position.y, position.z);
    }
}
