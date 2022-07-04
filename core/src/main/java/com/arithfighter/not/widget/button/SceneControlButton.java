package com.arithfighter.not.widget.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SceneControlButton{
    private enum SceneChange {NEUTRAL, READY, START}
    private SceneChange sceneChange = SceneChange.NEUTRAL;
    private final Button button;

    public SceneControlButton(TextureRegion texture, float scale){
        button = new Button(texture, scale);
    }

    public Button getButton(){
        return button;
    }

    public void update() {
        if (button.isOn())
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
