package com.arithfighter.not.pojo;

import com.badlogic.gdx.Gdx;

public class LayoutSetter {
    private final Rectangle maxLayout;
    private Rectangle grid;

    public LayoutSetter(){
        maxLayout = new Rectangle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public LayoutSetter(Rectangle maxLayout){
        this.maxLayout = maxLayout;
    }

    public void setGrid(int proportionX, int proportionY) {
        grid = new Rectangle(maxLayout.getWidth()/proportionX, maxLayout.getHeight()/proportionY);
    }

    public Rectangle getGrid() {
        return grid;
    }
}
