package jade;

import org.lwjgl.BufferUtils;
import renderer.Shader;
import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main() {\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;


    private float[] vertexArray = {
            //position                 //colour
            0.5f, -0.5f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,  // top right
            -0.5f, 0.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f,  // top left
            0.5f, 0.5f, 0.0f,      0.0f, 0.0f, 1.0f, 1.0f,  // bottom right
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f,  // bottom left
    };

    // IMPORTANT MUst be in counter-clockwise order

    private int[] elementArray = {

            2,1,0, // top right triangle
            0,1,3 // botton left triangle

    };
    private int vaoID, vboID, eboID; //vertex array object, vertex buffer object and element buffer oject
    private Shader defaultShader;
    public LevelEditorScene() { // contsructor

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        // generate VAO, VBO, EBO buffer objects and send to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

// create a float buffer of vertices
        FloatBuffer vertexBuffer  = BufferUtils.createFloatBuffer(vertexArray.length); // create float buffer of vertex array length
        vertexBuffer.put(vertexArray).flip();
        // create VBO upload vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferData(GL_ARRAY_BUFFER,vertexBuffer,GL_STATIC_DRAW);

        //create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID );
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,elementBuffer, GL_STATIC_DRAW);
 // add vertex attribute pointer
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize+ colorSize)*floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize*floatSizeBytes );
        glEnableVertexAttribArray(1);


    }


    @Override

    public void update(float dt) { //transition from leveleditor scene to level scene
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
//
//        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
//        defaultShader.uploadMat4f("uView", camera.getViewMatrix());

        // bind the VAO that were using
        glBindVertexArray(vaoID);
        //enable the bertex attribute pointera
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES,elementArray.length, GL_UNSIGNED_INT, 0);
// unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
    defaultShader.detach();


    }


}


