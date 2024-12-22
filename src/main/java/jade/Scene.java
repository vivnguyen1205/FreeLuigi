package jade;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    private  boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    public Scene(){



    }
    public void init(){

    }
    public abstract void update(float dt); // updates time dt
    public void start(){    //start game object, check if game is running  , if not then do something
        // when game started, start game objects
        for (GameObject go : gameObjects){
            go.start();

        }
        isRunning = true;
    }
    public void addGameObjectToScene(GameObject go){
        if(!isRunning){
            gameObjects.add(go);

        }else{ // when rinning, add game obejcts to list
            gameObjects.add(go);
            go.start();
        }

    }

}
