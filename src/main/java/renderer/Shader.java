package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader {

    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private String filepath;
    public Shader(String filepath){
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");
// find the first pattern after #type 'pattern'
            int index = source.indexOf("#type") + 6; // finds first index of where "#type" appears in the file which should be 0 since it is the first string element + 6 to get to after #typpe
            // eol is end of line
            int eol = source.indexOf("\n" + index); // gets the word immediately after "#type"
            String firstPattern = source.substring(index, eol).trim(); // trim means get rid of spaces
            index = source.indexOf("#type", eol); // next #type
            eol = source.indexOf("\n" + index); // gets the word immediately after "#type"
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
                vertexSource = splitString[1];

            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected Token" +secondPattern );
            }
        }catch(IOException e){ // when in java and opening file u always have to do try catch io exception
          e.printStackTrace();;
          assert false: "Error could not open file for shader:'" +  filepath + "'";
        }
        System.out.println(vertexSource);
        System.out.println(fragmentSource);




    }
    public void compile(){

    }
    public void use(){

    }
    public void detach(){

    }

}
