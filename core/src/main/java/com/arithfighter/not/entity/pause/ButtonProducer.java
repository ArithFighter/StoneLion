package com.arithfighter.not.entity.pause;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class ButtonProducer {
    private final SceneControlButton[] buttons;

    public ButtonProducer(Texture texture, Font font) {
        buttons = new SceneControlButton[3];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new SceneControlButton(texture, 1.8f);
            buttons[i].getButton().setPosition(540, 250 + 150 * i);
            buttons[i].getButton().setFont(font);
        }
    }

    public SceneControlButton[] getButtons() {
        return buttons;
    }

    public SceneControlButton getResume() {
        return buttons[2];
    }

    public SceneControlButton getOption() {
        return buttons[1];
    }

    public SceneControlButton getQuit() {
        return buttons[0];
    }

    public void draw(SpriteBatch batch, String[] texts) {
        for (int i = 0; i < buttons.length; i++) {
            Button b = buttons[i].getButton();
            b.draw(batch, texts[i]);
        }
    }
}
