package jade;


import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene {
    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() { // contsructor

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
         sprites =  AssetPool.getSpriteSheet("assets/images/spriteSheet.png");

         obj1 = new GameObject("object1",new Transform(new Vector2f(200,100), new Vector2f(250,256)), 2);

      obj1.addComponent(new SpriteRenderer(new Sprite(
              AssetPool.getTexture("assets/images/blendImage1.png")
      )));
        this.addGameObjectToScene(obj1);
        GameObject obj2 = new GameObject("object2",
                new Transform(new Vector2f(400,100), new Vector2f(250,256)), 1);
        obj2.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/blendImage2.png"))));
        this.addGameObjectToScene(obj2);


    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("asset/images/spriteSheet.png", new Spritesheet(AssetPool.getTexture("assets/imgaes/spritesheets.png"), 16, 16, 26, 0));
    }



    @Override

    public void update(float dt) { //transition from leveleditor scene to level scene

        for(GameObject go: this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();


    }


}


