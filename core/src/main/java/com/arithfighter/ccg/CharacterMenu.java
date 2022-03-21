package com.arithfighter.ccg;

import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.widget.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu {
    private final Font selectionFont;
    private final Button[] buttons;
    private final Button startButton;
    private final String[] names = {"Knight", "Rogue"};
    private int selectIndex = 0;

    public CharacterMenu(Texture[] textures) {
        buttons = new Button[names.length];

        for (int i = 0; i<names.length;i++){
            buttons[i] = new Button(textures[6]);
            buttons[i].setPosition(360, 450-i*150);
        }

        startButton = new Button(textures[6]);
        startButton.setPosition(700,200);

        selectionFont = new Font(36);
        selectionFont.setColor(Color.WHITE);
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i< buttons.length;i++)
            buttons[i].draw(batch, names[i]);

        startButton.draw(batch, "Start");

        for (int i = 0; i< buttons.length;i++){
            if (buttons[i].isActive())
                selectIndex = i;
        }

        selectionFont.draw(batch, names[selectIndex], 700,450);
    }

    public void activateButton(int mouseX, int mouseY) {
        for (Button button:buttons)
            button.activate(mouseX, mouseY);

        startButton.activate(mouseX, mouseY);
    }

    public void deactivateButton() {
        for (Button button:buttons)
            button.deactivate();

        startButton.deactivate();
    }

    public void dispose(){
        for (Button button:buttons)
            button.dispose();

        startButton.dispose();
        selectionFont.dispose();
    }
}
