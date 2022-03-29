package com.arithfighter.ccg.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoardArea {
    private final Sprite table;
    private final Widget widget;
    private final Point point;

    public BoardArea(Texture texture){
        widget = new Widget();
        widget.setSize(texture, 3);
        point = widget.getPoint();

        table = new Sprite(texture);
        table.setColor(Color.BROWN);
    }

    public void setPosition(int x, int y){
        point.set(x,y);
    }

    private void setSprite(){
        table.setPosition(point.getX(), point.getY());
        table.setSize(widget.getWidth(), widget.getHeight());
    }

    public void draw(SpriteBatch batch){
        setSprite();
        table.draw(batch);
    }

    public final void playCardOnBoard(int mouseX, int mouseY) {
        if (isOnBoard(mouseX, mouseY)) {
            checkCardPlayed();
        }
        initCardPosition();
    }

    public void initCardPosition() {

    }

    public void checkCardPlayed() {

    }

    public boolean isOnBoard(float x, float y){
        return x > point.getX()
                && x < point.getX() + widget.getWidth()
                && y > point.getY()
                && y < point.getY() + widget.getHeight();
    }
}
