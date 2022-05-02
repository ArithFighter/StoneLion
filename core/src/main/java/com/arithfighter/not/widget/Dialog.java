package com.arithfighter.not.widget;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog {
    private final Font text1;
    private final Font text2;
    private final VisibleWidget dialog;
    private final SceneControlButton yesButton;
    private final SceneControlButton noButton;
    private String content1;
    private String content2;
    private Point point;

    public Dialog(Texture[] textures){
        yesButton = new SceneControlButton(textures[6], 1.2f);
        noButton = new SceneControlButton(textures[6], 1.2f);

        dialog = new SpriteWidget(textures[10], 35);

        text1 = new Font(20);
        text1.setColor(Color.BLACK);

        text2 = new Font(20);
        text2.setColor(Color.BLACK);
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public void init(){
        yesButton.init();
        noButton.init();
    }

    public void update(){
        yesButton.update();
        noButton.update();
    }

    public VisibleWidget getDialog(){
        return dialog;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void draw(SpriteBatch batch){
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();
        float height = dialog.getWidget().getHeight();

        yesButton.getButton().setPosition(point.getX()+width/5, point.getY()+height/6);
        yesButton.getButton().draw(batch, "yes");

        noButton.getButton().setPosition(point.getX()+width*3/5, point.getY()+height/6);
        noButton.getButton().draw(batch, "no");

        text1.draw(batch, content1, point.getX()+width/9, point.getY()+height*3/4);
        text2.draw(batch, content2, point.getX()+width/9, point.getY()+height*5/8);
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
        text1.dispose();
        text2.dispose();
        yesButton.dispose();
        noButton.dispose();
    }
}
