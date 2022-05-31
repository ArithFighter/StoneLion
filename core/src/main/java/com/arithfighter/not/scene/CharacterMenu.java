package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
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
    private final Font largeFont;
    private final Font smallFont;
    private final Font tokenFont;
    private final VisibleWidget highLight;
    private final SceneControlButton optionButton;
    private final SceneControlButton startButton;
    private final PanelButtonPlacer placer = new PanelButtonPlacer();
    private final MaskAnimation animation;
    private final PanelButtonProducer buttonProducer;
    private final SoundManager soundManager;
    private final int[] savedTokens = new int[CharacterList.values().length];

    public CharacterMenu(TextureService textureService, SoundManager soundManager) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] panels = textureService.getTextures(textureService.getKeys()[2]);
        this.soundManager = soundManager;

        int panelQuantity = CharacterList.values().length;

        buttonProducer = new PanelButtonProducer(panels, panelQuantity);

        highLight = new SpriteWidget(textures[7], 1.8f);

        largeFont = new Font(36);
        largeFont.setColor(Color.WHITE);

        smallFont = new Font(22);
        smallFont.setColor(Color.WHITE);

        tokenFont = new Font(22);
        tokenFont.setColor(Color.WHITE);

        startButton = new SceneControlButton(textures[6], 1.8f);
        startButton.getButton().setFont(smallFont);
        startButton.getButton().setPosition(900, 120);

        optionButton = new SceneControlButton(textures[6], 1.8f);
        optionButton.getButton().setFont(smallFont);
        optionButton.getButton().setPosition(1000, 600);

        animation = new MaskAnimation(getMasks(panelQuantity, textures[5]));
    }

    private Mask[] getMasks(int quantity, Texture texture){
        Mask[] masks = new Mask[quantity];

        for (int i = 0; i < quantity; i++) {
            masks[i] = new Mask(texture, 3f);
            masks[i].setPosition(placer.getButtonX(i), placer.getButtonY(i));
        }
        return masks;
    }

    public void setSavedTokens(CharacterList character, int highScore) {
        for (int i = 0; i<CharacterList.values().length;i++){
            if (character == CharacterList.values()[i])
                savedTokens[i] = highScore;
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

    public void render() {
        SpriteBatch batch = getBatch();

        setButtonHighLightPosition();
        highLight.draw(batch);

        buttonProducer.draw(batch);

        startButton.getButton().draw(batch, "Start");

        optionButton.getButton().draw(batch, "Option");

        largeFont.draw(batch, CharacterList.values()[getSelectIndex()].name(), 900, 500);

        animation.draw(batch, 0.1f);

        tokenFont.draw(batch, "tokens: "+ savedTokens[getSelectIndex()], 900,450);
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

        optionButton.getButton().on(x, y);

        startButton.getButton().on(x, y);
    }

    public void touchDragged() {
        deactivateButton();
    }

    public void touchUp() {
        if (buttonProducer.isButtonActive())
            soundManager.playTouchedSound();

        if (optionButton.getButton().isOn()||startButton.getButton().isOn())
            soundManager.playAcceptSound();

        deactivateButton();
    }

    private void deactivateButton() {
        buttonProducer.deactivate();

        optionButton.getButton().off();

        startButton.getButton().off();
    }

    public void dispose() {
        largeFont.dispose();
        smallFont.dispose();
        tokenFont.dispose();
    }
}

class PanelButtonProducer {
    private final PanelButton[] buttons;
    private int selectedIndex;

    public PanelButtonProducer(Texture[] panels, int length) {
        buttons = new PanelButton[length];

        PanelButtonPlacer placer = new PanelButtonPlacer();

        for (int i = 0; i < length; i++) {
            buttons[i] = new PanelButton(panels[i], 0.8f);
            buttons[i].setPosition(placer.getButtonX(i), placer.getButtonY(i));
        }
    }

    public boolean isButtonActive() {
        return buttons[selectedIndex].isOn();
    }

    public void draw(SpriteBatch batch) {
        for (PanelButton button : buttons) button.draw(batch);
    }

    public int getActiveButtonIndex() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].isOn())
                selectedIndex = i;
        }
        return selectedIndex;
    }

    public void activate(int mouseX, int mouseY) {
        for (PanelButton button : buttons) button.on(mouseX, mouseY);
    }

    public void deactivate() {
        for (PanelButton button : buttons) button.off();
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