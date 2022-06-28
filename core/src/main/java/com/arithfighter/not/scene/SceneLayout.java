package com.arithfighter.not.scene;

import com.arithfighter.not.pojo.Rectangle;

public class SceneLayout {
    private Rectangle maxLayout;
    private Rectangle grid;

    public SceneLayout(){
    }

    public void setMaxLayout(Rectangle maxLayout) {
        this.maxLayout = maxLayout;
    }

    public void setGrid(int proportionX, int proportionY) {
        grid = new Rectangle(maxLayout.getWidth()/proportionX, maxLayout.getHeight()/proportionY);
    }

    public Rectangle getGrid() {
        return grid;
    }
}
