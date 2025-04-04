package components;

import GE.Component;
import GE.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;

    private boolean isDirty;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = null;
        this.isDirty = true;
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
        this.isDirty = true;
    }

    @Override
    public void start(){
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if(!this.lastTransform.equals(this.gameObject.transform)){
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return color;
    }
    public Texture getTexture() {
        return sprite.getTexture();
    }
    public Vector2f[] getTexCoords() {
        return this.sprite.getTexCoords();
    }

    public void setColor(Vector4f color) {
        if(!this.color.equals(color)){
            this.color = color;
            this.isDirty = true;
        }
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public boolean isDirty(){
        return this.isDirty;
    }

    public void setClean(){
        this.isDirty = false;
    }
}
