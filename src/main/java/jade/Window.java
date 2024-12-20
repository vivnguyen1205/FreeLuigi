package jade;

import org.lwjgl.Version;
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
    private static Window window = null; // private static window object

    private Window() { // constructor
        this.width = 1920; // giving standard hd definition
        this.height = 1080;
        this.title = "Mario";

    }

    public static Window get() {// new method that returns  window

        if (Window.window == null){
            Window.window = new Window();

        }
        return Window.window;

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
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL); // memory address where window  it is in the memory space
        if (glfwWindow == NULL){ // window wasnt created for some reason
            throw new IllegalStateException("failed to create the GLFW window ");


        }

        // make the OpenGL context current

        glfwMakeContextCurrent(glfwWindow);

        // enable v-sync  - just swap it every frame, there is no wait time, go according to refresh rate of the monitor
        glfwSwapInterval(1);
        // make the window visible
        glfwShowWindow(glfwWindow); // long number pointer to our window

        GL.createCapabilities(); // important!!

    }
    public void loop(){
        // loop through
        while(!glfwWindowShouldClose(glfwWindow)){
            // first poll events
            glfwPollEvents();
            glClearColor(1.0f,1.0f,1.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT); // use color buffer bit and flush to entire screen


            glfwSwapBuffers(glfwWindow);

        }

    }

}
