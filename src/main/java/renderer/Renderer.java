package renderer;

import GE.GameObject;
import components.SpriteRenderer;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<RenderBatch>();
    }

    public void add(GameObject go){
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if(spr != null){
            add(spr);
        }
    }

    public void add(SpriteRenderer sprite){
        boolean added = false;
        for(RenderBatch batch : batches){
            if(batch.hasRoom()){
                Texture tex = sprite.getTexture();
                if(tex == null || batch.hasTexture(tex) || batch.hasTextureRoom()){
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }
        if(!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    public void render(){
        for(RenderBatch batch : batches){
            batch.render();
        }
    }
}
