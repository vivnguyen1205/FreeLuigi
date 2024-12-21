import jade.Window;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

public class Main {
    public static void main(String[] args){
        System.out.println("Debug: Starting Main.main()");


        Window window = Window.get();
        window.run();
    }
}
