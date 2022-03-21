package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.Button;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu {
    private final Button[] buttons;
    private final String[] names = {"Knight", "Rogue"};

    public CharacterMenu(Texture[] textures) {
        buttons = new Button[names.length];

        for (int i = 0; i<names.length;i++){
            buttons[i] = new Button(textures[6]);
            buttons[i].setPosition(450, 450-i*150);
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i< buttons.length;i++)
            buttons[i].draw(batch, names[i]);
    }

    public void activateButton(int mouseX, int mouseY) {
        for (Button button:buttons)
            button.activate(mouseX, mouseY);
    }

    public void deactivateButton() {
        for (Button button:buttons)
            button.deactivate();
    }

    public void dispose(){
        for (Button button:buttons)
            button.dispose();
    }
}
