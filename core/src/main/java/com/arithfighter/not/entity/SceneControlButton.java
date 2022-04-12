package com.arithfighter.not.entity;

import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;

public class SceneControlButton{
    private enum SceneChange {NEUTRAL, READY, START}
    private SceneChange sceneChange = SceneChange.NEUTRAL;
    private final Button button;

    public SceneControlButton(Texture texture, float scale){
        button = new Button(texture, scale);
    }

    public Button getButton(){
        return button;
    }

    public void handleScene() {
        if (button.isActive())
            sceneChange = SceneChange.READY;
        else {
            if (sceneChange == SceneChange.READY)
                sceneChange = SceneChange.START;
        }
    }

    public boolean isStart(){
        return sceneChange == SceneChange.START;
    }

    public void init(){
        sceneChange = SceneChange.NEUTRAL;
    }
}
