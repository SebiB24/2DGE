package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;
    private String vertexShaderSource;
    private String fragmentShaderSource;
    private String filePath;


    public Shader(String filePath){
        this.filePath = filePath;
        try{
            String source =Files.readString(Paths.get(filePath));
            String[] splitString = source.split("(#type)( )+([a-zA-z]+)");

            //Find the first pattern after the first #type
            int index = source.indexOf("#type") + 6;
            int endOfLine = source.indexOf("\n", index);
            String FirstType = source.substring(index, endOfLine).trim(); // type = vertex/fragment

            //Find the first pattern after the second #type
            index = source.indexOf("#type", endOfLine) + 6;
            endOfLine = source.indexOf("\n", index);
            String SecondType = source.substring(index, endOfLine).trim();

            if(FirstType.equals("vertex")){
                vertexShaderSource = splitString[1];
            }
            else if(FirstType.equals("fragment")){
                fragmentShaderSource = splitString[1];
            }
            else{
                throw new IOException("Unexpected token '" + FirstType + "'");
            }

            if(SecondType.equals("vertex")){
                vertexShaderSource = splitString[2];
            }
            else if(SecondType.equals("fragment")){
                fragmentShaderSource = splitString[2];
            }
            else{
                throw new IOException("Unexpected token '" + SecondType + "'");
            }

        }catch (IOException e){
            e.printStackTrace();
            assert false: "Error: Could not load shader file: " + filePath;
        }

    }

    public void compileAndLink(){

        int vertexID;
        int fragmentID;
        //======================================
        //Compile and link shaders
        //======================================

        //Load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        //Pass shader source to the GPU
        glShaderSource(vertexID, vertexShaderSource);

        //Compiling shader
        glCompileShader(vertexID);

        //Check for Errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tVertex shader compilation failed. ");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        //Load and compile the fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        //Pass shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSource);

        //Compiling shader
        glCompileShader(fragmentID);

        //Check for Errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tFragment shader compilation failed. ");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //Link shaders and check for Errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for linking error
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tLinking of shaders failed. ");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    public void use(){
        //Bind shader program
        glUseProgram(shaderProgramID);
    }

    public void detach(){
        glUseProgram(0);
    }
}
