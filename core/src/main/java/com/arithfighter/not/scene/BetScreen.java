package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.widget.ControlNumber;
import com.arithfighter.not.widget.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.RandomNumProducer;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.pojo.ValueHolder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent{
    private final Font cardLimitMessage;
    private final Font betMessage;
    private final ControlNumber tokenBet;
    private final ValueHolder tokenHolder;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private int numberBoxQuantity = 1;
    private int cardLimit;
    private int initToken;
    private final TextProvider textProvider;

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
    }

    public int getCardLimit(){
        return cardLimit;
    }

    public int getNumberBoxQuantity(){
        return numberBoxQuantity;
    }

    public void setToken(int value){
        initToken = value;
        tokenHolder.setInitValue(initToken);
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

    @Override
    public void init() {
        numberBoxQuantity = new RandomNumProducer(9,1).getRandomNum();
        startButton.init();
    }

    @Override
    public void update() {
        startButton.update();
        tokenBet.update();

        int divide = 7;
        int valueChange = initToken/divide;
        tokenBet.setValueChange(valueChange);

        cardLimit = calculateLimit(divide);
    }

    private int calculateLimit(int divide){
        float betTokensProportion = tokenHolder.getValue()/(float)initToken;
        int baseLimit;
        if (numberBoxQuantity<2)
            baseLimit = 0;
        else if (numberBoxQuantity<4)
            baseLimit = 2;
        else
            baseLimit = 3;

        return (int) ((1-betTokensProportion)*divide+numberBoxQuantity+baseLimit);
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
