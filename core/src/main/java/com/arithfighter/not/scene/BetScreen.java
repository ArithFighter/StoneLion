package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.widget.ValueBrowser;
import com.arithfighter.not.widget.button.Button;
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
    private final ValueBrowser tokenBet;
    private final ValueHolder tokenHolder;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private final TextProvider textProvider;
    private final NumberBoxQuantityGenerator numberBoxQuantityGenerator;
    private int numberBoxQuantity = 0;
    private final int minimalBet = 100;
    private final int cardLimit = 20;
    private final FontManager fontManager;
    private final GameCard gameCard;

    public BetScreen(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        this.soundManager = soundManager;

        tokenHolder = new ValueHolder();

        textProvider = new TextProvider();

        tokenBet = new ValueBrowser(textures);
        tokenBet.setPosition(500, 150);
        tokenBet.setValueHolder(tokenHolder);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000, 80);

        fontManager = new FontManager();

        numberBoxQuantityGenerator = new NumberBoxQuantityGenerator();

        gameCard = new GameCard(textures);
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

        startButton.getButton().on(x, y);
    }

    @Override
    public void touchDragged() {
        tokenBet.deactivate();

        startButton.getButton().off();
    }

    @Override
    public void touchUp() {
        if (tokenBet.isButtonActive())
            soundManager.playTouchedSound();

        if (startButton.getButton().isOn())
            soundManager.playAcceptSound();

        tokenBet.deactivate();

        startButton.getButton().off();

        gameCard.touchDown(getCursorPos().getX(), getCursorPos().getY());
    }

    public void setNumberBoxQuantity() {
        numberBoxQuantityGenerator.init();
        numberBoxQuantityGenerator.update();
        numberBoxQuantity = numberBoxQuantityGenerator.getQuantityGroup()[0];
    }

    @Override
    public void init() {
        numberBoxQuantityGenerator.init();
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

        fontManager.setCardLimit(texts[0] + cardLimit);
        fontManager.setBet(texts[1]);
        fontManager.draw(batch);

        tokenBet.draw(batch);

        startButton.getButton().draw(batch, texts[2]);

        gameCard.draw(batch);
    }

    @Override
    public void dispose() {
        fontManager.dispose();
        tokenBet.dispose();
        startButton.dispose();
        gameCard.dispose();
    }
}

class GameCard {
    private final Font codeFont;
    private final Button gameCard;

    public GameCard(Texture[] textures){
        gameCard = new Button(textures[6], 2f);
        gameCard.setPosition(100,500);

        codeFont = new Font(24);
        codeFont.setColor(Color.WHITE);
    }

    public void draw(SpriteBatch batch){
        codeFont.draw(batch, "A", 60,630);
        gameCard.draw(batch, "game card");
    }

    public void touchDown(float x, float y){
        if (gameCard.isOn())
            gameCard.off();
        else
            gameCard.on(x,y);
    }

    public void dispose(){
        codeFont.dispose();
        gameCard.dispose();
    }
}

class FontManager{
    private final Font cardLimitFont;
    private final Font betFont;
    private String cardLimit;
    private String bet;

    public FontManager(){
        cardLimitFont = new Font(24);
        cardLimitFont.setColor(Color.WHITE);

        betFont = new Font(30);
        betFont.setColor(Color.WHITE);
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public void draw(SpriteBatch batch){
        cardLimitFont.draw(batch, cardLimit, 900, 650);

        betFont.draw(batch, bet, 400, 250);
    }

    public void dispose(){
        cardLimitFont.dispose();
        betFont.dispose();
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

    public void init(){
        quantityGroup.clear();
    }
}