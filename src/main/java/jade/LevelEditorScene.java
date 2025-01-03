package jade;

import jade.KeyListener;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

//import java.awt.event.KeyListener;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() { // contsructor

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));
        int xOffset = 10;
        int yOffset = 10;

        float  totalWidth  = (float)(600-xOffset*2);
        float totalHeight = (float)(300-yOffset*2);
        float sizeX = totalWidth/100.0f;
        float sizeY = totalHeight / 100.0f;
        float padding = 0;
        for(int x=0;x<100;x++){
            for(int y =0;y<100;y++){
                float xPos = xOffset + (x * sizeX) + (padding * x);
                float yPos = yOffset + (y * sizeY) + (padding * y);
// fix zIndex
                GameObject go = new GameObject(("Obj" + x + " " + y) ,new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)), 2);
                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObjectToScene(go);
            }
        }
       loadResources();
    }
    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }


    @Override

    public void update(float dt) { //transition from leveleditor scene to level scene
        if(KeyListener.isKeyPressed(GLFW_KEY_RIGHT)){
            camera.position.x += 100f * dt;
        }
        else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)){
        camera.position.x -= 100f * dt;

        }
        if(KeyListener.isKeyPressed(GLFW_KEY_UP)){
            camera.position.y += 100f * dt;


        }else if(KeyListener.isKeyPressed(GLFW_KEY_DOWN)){
            camera.position.y -=100f * dt;
        }
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();


    }
}


