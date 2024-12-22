package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    private String filepath;
    private int textID;
    public Texture(String filepath){

        this.filepath = filepath;
        //generate texture on GPU
        textID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textID);
        //now set texture parameters (how we want it to act)
        // first we want it to repeat image in both directions if uvcoord greater than etxuture width

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
    // when stretching the image, pixilate
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    // when shrinking, also pixilate
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

    //load image by getting RGB data
    IntBuffer width  = BufferUtils.createIntBuffer(1);
    IntBuffer height  = BufferUtils.createIntBuffer(1);
    IntBuffer channels = BufferUtils.createIntBuffer(1);

    ByteBuffer image = stbi_load(filepath, width, height, channels, 0);
    if(image !=null){
        if(channels.get(0)==3){
            //uploads pixels to the GPU - creates buffer of size width*height and puts image data in

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        }
        else if(channels.get(0)==4){
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        }
        else{
            assert false: "error: (Texture) unknown numeber of channels "+ channels.get(0);
        }
        }
    else{
        assert false: "Error: (texture) - could not load image"+ filepath;

    }
    // frees memory that stb has allocated to the image to avoid memory leak
    stbi_image_free(image);


    }


    //bind and unbind textures

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, textID);

    }
    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }



}
