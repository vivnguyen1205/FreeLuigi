package renderer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {


    private int shaderProgramID;
    private boolean beingUsed = false;
    private String vertexSource;
    private String fragmentSource;
    private String filepath;
    public Shader(String filepath){
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");
            // find the first pattern after #type 'pattern'
            // Extract vertex and fragment shader sources


            int index = source.indexOf("#type") + 6; // finds first index of where "#type" appears in the file which should be 0 since it is the first string element + 6 to get to after #typpe
            // eol is end of line

            int eol = source.indexOf("\n",index); // gets the word immediately after "#type"
            if (eol == -1) {
            throw new IOException("Shader file malformed: missing newline after #type");
            }
            String firstPattern = source.substring(index, eol).trim(); // trim means get rid of spaces

            index = source.indexOf("#type",eol) + 6; // next #type

            eol = source.indexOf("\n", index); // gets the word immediately after "#type"
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];

            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected Token" + firstPattern );
            }
            // same thing for second pattern

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];

            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected Token" + secondPattern );
            }

        }catch(IOException e){ // when in java and opening file u always have to do try catch io exception
          e.printStackTrace();;
          assert false: "Error could not open file for shader:'" +  filepath + "'";
        }
        System.out.println(vertexSource);
        System.out.println(fragmentSource);




    }


    public void compile(){         // complile and link shaders

        int vertexID, fragmentID;
        if (vertexSource == null || fragmentSource == null) {
            throw new IllegalStateException("Shader source is not properly loaded.");
        }

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // pass the shaders cousse code into gpu
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);
// check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }
// FRAGMENT SHADER
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // pass the shaders cousse code into gpu
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);
// check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }
        // link shaders and check for errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID); // linking vertex and fragment shader program
        // check for linkingerrors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";

        }

    }
    public void use(){
        if(!beingUsed){
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }

    }
    public void detach(){
        glUseProgram(0);
        beingUsed = false;

    }
    public void upload(String varName, Matrix4f mat4){

        //uploads matrix into shader with name
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(15);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);


    }


    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matrixBuffer); // create1D array from our mat4
        glUniformMatrix4fv(varLocation, false, matrixBuffer);
    }
    public void uploadvec4f(String varName, Vector4f vec){
        int varLocation = glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform4f(varLocation,vec.x,vec.y,vec.z, vec.w);


    }

    public void uploadFloat(String varName, float val){
        int varLocation = glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1f(varLocation,val);

    }
    public void uploadInt(String varName, int val){
        int varLocation = glGetUniformLocation(shaderProgramID,varName);
        use();
        glUniform1i(varLocation,val);

    }
    public void uploadMat3(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }
    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }


//    public void uploadVec3f(String varName, Vector3f vec) {
//        int varLocation = glGetUniformLocation(shaderProgramID, varName);
//        use();
//        glUniform3f(varLocation, vec.x, vec.y, vec.z);
//    }
//
//    public void uploadVec2f(String varName, Vector2f vec) {
//        int varLocation = glGetUniformLocation(shaderProgramID, varName);
//        use();
//        glUniform2f(varLocation, vec.x, vec.y);
//    }


    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }



}
