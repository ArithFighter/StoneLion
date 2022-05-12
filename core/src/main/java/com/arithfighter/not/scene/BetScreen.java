package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.widget.ControlNumber;
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
    private final ControlNumber tokenBet;
    private final ValueHolder tokenHolder;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private int numberBoxQuantity = 0;
    private int cardLimit;
    private int initToken;
    private final TextProvider textProvider;
    private final NumberBoxQuantityGenerator numberBoxQuantityGenerator;

    public BetScreen(TextureManager textureManager, SoundManager soundManager){
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        this.soundManager = soundManager;

        tokenHolder = new ValueHolder();

        textProvider = new TextProvider();

        tokenBet = new ControlNumber(textures);
        tokenBet.setPosition(500,300);
        tokenBet.setValueHolder(tokenHolder);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000,150);

        cardLimitMessage = new Font(40);
        cardLimitMessage.setColor(Color.WHITE);

        betMessage = new Font(30);
        betMessage.setColor(Color.WHITE);

        numberBoxQuantityGenerator = new NumberBoxQuantityGenerator();
    }

    public int getCardLimit(){
        return cardLimit;
    }

    public int getNumberBoxQuantity(){
        return numberBoxQuantity;
    }

    public void setToken(int value){
        initToken = value;
        tokenHolder.setMaxValue(initToken);
        tokenHolder.setMinValue(100);
    }

    public int getBet(){
        return tokenHolder.getValue();
    }

    public boolean isStartGame(){
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

    public void setNumberBoxQuantity(){
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

        int valueChange = 100;
        tokenBet.setValueChange(valueChange);

        cardLimit = calculateLimit(valueChange);
    }

    private int calculateLimit(int valueChange){
        float betTokensProportion = tokenHolder.getValue()/(float)initToken;
        int baseLimit;
        if (numberBoxQuantity<2)
            baseLimit = 0;
        else if (numberBoxQuantity<4)
            baseLimit = 2;
        else
            baseLimit = 3;

        return (int) ((1-betTokensProportion)*valueChange/10+numberBoxQuantity+baseLimit);
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        String[] texts = textProvider.getBetScreenTexts();

        cardLimitMessage.draw(batch, texts[0] + cardLimit, 400,600);

        betMessage.draw(batch, texts[1], 400,400);

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

class NumberBoxQuantityGenerator{
    private final int[] quantityCandidates;
    private int candidateCursor = 0;
    private final RandomNumProducer randomNumProducer;

    public NumberBoxQuantityGenerator(){
        quantityCandidates = new int[]{1,3,6,9};

        randomNumProducer = new RandomNumProducer(100,0);
    }
    public int getQuantity(){
        checkCursor();
        return quantityCandidates[candidateCursor];
    }

    private void checkCursor(){
        if (candidateCursor>quantityCandidates.length-1)
            init();
    }

    private void init(){
        candidateCursor = 0;
    }

    public void update(){
        int i = randomNumProducer.getRandomNum();

        if (i < 5)
            candidateCursor = 0;
        if (i>=5&&i<15)
            candidateCursor = 1;
        if (i>=15&&i<80)
            candidateCursor = 2;
        if (i>=80&&i<=100)
            candidateCursor = 3;
    }
}