package jade;

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
    public static void mousePosCallBack(long window, double xPos, double yPos){
        get().lastX = get().xPos; // setting the last xpos to the current xpos (reseting value contsantcly)
        get().lastY = get().yPos;
        get().xPos = xPos; // setting the current xpos
        get().yPos = yPos;

    }
}
