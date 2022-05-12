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

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font cardLimitMessage;
    private final Font betMessage;
    private final ValueBrowser tokenBet;
    private final ValueHolder tokenHolder;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private int numberBoxQuantity = 0;
    private final CardLimitCalculator cardLimitCalculator = new CardLimitCalculator();
    private final TextProvider textProvider;
    private final NumberBoxQuantityGenerator numberBoxQuantityGenerator;

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
        return cardLimitCalculator.getCardLimit();
    }

    public int getNumberBoxQuantity() {
        return numberBoxQuantity;
    }

    public void setToken(int value) {
        cardLimitCalculator.setInitTokens(value);
        cardLimitCalculator.setMinTokens(100);
        tokenHolder.setMaxValue(cardLimitCalculator.getInitTokens());
        tokenHolder.setMinValue(cardLimitCalculator.getMinTokens());
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
        numberBoxQuantity = numberBoxQuantityGenerator.getQuantity();
    }

    @Override
    public void init() {
        startButton.init();
    }

    @Override
    public void update() {
        startButton.update();
        tokenBet.update();

        int valueChange = cardLimitCalculator.getMinTokens();
        tokenBet.setValueChange(valueChange);

        cardLimitCalculator.setTokenHolder(tokenHolder);
        cardLimitCalculator.setNumberBoxQuantity(numberBoxQuantity);

        cardLimitCalculator.run();
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        String[] texts = textProvider.getBetScreenTexts();

        int cardLimit = cardLimitCalculator.getCardLimit();

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

class CardLimitCalculator{
    private int numberBoxQuantity;
    private int initTokens;
    private int minTokens;
    private ValueHolder tokenHolder;
    private int cardLimit;

    public void setNumberBoxQuantity(int numberBoxQuantity) {
        this.numberBoxQuantity = numberBoxQuantity;
    }

    public void setInitTokens(int initTokens) {
        this.initTokens = initTokens;
    }

    public void setTokenHolder(ValueHolder tokenHolder) {
        this.tokenHolder = tokenHolder;
    }

    public void setMinTokens(int minTokens) {
        this.minTokens = minTokens;
    }

    public int getMinTokens() {
        return minTokens;
    }

    public int getInitTokens() {
        return initTokens;
    }

    public int getCardLimit(){
        return cardLimit;
    }

    public void run() {
        float betTokensProportion = tokenHolder.getValue() / (float) initTokens;

        cardLimit = (int) ((1 - betTokensProportion) * minTokens / 10 + numberBoxQuantity + getBaseLimit());
    }

    private int getBaseLimit(){
        int baseLimit;

        if (numberBoxQuantity < 2)
            baseLimit = 0;
        else if (numberBoxQuantity < 4)
            baseLimit = 2;
        else
            baseLimit = 3;

        return baseLimit;
    }
}

class NumberBoxQuantityGenerator {
    private final int[] quantityCandidates;
    private int candidateCursor = 0;
    private final RandomNumProducer probabilityProducer;
    private final int percent = 100;

    public NumberBoxQuantityGenerator() {
        quantityCandidates = new int[]{1, 3, 6, 9};

        probabilityProducer = new RandomNumProducer(percent, 0);
    }

    public int getQuantity() {
        checkCursor();
        return quantityCandidates[candidateCursor];
    }

    private void checkCursor() {
        if (candidateCursor > quantityCandidates.length - 1)
            init();
    }

    private void init() {
        candidateCursor = 0;
    }

    public void update() {
        int i = probabilityProducer.getRandomNum();
        double[] probability = {
                0.1 * percent,
                0.2 * percent,
                0.4 * percent,
                0.3 * percent
        };

        if (i < probability[0])
            candidateCursor = 0;
        if (i >= probability[0] && i < probability[0]+probability[1])
            candidateCursor = 1;
        if (i >= probability[0]+probability[1] && i < probability[0]+probability[1]+probability[2])
            candidateCursor = 2;
        if (i >= percent - probability[3] && i <= percent)
            candidateCursor = 3;
    }
}