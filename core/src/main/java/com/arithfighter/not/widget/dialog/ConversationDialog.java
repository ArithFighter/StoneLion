package com.arithfighter.not.widget.dialog;

import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConversationDialog extends Dialog{
    private final Button skipButton;

    public ConversationDialog(Texture[] textures) {
        super(textures[11], 25, 28);

        skipButton = new Button(textures[6], 1.2f);

        getPoint().set(WindowSetting.CENTER_X-getDialog().getWidget().getWidth()/2,0);
    }

    public Button getSkipButton(){
        return skipButton;
    }

    public void draw(SpriteBatch batch){
        float width = getDialog().getWidget().getWidth();

        float buttonWidth = skipButton.getButton().getWidget().getWidth();
        int margin = 15;

        drawDialog(batch);

        skipButton.setPosition(
                getPoint().getX()+width-buttonWidth-margin,
                getPoint().getY()+margin
        );
        skipButton.draw(batch, "skip");
    }

    public void activate(float x, float y){
        skipButton.activate(x, y);
    }

    public void deactivate(){
        skipButton.deactivate();
    }

    public void dispose(){
        disposeTexts();
        skipButton.dispose();
    }
}
