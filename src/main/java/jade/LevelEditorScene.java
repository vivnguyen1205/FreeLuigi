package jade;


import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() { // contsructor

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        Spritesheet sprites =  AssetPool.getSpriteSheet("assets/images/spriteSheet.png");

      GameObject obj1 = new GameObject("object1",new Transform(new Vector2f(100,100), new Vector2f(250,256)));

      obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("object2",new Transform(new Vector2f(400,100), new Vector2f(250,256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(15)));
        this.addGameObjectToScene(obj2);


    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("asset/images/spriteSheet.png", new Spritesheet(AssetPool.getTexture("assets/imgaes/spritesheets.png"), 16, 16, 26, 0));
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


