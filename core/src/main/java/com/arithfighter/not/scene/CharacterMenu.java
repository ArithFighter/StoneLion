package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.MaskAnimation;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.Mask;
import com.arithfighter.not.widget.button.PanelButton;
import com.arithfighter.not.widget.SpriteWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CharacterMenu extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font characterName;
    private final VisibleWidget highLight;
    private final SceneControlButton optionButton;
    private final SceneControlButton startButton;
    private final PanelButtonPlacer placer = new PanelButtonPlacer();
    private final MaskAnimation animation;
    private final PanelButtonProducer buttonProducer;
    private final SoundManager soundManager;
    private final Font bestScoreText;
    private final int[] highScores = new int[CharacterList.values().length];

    public CharacterMenu(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        Texture[] panels = textureManager.getTextures(textureManager.getKeys()[2]);
        this.soundManager = soundManager;

        int panelQuantity = CharacterList.values().length;

        buttonProducer = new PanelButtonProducer(panels, panelQuantity);

        highLight = new SpriteWidget(textures[7], 1.8f);

        startButton = new SceneControlButton(textures[6], 1.8f);
        startButton.getButton().setPosition(900, 120);

        optionButton = new SceneControlButton(textures[6], 1.8f);
        optionButton.getButton().setPosition(1000, 600);

        characterName = new Font(36);
        characterName.setColor(Color.WHITE);

        animation = new MaskAnimation(getMasks(panelQuantity, textures[5]));

        bestScoreText = new Font(20);
        bestScoreText.setColor(Color.WHITE);
    }

    private Mask[] getMasks(int quantity, Texture texture){
        Mask[] masks = new Mask[quantity];

        for (int i = 0; i < quantity; i++) {
            masks[i] = new Mask(texture, 3f);
            masks[i].setPosition(placer.getButtonX(i), placer.getButtonY(i));
        }
        return masks;
    }

    public void setHighScore(CharacterList character, int highScore) {
        for (int i = 0; i<CharacterList.values().length;i++){
            if (character == CharacterList.values()[i])
                highScores[i] = highScore;
        }
    }

    public void init() {
        animation.init();
        optionButton.init();
        startButton.init();
    }

    public void update() {
        optionButton.update();
        startButton.update();
    }

    public void draw() {
        SpriteBatch batch = getBatch();

        setButtonHighLightPosition();
        highLight.draw(batch);

        buttonProducer.draw(batch);

        startButton.getButton().draw(batch, "Start");

        optionButton.getButton().draw(batch, "Option");

        characterName.draw(batch, CharacterList.values()[getSelectIndex()].name(), 900, 500);

        animation.draw(batch, 0.1f);

        bestScoreText.draw(batch, "best: "+highScores[getSelectIndex()], 900,450);
    }

    private void setButtonHighLightPosition(){
        int index = buttonProducer.getActiveButtonIndex();
        highLight.setPosition(placer.getButtonX(index) - 22, placer.getButtonY(index) - 20);
    }

    public int getSelectIndex() {
        return buttonProducer.getActiveButtonIndex();
    }

    public boolean isGameStart() {
        return startButton.isStart();
    }

    public boolean isOpenOption() {
        return optionButton.isStart();
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        buttonProducer.activate(x, y);

        optionButton.getButton().activate(x, y);

        startButton.getButton().activate(x, y);
    }

    public void touchDragged() {
        deactivateButton();
    }

    public void touchUp() {
        if (buttonProducer.isButtonActive())
            soundManager.playTouchedSound();

        if (optionButton.getButton().isActive()||startButton.getButton().isActive())
            soundManager.playAcceptSound();

        deactivateButton();
    }

    private void deactivateButton() {
        buttonProducer.deactivate();

        optionButton.getButton().deactivate();

        startButton.getButton().deactivate();
    }

    public void dispose() {
        startButton.getButton().dispose();
        optionButton.getButton().dispose();
        characterName.dispose();
        bestScoreText.dispose();
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