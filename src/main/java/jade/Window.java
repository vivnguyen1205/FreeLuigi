package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    public float r,g,b,a;
    private static Window window = null; // private static window object
    private boolean fadeToBlack = false;
    private static Scene currentScene;
    private Window() { // constructor

        this.width = 1920; // giving standard hd definition
        this.height = 1080;
        this.title = "Luigi";
        r = 1;
        g=1;
        b=1;
        a=0;

    }
    public static void changeScene(int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:

                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                        break;
            default:
                assert false: "unknown scene" + newScene + ".";
                break;
        }
    }

    public static Window get() {// new method that returns  window

        if (Window.window == null){
            Window.window = new Window();

        }
        return Window.window;

    }
    public static Scene getScene(){
        return get().currentScene;
    }

    public void run() {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();



        // free memory once exit loop
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        //terminal glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free(); // freeing memory

    }
    public void init(){
        //setup error callback (GLFW with print to if error)

        GLFWErrorCallback.createPrint(System.err).set();

        //intialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to Initialize GLFW");


        }

        // configure GLFW
        glfwDefaultWindowHints(); // gives default window hints**
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        //create window
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);


        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL); // memory address where window  it is in the memory space
        if (glfwWindow == NULL){ // window wasn't created for some reason
            throw new IllegalStateException("failed to create the GLFW window ");
        }
        glfwSetCursorPosCallback(glfwWindow,MouseListener::mousePosCallBack); //:: shorthand for lambda expression
        glfwSetMouseButtonCallback(glfwWindow,MouseListener::mouseButtonCallBack);
        glfwSetScrollCallback(glfwWindow, MouseListener:: mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow,KeyListener:: keyCallBack);
        // make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync  - just swap it every frame, there is no wait time, go according to refresh rate of the monitor
        glfwSwapInterval(1);
        // make the window visible
        glfwShowWindow(glfwWindow); // long number pointer to our window
        GL.createCapabilities(); // important!!
        Window.changeScene(0);

    }
    public void loop(){
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;
        // loop through
        while(!glfwWindowShouldClose(glfwWindow)){
            // first poll events
            glfwPollEvents();
            glClearColor(r,g, b,a);

            glClear(GL_COLOR_BUFFER_BIT); // use color buffer bit and flush to entire screen

            if (dt>=0) {
                currentScene.update(dt);

            }

            glfwSwapBuffers(glfwWindow);
            endTime = (float)glfwGetTime(); // records when game ends end of loop
            dt = endTime - beginTime; // time of game
            beginTime = endTime; // new begin time

        }

    }

}
