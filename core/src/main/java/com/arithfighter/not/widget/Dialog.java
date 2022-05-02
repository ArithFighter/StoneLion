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
    private String content1;
    private String content2;
    private Point point;

    public Dialog(Texture[] textures){
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

    public Point getPoint() {
        return point;
    }

    public VisibleWidget getDialog(){
        return dialog;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void drawDialog(SpriteBatch batch){
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();
        float height = dialog.getWidget().getHeight();

        text1.draw(batch, content1, point.getX()+width/9, point.getY()+height*3/4);
        text2.draw(batch, content2, point.getX()+width/9, point.getY()+height*5/8);
    }

    public void disposeTexts(){
        text1.dispose();
        text2.dispose();
    }
}
