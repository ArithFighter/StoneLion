package com.arithfighter.not.widget.dialog;

import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConversationDialog extends Dialog{
    private final Button skipButton;
    private final float buttonWidth;

    public ConversationDialog(Texture[] textures) {
        super(textures[11], 25);

        skipButton = new Button(textures[6], 1.2f);

        buttonWidth = textures[6].getWidth()*1.2f;

        getPoint().set(WindowSetting.CENTER_X-getDialog().getWidget().getWidth()/2,0);
    }

    public void setButtonFont(Font font){
        skipButton.setFont(font);
    }

    public Button getSkipButton(){
        return skipButton;
    }

    public void draw(SpriteBatch batch){
        float width = getDialog().getWidget().getWidth();
        int margin = 15;

        drawDialog(batch);

        skipButton.setPosition(
                getPoint().getX()+width-buttonWidth-margin,
                getPoint().getY()+margin
        );
        skipButton.draw(batch, "skip");
    }

    public void activate(float x, float y){
        skipButton.on(x, y);
    }

    public void deactivate(){
        skipButton.off();
    }
}
