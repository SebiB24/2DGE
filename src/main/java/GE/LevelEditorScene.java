package GE;

import org.lwjgl.BufferUtils;
import util.Time;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 Color;\n" +
            "\n" +
            "void main(){\n" +
            "    Color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
      // position             // color
       0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f, //BR 0
      -0.5f,  0.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f, //TL 1
       0.5f,  0.5f, 0.0f,      0.0f, 0.0f, 1.0f, 1.0f, //TR 2
      -0.5f, -0.5f, 0.0f,      1.0f, 1.0f, 0.0f, 1.0f, //BL 3
    };

    private int[] elementArray = {
      0,2,1, // Top right triangle
      0,1,3  // Bottom left triangle
    };

    private int vaoID, vboID, eboID;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        //======================================
        //Compile and link shaders
        //======================================

        //Load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        //Pass shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc);

        //Compiling shader
        glCompileShader(vertexID);

        //Check for Errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed. ");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        //Load and compile the fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        //Pass shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);

        //Compiling shader
        glCompileShader(fragmentID);

        //Check for Errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed. ");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //Link shaders and check for Errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking error
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking of shaders failed. ");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        //======================================
        //Generate VAO, VBO, EBO buffer objects, and send to GPU
        //======================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);  //Binding the created vao so that the next lines apply to it

        //Create a float buffer where we put the vertexArray
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        //Create VBO upload the vertexBuffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID); //Binding the created vbo so that the next lines apply to it
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW); //sends vertexBuffer to the id vboID
                                                                    //Static draw = information inside the buffer cant be changed

        //Create an int buffer where we put the elementArray
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        //Create EBO upload the elementBuffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Add the vertex atribute pointers
        int positionsSize = 3;
        int colorsSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorsSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorsSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {

        //Bind shader program
        glUseProgram(shaderProgram);

        //Bid VAO that we are using
        glBindVertexArray(vaoID);

        //Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind everything
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
        glUseProgram(0);

    }
}
