package jade;

import java.security.Key;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;


public class KeyListener {// singleton pattern
    private static KeyListener instance; // instance key listener
    public boolean keyPressed[] = new boolean[350];

    private KeyListener(){

    }
    public static KeyListener get(){
        if(KeyListener.instance == null){
            KeyListener.instance = new KeyListener();

        }
        return KeyListener.instance;
    }
    public static void keyCallBack(long window, int key,  int scancode, int action, int mods){
        if(action == GLFW_PRESS){ // key is being pressed
            get().keyPressed[key]=true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false; // key is released

        }


    }
    public static boolean isKeyPressed(int keyCode){
        if(keyCode< get().keyPressed.length){
            return get().keyPressed[keyCode];

        }
        else{
            return false;
        }

    }
}
