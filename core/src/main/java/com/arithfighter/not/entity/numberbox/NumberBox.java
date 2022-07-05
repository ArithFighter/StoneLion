package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.FontWidget;
import com.arithfighter.not.widget.RawWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberBox{
    private Font font;
    private final FontWidget widget;
    private Point point;

    public NumberBox(Texture texture, float scale) {
        widget = new SpriteWidget(texture, scale);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public Rectangle getRectangle(){
        RawWidget raw = widget.getWidget();
        return new Rectangle(raw.getWidth(), raw.getHeight());
    }

    public void draw(SpriteBatch batch, int number) {
        widget.setPosition(point.getX(), point.getY());

        widget.setFontSize(font.getSize());

        widget.getSprite().setColor(Color.WHITE);

        widget.draw(batch);

        addText(batch, number);
    }

    private void addText(SpriteBatch batch, int number) {
        String content = String.valueOf(number);

        float textX = widget.getCenterX(content);
        float textY = widget.getCenterY();

        changeNumColor(number);

        font.draw(batch, content, textX, textY);
    }

    private void changeNumColor(int number) {
        int purpleNum = 15;
        int blueNum = 21;
        int yellowNum = 99;

        if (number < purpleNum) font.setColor(Color.PURPLE);

        else if (number <= blueNum) font.setColor(Color.BLUE);

        else if (number < yellowNum) font.setColor(Color.YELLOW);
    }


}
