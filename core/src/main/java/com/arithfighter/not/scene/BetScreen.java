package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.widget.ValueBrowser;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.pojo.ValueHolder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;
import java.util.List;

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font cardLimitMessage;
    private final Font betMessage;
    private final ValueBrowser tokenBet;
    private final ValueHolder tokenHolder;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private int numberBoxQuantity = 0;
    private final TextProvider textProvider;
    private final NumberBoxQuantityGenerator numberBoxQuantityGenerator;
    private final int minimalBet = 100;
    private final int cardLimit = 20;

    public BetScreen(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        this.soundManager = soundManager;

        tokenHolder = new ValueHolder();

        textProvider = new TextProvider();

        tokenBet = new ValueBrowser(textures);
        tokenBet.setPosition(500, 300);
        tokenBet.setValueHolder(tokenHolder);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000, 150);

        cardLimitMessage = new Font(40);
        cardLimitMessage.setColor(Color.WHITE);

        betMessage = new Font(30);
        betMessage.setColor(Color.WHITE);

        numberBoxQuantityGenerator = new NumberBoxQuantityGenerator();
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public int getNumberBoxQuantity() {
        return numberBoxQuantity;
    }

    public void setInitToken(int initTokens) {
        tokenHolder.setMaxValue(initTokens);
        tokenHolder.setMinValue(minimalBet);
    }

    public int getBet() {
        return tokenHolder.getValue();
    }

    public boolean isStartGame() {
        return startButton.isStart();
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        tokenBet.activate(x, y);

        startButton.getButton().activate(x, y);
    }

    @Override
    public void touchDragged() {
        tokenBet.deactivate();

        startButton.getButton().deactivate();
    }

    @Override
    public void touchUp() {
        if (tokenBet.isButtonActive())
            soundManager.playTouchedSound();

        if (startButton.getButton().isActive())
            soundManager.playAcceptSound();

        tokenBet.deactivate();

        startButton.getButton().deactivate();
    }

    public void setNumberBoxQuantity() {
        numberBoxQuantityGenerator.update();
        numberBoxQuantity = numberBoxQuantityGenerator.getQuantityGroup()[0];
    }

    @Override
    public void init() {
        startButton.init();
    }

    @Override
    public void update() {
        startButton.update();
        tokenBet.update();

        tokenBet.setValueChange(minimalBet);
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        String[] texts = textProvider.getBetScreenTexts();

        cardLimitMessage.draw(batch, texts[0] + cardLimit, 400, 600);

        betMessage.draw(batch, texts[1], 400, 400);

        tokenBet.draw(batch);

        startButton.getButton().draw(batch, texts[2]);
    }

    @Override
    public void dispose() {
        betMessage.dispose();
        cardLimitMessage.dispose();
        tokenBet.dispose();
        startButton.dispose();
    }
}

class NumberBoxQuantityGenerator {
    private final int[] quantityCandidates;
    private final List<Integer> quantityGroup;
    private final RandomNumProducer indexPicker;
    private final int quantityArrayLength = 3;

    public NumberBoxQuantityGenerator() {
        quantityCandidates = new int[]{1, 3, 6, 9};

        quantityGroup = new LinkedList<>();

        indexPicker = new RandomNumProducer(quantityCandidates.length-1, 0);
    }

    public int[] getQuantityGroup() {
        int[] quantityArray = new int[quantityArrayLength];

        for (int i = 0;i<quantityArrayLength;i++)
            quantityArray[i] = quantityGroup.get(i);

        return quantityArray;
    }

    public void update(){
        while (quantityGroup.size()<quantityArrayLength){
            int candidateCursor = indexPicker.getRandomNum();
            quantityGroup.add(quantityCandidates[candidateCursor]);
        }
    }
}