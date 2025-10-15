package com.hollowstring.airengine.scene;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;

import com.hollowstring.airengine.camera.Camera;
import com.hollowstring.airengine.lightSource.LightSource;
import com.hollowstring.airengine.object.Object;

public class Scene {
    private float cR, cG, cB;
    private Camera activeCamera;
    private ArrayList<Object> objectPool;
    private ArrayList<LightSource> lightPool;

    public Scene(){
        objectPool = new ArrayList<>();
        lightPool = new ArrayList<>();
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void setActiveCamera(Camera activeCamera) {
        this.activeCamera = activeCamera;
    }

    public void appendObject(Object obj){
        objectPool.add(obj);
    }
    public void appendLightSource(LightSource l){
        lightPool.add(l);
    }
    public void setClearColor(float cR, float cG, float cB){
        this.cR = cR;
        this.cG = cG;
        this.cB = cB;
    }
    public void processObjects(){
        for(int i = 0; i < objectPool.size(); i++){
            if(objectPool.get(i) == null){
                break;
            }
            if(objectPool.get(i).isHidden){
                continue;
            }
            objectPool.get(i)._process();
        }
    }
    public void render(){
        GL11.glClearColor(cR, cG, cB, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        for(int i = 0; i < objectPool.size(); i++){
            Object obj = objectPool.get(i);
            if(obj == null){
                break;
            }
            if(obj.isHidden){
                continue;
            }
            GL20.glUseProgram(obj.getMaterial().getShaderProgram());
            if(obj.getMaterial().getTexture().length != 0){
                for(int j = 0; j < obj.getMaterial().getTexture().length; j++){
                    if(obj.getMaterial().getTexture()[j] == 0){
                        break;
                    }
                    GL13.glActiveTexture(GL13.GL_TEXTURE0+j);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, obj.getMaterial().getTexture()[j]);
                    GL20.glUniform1i(GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), obj.getMaterial().getTextureIds()[j]), j);
                }
            }
            for(int j = 0; j < lightPool.size(); j++){
                lightPool.get(j).applyLighting(obj);
            }
            Matrix4f objectTranslation = new Matrix4f().identity();
            objectTranslation.translate(obj.position);
            objectTranslation.rotateX((float)Math.toRadians(obj.rotation.x));
            objectTranslation.rotateY((float)Math.toRadians(obj.rotation.y));
            objectTranslation.rotateZ((float)Math.toRadians(obj.rotation.z));
            objectTranslation.scale(obj.scale);
            Matrix4f cameraTranslation = new Matrix4f().identity();
            cameraTranslation.lookAt(activeCamera.getPosition(), activeCamera.getTarget(), new Vector3f(0.0f, 1.0f, 0.0f));
            int transformLocation = GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "transform");
            int cameraTransformLocation = GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "cameraTransform");
            int projectionLocation = GL20.glGetUniformLocation(obj.getMaterial().getShaderProgram(), "projection");
            GL20.glUniformMatrix4fv(transformLocation, false, objectTranslation.get(new float[16]));
            GL20.glUniformMatrix4fv(cameraTransformLocation, false, cameraTranslation.get(new float[16]));
            GL20.glUniformMatrix4fv(projectionLocation, false, activeCamera.getProjection().get(new float[16]));
            GL30.glBindVertexArray(obj.getVAO());
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, obj.getMesh().length / 8);
        }
    }

}
