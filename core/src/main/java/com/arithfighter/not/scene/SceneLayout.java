package com.arithfighter.not.scene;

import com.arithfighter.not.pojo.Rectangle;
import com.badlogic.gdx.Gdx;

public class SceneLayout {
    private final Rectangle maxLayout;
    private final Rectangle grid;
    private final float centerX;
    private final float centerY;

    public SceneLayout(int proportionX, int proportionY){
        maxLayout = new Rectangle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        grid = new Rectangle(maxLayout.getWidth()/proportionX, maxLayout.getHeight()/proportionY);

        centerX = maxLayout.getWidth()/2;
        centerY = maxLayout.getHeight()/2;
    }

    public Rectangle getMaxLayout() {
        return maxLayout;
    }

    public Rectangle getGrid() {
        return grid;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }
}
