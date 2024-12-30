package jade;


import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() { // contsructor

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
      GameObject obj1 = new GameObject("object1",new Transform(new Vector2f(100,100), new Vector2f(250,256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage.png")));
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("object2",new Transform(new Vector2f(400,100), new Vector2f(250,256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage2.png")));
        this.addGameObjectToScene(obj1);
      loadResources();

    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }


    @Override

    public void update(float dt) { //transition from leveleditor scene to level scene
System.out.println("fps"+ (1.0f/dt));
//        camera.position.x -= dt * 50.0f;
//        camera.position.y -= dt * 20.0f;


        for(GameObject go: this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();


    }


}


