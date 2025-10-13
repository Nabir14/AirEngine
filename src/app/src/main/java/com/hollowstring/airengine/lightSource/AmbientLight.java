package com.hollowstring.airengine.lightSource;

import com.hollowstring.airengine.object.Object;
import org.lwjgl.opengl.*;

public class AmbientLight implements LightSource {
    public float r, g, b;
    public float lightEnergy;
    public AmbientLight(float r, float g, float b, float lightEnergy){
        this.r = r;
        this.g = g;
        this.b = b;
        this.lightEnergy = lightEnergy;
    }
    public void applyLighting(Object obj){
        GL20.glUseProgram(obj.getMaterial().getShaderProgram());
        GL20.glUniform3f(GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "ambientLightColor"), r * lightEnergy, g * lightEnergy, b * lightEnergy);
    }
}
