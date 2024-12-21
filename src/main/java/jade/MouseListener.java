package jade;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3]; // boolean array which button was last pressed, initialize to 3
    private boolean isDragging; // is mouse dragging

    private MouseListener(){
        this.scrollX = 0.0; // initizaliing mouse position cvb
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX= 0.0;
        this.lastY = 0.0;


    }
    public static MouseListener get(){
        if (MouseListener.instance == null ){ // if instance is null (will be first time) set everything to the values in mouselistener
         instance = new MouseListener();


        }
        return MouseListener.instance;


    }
    public static void mousePosCallBack(long window, double xPos, double yPos){ // MOUSE POSITION UPDATING
        get().lastX = get().xPos; // setting the last xpos to the current xpos (reseting value contsantcly)
        get().lastY = get().yPos;
        get().xPos = xPos; // setting the current xpos
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2]; // if any of the buttons are pressed and the mouse has only moved, then it is dragging

    }
    public static void mouseButtonCallBack(long window, int button, int action, int mods){// mouse button clicking
    if(action == GLFW_PRESS){
        if (button < get().mouseButtonPressed.length){
            get().mouseButtonPressed[button] = true; // button press = true


        }

    } else if (action == GLFW_RELEASE) {
        if (button < get().mouseButtonPressed.length) {

            get().mouseButtonPressed[button] = false; // button press = false
            get().isDragging = false; // no longer dragging
        }
    }

    }
    public static void mouseScrollCallback(long window, double xOffSet, double yOffset){
       get().scrollX = xOffSet;
       get().scrollY = yOffset;

    }
    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }
    public static float getX(){ // getter function
        return(float)get().xPos;
    }
    public static float getY(){ // getter function
        return(float)get().yPos;
    }
    public static float getDx(){ // getter function
        return(float)(get().lastX - get().xPos);

    }
    public static float getDy(){ // getter function
        return(float)(get().lastY - get().yPos);

    }
    public static float getScrollX(){ // getter function
        return (float)get().scrollX;

    }
    public static float getScrollY(){ // getter function
        return (float)get().scrollY;

    }
    public static boolean isDragging(){
        return get().isDragging;
    }
    public static boolean mouseButtonDown(int button){
        if(button< get().mouseButtonPressed.length){
            return get().mouseButtonPressed[button];
        }
        else{
            return false;
        }


    }
}
