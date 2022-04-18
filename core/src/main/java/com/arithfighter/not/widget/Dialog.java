package com.arithfighter.not.widget;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog {
    private final Font text1;
    private final Font text2;
    private final VisibleWidget dialog;
    private final Button yesButton;
    private final Button noButton;
    private Point point;

    public Dialog(Texture[] textures){
        yesButton = new Button(textures[6], 1.2f);
        noButton = new Button(textures[6], 1.2f);

        dialog = new SpriteWidget(textures[10], 35);

        text1 = new Font(20);
        text1.setColor(Color.BLACK);

        text2 = new Font(20);
        text2.setColor(Color.BLACK);
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void draw(SpriteBatch batch, String content1, String content2){
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();
        float height = dialog.getWidget().getHeight();

        yesButton.setPosition(point.getX()+width/5, point.getY()+height/6);
        yesButton.draw(batch, "yes");

        noButton.setPosition(point.getX()+width*3/5, point.getY()+height/6);
        noButton.draw(batch, "no");

        text1.draw(batch, content1, point.getX()+width/9, point.getY()+height*3/4);
        text2.draw(batch, content2, point.getX()+width/9, point.getY()+height*5/8);
    }

    public void dispose(){
        text1.dispose();
        yesButton.dispose();
        noButton.dispose();
    }
}
