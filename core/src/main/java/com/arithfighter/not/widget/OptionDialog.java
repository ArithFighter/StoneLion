package com.arithfighter.not.widget;

import com.badlogic.gdx.graphics.Texture;

public class OptionDialog {
    private final SceneControlButton yesButton;
    private final SceneControlButton noButton;

    public OptionDialog(Texture[] textures){
        yesButton = new SceneControlButton(textures[6], 1.2f);
        noButton = new SceneControlButton(textures[6], 1.2f);
    }

    public void init(){
        yesButton.init();
        noButton.init();
    }

    public void update(){
        yesButton.update();
        noButton.update();
    }
}
