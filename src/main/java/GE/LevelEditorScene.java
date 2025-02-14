package GE;

import util.Time;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{
    private boolean changingScene = false;
    private float TimeToChangeScene = 0.2f;

    public LevelEditorScene() {
        System.out.println("LevelEditorScene");
    }

    @Override
    public void update(float dt) {

        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if(changingScene && TimeToChangeScene > 0){
            TimeToChangeScene -= dt;
            Window.get().r -= dt*5.0f;
            Window.get().g -= dt*5.0f;
            Window.get().b -= dt*5.0f;
        }
        else if(changingScene){
            Window.changeScene(1);
        }
    }
}
