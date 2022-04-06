package com.arithfighter.ccg.scene;

import com.arithfighter.ccg.audio.SoundManager;
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
    private final Button optionButton;
    private final SceneControlButton startButton;
    private SpriteBatch batch;
    private final PanelButtonPlacer placer = new PanelButtonPlacer();
    private final MaskAnimation animation;
    private final PanelButtonProducer buttonProducer;
    private final SoundManager soundManager;

    public CharacterMenu(Texture[] textures, Texture[] panels, SoundManager soundManager) {
        this.soundManager = soundManager;

        int panelQuantity = CharacterList.values().length;

        buttonProducer = new PanelButtonProducer(panels, panelQuantity);

        highLight = new SpriteWidget(textures[7], 1.8f);

        startButton = new SceneControlButton(textures[6], 1.8f);
        startButton.getButton().setPosition(900, 120);

        optionButton = new Button(textures[6], 1.8f);
        optionButton.setPosition(1000,600);

        characterName = new Font(36);
        characterName.setColor(Color.WHITE);

        Mask[] masks = new Mask[panelQuantity];
        for (int i = 0; i < panelQuantity; i++) {
            masks[i] = new Mask(textures[5], 3f);
            masks[i].setPosition(
                    placer.getButtonX(i),
                    placer.getButtonY(i)
            );
        }

        animation = new MaskAnimation(masks);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void init() {
        animation.init();
        startButton.init();
    }

    public void draw() {
        startButton.handleScene();

        int index = buttonProducer.getActiveButtonIndex();
        highLight.setPosition(placer.getButtonX(index) - 22, placer.getButtonY(index) - 20);
        highLight.draw(batch);

        buttonProducer.draw(batch);

        startButton.getButton().draw(batch, "Start");

        optionButton.draw(batch, "Option");

        CharacterList[] characters = CharacterList.values();
        characterName.draw(batch, characters[getSelectIndex()].name(), 900, 500);

        animation.draw(batch, 0.1f);
    }
    public int getSelectIndex() {
        return buttonProducer.getActiveButtonIndex();
    }

    public boolean isGameStart() {
        return startButton.isStart();
    }

    public void touchDown(int mouseX, int mouseY) {
        activateButton(mouseX, mouseY);
    }

    public void touchDragged() {
        deactivateButton();
    }

    public void touchUp() {
        if (buttonProducer.isButtonActive())
            soundManager.playTouchedSound();

        if (optionButton.isActive())
            soundManager.playAcceptSound();

        if (startButton.getButton().isActive())
            soundManager.playAcceptSound();

        deactivateButton();
    }

    private void activateButton(int mouseX, int mouseY) {
        buttonProducer.activate(mouseX, mouseY);

        optionButton.activate(mouseX, mouseY);

        startButton.getButton().activate(mouseX, mouseY);
    }

    private void deactivateButton() {
        buttonProducer.deactivate();

        optionButton.deactivate();

        startButton.getButton().deactivate();
    }

    public void dispose() {
        startButton.getButton().dispose();
        characterName.dispose();
    }
}

class PanelButtonProducer {
    private final PanelButton[] buttons;
    private int selectedIndex;

    public PanelButtonProducer(Texture[] panels, int length) {
        buttons = new PanelButton[length];

        PanelButtonPlacer placer = new PanelButtonPlacer();

        for (int i = 0; i < length; i++) {
            buttons[i] = new PanelButton(panels[i]);
            buttons[i].setPosition(placer.getButtonX(i), placer.getButtonY(i));
        }
    }

    public boolean isButtonActive() {
        return buttons[selectedIndex].isActive();
    }

    public void draw(SpriteBatch batch) {
        for (PanelButton button : buttons) button.draw(batch);
    }

    public int getActiveButtonIndex() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isActive())
                selectedIndex = i;
        }
        return selectedIndex;
    }

    public void activate(int mouseX, int mouseY) {
        for (PanelButton button : buttons) button.activate(mouseX, mouseY);
    }

    public void deactivate() {
        for (PanelButton button : buttons) button.deactivate();
    }
}

class PanelButtonPlacer {
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

class SceneControlButton{
    private enum SceneChange {NEUTRAL, READY, START}
    private SceneChange sceneChange = SceneChange.NEUTRAL;
    private final Button button;

    public SceneControlButton(Texture texture, float scale){
        button = new Button(texture, scale);
    }

    public Button getButton(){
        return button;
    }

    public void handleScene() {
        if (button.isActive())
            sceneChange = SceneChange.READY;
        else {
            if (sceneChange == SceneChange.READY)
                sceneChange = SceneChange.START;
        }
    }

    public boolean isStart(){
        return sceneChange == SceneChange.START;
    }

    public void init(){
        sceneChange = SceneChange.NEUTRAL;
    }
}