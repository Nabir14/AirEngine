import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;

import com.hollowstring.airengine.*;
import com.hollowstring.airengine.camera.*;
import com.hollowstring.airengine.lightSource.*;
import com.hollowstring.airengine.scene.*;
import com.hollowstring.airengine.texture.*;
import com.hollowstring.airengine.object.Object;
import com.hollowstring.airengine.material.*;
import com.hollowstring.airengine.mesh.Mesh;

public class AppTest {
    public static void main(String[] args) {
        System.out.println(AirEngine.getVersion());

        AirEngine engine = new AirEngine(800, 600, "Airdrop");
        engine.createWindow();

        Scene scene = new Scene();
        PerspectiveCamera camera = new PerspectiveCamera(70, 800, 600, 0.1f, 1000.0f);
        camera.setPosition(-3.0f, 3.0f, 0.0f);

        //OrthographicCamera camera = new OrthographicCamera(16, 0.01f, 1000.0f);
        //camera.setRotation(-15.0f, -45.0f, 0.0f);
        //camera.setPosition(-16.0f, -3.0f, 4.5f);

        scene.setActiveCamera(camera);

        AmbientLight ambientLight = new AmbientLight(0.3f, 0.5f, 0.7f, 1.0f);
        PointLight pointLight = new PointLight(1.0f, 1.0f, 1.0f, 1.0f);

        Texture grassTexture = new Texture(GL11.GL_REPEAT, GL11.GL_LINEAR_MIPMAP_LINEAR, GL11.GL_LINEAR);
        grassTexture.loadTexture("others/grass.jpg");
        Texture brickTexture = new Texture(GL11.GL_REPEAT, GL11.GL_LINEAR_MIPMAP_LINEAR, GL11.GL_LINEAR);
        brickTexture.loadTexture("others/bricks.png");

        Material mat = new Material();
        mat.setTexture(grassTexture, "defaultTexture");
        Material mat2 = new Material();
        mat2.setTexture(brickTexture, "defaultTexture");
        
        Object obj = new Object(Mesh.Plane, mat);
        obj.setSize(64.0f, 1.0f, 64.0f);
        Object brick = new Object(Mesh.Cube, mat2);
        brick.setPosition(0.0f,1.0f, 0.0f);
        pointLight.setPosition(0.0f, 2.0f, 0.0f);
        
        scene.appendObject(obj);
        scene.appendObject(brick);
        scene.appendLightSource(ambientLight);
        scene.appendLightSource(pointLight);
        scene.processObjects();
        
        mat.setUniformValue("textureUVTile", 32.0f);
        //brick.setHidden(true);
        mat.setUniformValue("specularStrength", 0.5f);

        boolean run = true;
        while(run){
            scene.setClearColor(0.3f, 0.5f, 0.7f);
            mat2.setUniformValue("camPos", camera.position.x, camera.position.y, camera.position.z);
            scene.render();
            engine.processDefault();
            if(engine.checkKey(GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_PRESS) || GLFW.glfwWindowShouldClose(engine.getWindow())){
                run = false;
            }else if(engine.checkKey(GLFW.GLFW_KEY_X, GLFW.GLFW_PRESS)){
                engine.setDrawMode(GL11.GL_LINE);
            }else if(engine.checkKey(GLFW.GLFW_KEY_F, GLFW.GLFW_PRESS)){
                engine.setDrawMode(GL11.GL_FILL);
            }else if(engine.checkKey(GLFW.GLFW_KEY_W, GLFW.GLFW_PRESS)){
                camera.position.add(camera.direction.normalize().mul(0.1f));
            }else if(engine.checkKey(GLFW.GLFW_KEY_D, GLFW.GLFW_PRESS)){
                camera.position.sub(camera.direction.normalize().mul(0.1f));
            }else if(engine.checkKey(GLFW.GLFW_KEY_LEFT, GLFW.GLFW_PRESS)){
                camera.rotation.y -= 1.0f;
            }else if(engine.checkKey(GLFW.GLFW_KEY_RIGHT, GLFW.GLFW_PRESS)){
                camera.rotation.y += 1.0f;
            }else if(engine.checkKey(GLFW.GLFW_KEY_UP, GLFW.GLFW_PRESS)){
                camera.rotation.x += 1.0f;
            }else if(engine.checkKey(GLFW.GLFW_KEY_DOWN, GLFW.GLFW_PRESS)){
                camera.rotation.x -= 1.0f;
            }
        }
        engine.close();
    }
}
