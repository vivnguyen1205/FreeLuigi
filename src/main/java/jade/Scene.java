package jade;

import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene() {
    }

    public void init() {


    }
    public void start() {    //start game object, check if game is running  , if not then do something
        // when game started, start game objects
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);

        }
        isRunning = true;
    }
    public void addGameObjectToScene(GameObject go){
        if(!isRunning){
            gameObjects.add(go);

        }else{ // when running, add game obejcts to list
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }

    }

    public abstract void update(float dt); // updates time dt

    public Camera camera() {
        return this.camera;
    }







}
