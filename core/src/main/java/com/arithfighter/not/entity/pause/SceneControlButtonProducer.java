package com.arithfighter.not.entity.pause;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class SceneControlButtonProducer {
    private final SceneControlButton[] buttons;
    private Point point;

    public SceneControlButtonProducer(int length) {
        buttons = new SceneControlButton[length];
    }

    public void setButtons(Texture texture, Font font){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new SceneControlButton(texture, 1.8f);
            buttons[i].getButton().setFont(font);
        }
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setLayout(float width, float height){
        LayoutSetter layoutSetter = new LayoutSetter(new Rectangle(width, height));
        layoutSetter.setGrid(2, buttons.length);
        Rectangle grid = layoutSetter.getGrid();

        for (int i = 0; i < buttons.length; i++){
            Button button = buttons[i].getButton();

            button.setPosition(
                    point.getX() + grid.getWidth()-button.getWidget().getWidth()/2,
                    point.getY() + height-button.getWidget().getHeight()*1.3f * (i+1)
            );
        }
    }

    public SceneControlButton[] getSceneControlButtons() {
        return buttons;
    }

    public void draw(SpriteBatch batch, String[] texts) {
        for (int i = 0; i < buttons.length; i++) {
            Button b = buttons[i].getButton();
            b.draw(batch, texts[i]);
        }
    }
}
