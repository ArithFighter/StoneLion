package com.arithfighter.not.widget.dialog;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog {
    private final Font text;
    private final VisibleWidget dialog;
    private String content1;
    private String content2;
    private final Point point;
    private final int fontSize;

    public Dialog(Texture texture, float scale, int fontSize){
        dialog = new SpriteWidget(texture, scale);

        text = new Font(fontSize);
        text.setColor(Color.BLACK);

        point = new Point();

        this.fontSize = fontSize;
        content1 = "";
        content2 = "";
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

    public void drawDialog(SpriteBatch batch){
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();
        float height = dialog.getWidget().getHeight();

        text.draw(batch, content1, point.getX()+width/9, point.getY()+height*3/4);
        text.draw(batch, content2, point.getX()+width/9, point.getY()+height*5/8-fontSize/2f);
    }

    public void disposeTexts(){
        text.dispose();
    }
}
