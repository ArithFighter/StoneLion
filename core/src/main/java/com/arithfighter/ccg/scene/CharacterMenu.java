package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.entity.MaskAnimation;
import com.arithfighter.ccg.entity.player.CharacterList;
import com.arithfighter.ccg.font.Font;
import com.arithfighter.ccg.widget.Mask;
import com.arithfighter.ccg.widget.button.Button;
import com.arithfighter.ccg.widget.button.PanelButton;
import com.arithfighter.ccg.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu {
    private final Font characterName;
    private final SpriteWidget highLight;
    private final Button startButton;
    private enum GameReady {NEUTRAL, READY, START}
    private GameReady gameReady = GameReady.NEUTRAL;
    private SpriteBatch batch;
    private final ButtonPlacer placer = new ButtonPlacer();
    private final MaskAnimation animation;
    private final PanelButtonProducer buttonProducer;

    public CharacterMenu(Texture[] textures, Texture[] panels) {
        int panelQuantity = CharacterList.values().length;

        buttonProducer = new PanelButtonProducer(panels, panelQuantity);

        highLight = new SpriteWidget(textures[7], 1.8f);

        startButton = new Button(textures[6], 1.8f);
        startButton.setPosition(900, 120);

        characterName = new Font(36);
        characterName.setColor(Color.WHITE);

        Mask[] masks = new Mask[panelQuantity];
        for (int i = 0; i< panelQuantity; i++){
            masks[i] = new Mask(textures[5], 3f);
            masks[i].setPosition(
                    placer.getButtonX(i),
                    placer.getButtonY(i)
            );
        }

        animation = new MaskAnimation(masks);
    }

    public boolean isPanelButtonActive() {
        return buttonProducer.isPanelButtonActive();
    }

    public boolean isStartButtonActive(){
        return startButton.isActive();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void init() {
        animation.init();
        gameReady = GameReady.NEUTRAL;
    }

    public void draw() {
        handleStartButton();

        int index = buttonProducer.getActiveButtonIndex();
        highLight.setPosition(placer.getButtonX(index)-22, placer.getButtonY(index)-20);
        highLight.draw(batch);

        buttonProducer.draw(batch);

        startButton.draw(batch, "Start");

        CharacterList[] characters = CharacterList.values();
        characterName.draw(batch, characters[getSelectIndex()].name(), 900, 500);

        animation.draw(batch, 0.1f);
    }

    private void handleStartButton(){
        if (startButton.isActive())
            gameReady = GameReady.READY;
        else{
            if (gameReady == GameReady.READY)
                gameReady = GameReady.START;
        }
    }

    public int getSelectIndex() {
        return buttonProducer.getActiveButtonIndex();
    }

    public boolean isGameStart() {
        return gameReady == GameReady.START;
    }

    public void activateButton(int mouseX, int mouseY) {
        buttonProducer.activate(mouseX, mouseY);

        startButton.activate(mouseX, mouseY);
    }

    public void deactivateButton() {
        buttonProducer.deactivate();

        startButton.deactivate();
    }

    public void dispose() {
        startButton.dispose();
        characterName.dispose();
    }
}

class PanelButtonProducer {
    private final PanelButton[] buttons;
    private int selectedIndex;

    public PanelButtonProducer(Texture[] panels, int length){
        buttons = new PanelButton[length];

        ButtonPlacer placer = new ButtonPlacer();

        for (int i = 0; i < length; i++) {
            buttons[i] = new PanelButton(panels[i]);
            buttons[i].setPosition(placer.getButtonX(i), placer.getButtonY(i));
        }
    }

    public boolean isPanelButtonActive() {
        return buttons[selectedIndex].isActive();
    }

    public void draw(SpriteBatch batch){
        for (PanelButton button : buttons) button.draw(batch);
    }

    public int getActiveButtonIndex(){
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isActive())
                selectedIndex = i;
        }
        return selectedIndex;
    }

    public void activate(int mouseX, int mouseY){
        for (PanelButton button : buttons) button.activate(mouseX, mouseY);
    }

    public void deactivate(){
        for (PanelButton button : buttons) button.deactivate();
    }
}

class ButtonPlacer{
    public int getButtonX(int i) {
        return i < 3 ? 200 : 500;
    }

    public int getButtonY(int i) {
        int initY = 480;
        int margin = 180;
        int row = 3;

        return i < row ? initY - i * margin : initY - (i - row) * margin;
    }
}