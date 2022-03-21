package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu {
    private final Button greenButton;

    public CharacterMenu(Texture[] textures) {
        greenButton = new Button(textures[6]);
        greenButton.setPosition(450, 450);
    }

    public void draw(SpriteBatch batch) {
        greenButton.draw(batch, "Button");
    }

    public void activateButton(int mouseX, int mouseY) {
        greenButton.activate(mouseX, mouseY);
    }

    public void deactivateButton() {
        greenButton.deactivate();
    }
}
