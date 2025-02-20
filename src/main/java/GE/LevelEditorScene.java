package GE;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{

    private float[] vertexArray = {
       // position                 // color
       100.5f, 0.5f,   0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 0,//BR 0
       0.5f,   100.5f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,     0, 1,//TL 1
       100.5f, 100.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f,     1, 1,//TR 2
       0.5f,   0.5f,   0.0f,       1.0f, 1.0f, 0.0f, 1.0f,     0, 0 //BL 3
    };

    private int[] elementArray = {
      0,2,1, // Top right triangle
      0,1,3  // Bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;
    private Texture testTexture;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compileAndLink();

        this.testTexture = new Texture("assets/images/testImage.jpg");

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

        //==================================================
        //Add the vertex atribute pointers
        //=================================================
        int positionsSize = 3;
        int colorsSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorsSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorsSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorsSize) * Float.BYTES);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {

        camera.position.x -= dt * 50.0f;
        camera.position.y -= dt * 20.0f;

        defaultShader.use();

        //Upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0); //activate texture slot
        testTexture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        //Bind VAO that we are using
        glBindVertexArray(vaoID);

        //Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind everything
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
        defaultShader.detach();

    }
}
