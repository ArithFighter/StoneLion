package com.arithfighter.not.widget.a1;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.ArrowButtons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetBrowser {
    private Font font;
    private final ArrowButtons arrows;
    private boolean isButtonLock = false;
    private int[] betList;
    private int cursor = 0;

    public BetBrowser(Texture[] textures) {
        arrows = new ArrowButtons(textures, 0.8f);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getBet(){
        return betList[cursor];
    }

    public void setBetList(int[] betList) {
        this.betList = betList;
    }

    public boolean isButtonActive(){
        return arrows.isLeftActive()||arrows.isRightActive();
    }

    public void setPosition(int x, int y) {
        Point point = new Point();
        point.set(x,y);

        arrows.setPoint(point);
    }

    public void draw(SpriteBatch batch){
        int fontSize = font.getSize();
        font.setColor(Color.WHITE);
        int width = fontSize*9;
            String content = String.valueOf(getBet());

            this.font.draw(
                    batch,
                    content,
                    arrows.getPoint().getX()+(width-content.length()*fontSize)/2f,
                    arrows.getPoint().getY()+fontSize
            );

        arrows.drawLeftArrow(batch);
        arrows.drawRightArrow(batch, width);
    }

    public void update() {
        if (!isButtonLock) {
            if (arrows.isLeftActive() && cursor > 0)
                cursor--;
            if (arrows.isRightActive() && cursor < betList.length-1)
                cursor++;
        }
        isButtonLock = arrows.isLeftActive() || arrows.isRightActive();
    }

    public void on(float x, float y) {
        arrows.on(x, y);
    }

    public void off() {
        arrows.off();
    }
}
