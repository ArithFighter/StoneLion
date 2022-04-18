package com.arithfighter.not.widget;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog {
    private final Font text;
    private final VisibleWidget dialog;
    private final Button yesButton;
    private final Button noButton;
    private Point point;

    public Dialog(Texture[] textures){
        yesButton = new Button(textures[6], 1.2f);
        noButton = new Button(textures[6], 1.2f);

        dialog = new SpriteWidget(textures[10], 30);

        text = new Font(20);
        text.setColor(Color.BLACK);
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void draw(SpriteBatch batch, String content){
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();
        float height = dialog.getWidget().getHeight();

        yesButton.setPosition(point.getX()+width/5, point.getY()+height/6);
        yesButton.draw(batch, "yes");

        noButton.setPosition(point.getX()+width*3/5, point.getY()+height/6);
        noButton.draw(batch, "no");

        text.draw(batch, content, point.getX()+width/5, point.getY()+height*3/4);
    }

    public void dispose(){
        text.dispose();
        yesButton.dispose();
        noButton.dispose();
    }
}
