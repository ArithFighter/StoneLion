package com.arithfighter.not.widget;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.button.ArrowButtons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetBrowser {
    private final Font font;
    private final ArrowButtons arrows;
    private final int fontSize;
    private boolean isButtonLock = false;
    private final int[] betList;
    private int cursor = 0;

    public BetBrowser(Texture[] textures) {
        arrows = new ArrowButtons(textures, 0.8f);

        fontSize = 25;
        font = new Font(fontSize);

        betList = new int[]{5,10,20,50,100,200};
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
        String content = String.valueOf(betList[cursor]);
        int width = fontSize*9;

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
                cursor++;
            if (arrows.isRightActive() && cursor < betList.length)
                cursor--;
        }
        isButtonLock = arrows.isLeftActive() || arrows.isRightActive();
    }

    public void activate(float x, float y) {
        arrows.on(x, y);
    }

    public void deactivate() {
        arrows.off();
    }

    public void dispose() {
        font.dispose();
        arrows.dispose();
    }
}
