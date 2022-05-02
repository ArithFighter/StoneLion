package com.arithfighter.not.widget.dialog;

import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionDialog extends Dialog {
    private final SceneControlButton yesButton;
    private final SceneControlButton noButton;

    public OptionDialog(Texture[] textures){
        super(textures[10], 35, 20);
        getPoint().set(
                WindowSetting.CENTER_X - getDialog().getWidget().getWidth() / 2,
                WindowSetting.CENTER_Y - getDialog().getWidget().getHeight() / 2
        );
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

    public void draw(SpriteBatch batch){
        Point point = getPoint();
        float width = getDialog().getWidget().getWidth();
        float height = getDialog().getWidget().getHeight();

        drawDialog(batch);

        yesButton.getButton().setPosition(point.getX()+width/5, point.getY()+height/6);
        yesButton.getButton().draw(batch, "yes");

        noButton.getButton().setPosition(point.getX()+width*3/5, point.getY()+height/6);
        noButton.getButton().draw(batch, "no");
    }

    public SceneControlButton getYesButton(){
        return yesButton;
    }

    public SceneControlButton getNoButton() {
        return noButton;
    }

    public void activate(float x, float y){
        yesButton.getButton().activate(x, y);
        noButton.getButton().activate(x, y);
    }

    public void deactivate(){
        yesButton.getButton().deactivate();
        noButton.getButton().deactivate();
    }

    public void dispose(){
        disposeTexts();
        yesButton.dispose();
        noButton.dispose();
    }
}
