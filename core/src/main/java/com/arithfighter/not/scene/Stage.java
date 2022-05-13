package com.arithfighter.not.scene;

import com.arithfighter.not.TextureManager;
import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.pojo.TokenHolder;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends SceneComponent implements SceneEvent, MouseEvent {
    private final PlayerCollection playerCollection;
    private final GamePlayComponent gamePlayComponent;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;
    private final GameDataDisplacer dataDisplacer;
    private final TokenHolder tokenHolder;
    private final StageMessage stageMessage;
    private int numberBoxQuantity;
    private final CardLimitManager cardLimitManager;

    public Stage(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        Texture[] cards = textureManager.getTextures(textureManager.getKeys()[1]);
        Texture[] spriteSheet = textureManager.getTextures(textureManager.getKeys()[3]);

        cardLimitManager = new CardLimitManager();

        dataDisplacer = new GameDataDisplacer();

        gamePlayComponent = new GamePlayComponent(textures, spriteSheet, soundManager);

        pauseMenu = new PauseMenu(textures, soundManager);

        tokenHolder = new TokenHolder();//the initValue there doesn't matter

        playerCollection = new PlayerCollection(
                textures,
                cards,
                CharacterList.values().length,
                gamePlayComponent.getNumberBoxDisplacer()
        );
        playerCollection.setPlayRecord(cardLimitManager.getPlayRecord());

        pauseButton = new SceneControlButton(textures[6], 1.8f);
        pauseButton.getButton().setPosition(1000, 600);

        stageMessage = new StageMessage(450, 500) {
            @Override
            public boolean isExceedCardLimitAndStageNotComplete() {
                return cardLimitManager.isExceedCardLimit() && !gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
            }

            @Override
            public boolean isStageComplete() {
                return gamePlayComponent.getNumberBoxDisplacer().isAllNumZero();
            }
        };
    }

    public void setCardLimit(int limit) {
        cardLimitManager.setCardLimit(limit);
    }

    public PauseMenu getPauseMenu(){
        return pauseMenu;
    }

    public TokenHolder getTokenHolder() {
        return tokenHolder;
    }

    public StageMessage getStageMessage(){
        return stageMessage;
    }

    public void init() {
        cardLimitManager.getPlayRecord().reset();
        gamePlayComponent.init();
        pauseMenu.init();
        pauseButton.init();
        stageMessage.init();
    }

    public void setNumberBoxQuantity(int quantity) {
        numberBoxQuantity = quantity;
    }

    public void update() {
        if (pauseButton.isStart()) {
            pauseMenu.update();

            if (pauseMenu.isResume()) {
                pauseButton.init();
                pauseMenu.init();
            }
        } else {
            pauseButton.update();

            gamePlayComponent.setNumberQuantity(numberBoxQuantity);

            gamePlayComponent.update(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void draw() {
        SpriteBatch batch = getBatch();

        cardLimitManager.draw(batch);

        gamePlayComponent.setBatch(batch);
        gamePlayComponent.draw();

        if (pauseButton.isStart())
            pauseMenu.draw(batch);
        else
            pauseButton.getButton().draw(batch, "Pause");

        stageMessage.draw(batch);
    }

    public void drawData(int index) {
        //for dev
        dataDisplacer.setCardPlayTimes(cardLimitManager.getPlayRecord().getRecord());
        dataDisplacer.setEnergy(playerCollection.getPlayers()[index].getEnergy());
        dataDisplacer.setCursorPoint(getCursorPos().getX(), getCursorPos().getY());
        dataDisplacer.setToken(tokenHolder.getTokens());
        dataDisplacer.setCardLimit(cardLimitManager.getCardLimit());
        dataDisplacer.draw(getBatch());
    }

    public void setSelectedPlayerToGame(int i) {
        gamePlayComponent.setPlayer(playerCollection.getPlayers()[i]);
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (isStageNotComplete()) {
            if (pauseButton.isStart())
                pauseMenu.touchDown(x, y);
            else {
                pauseButton.getButton().activate(x, y);
                gamePlayComponent.touchDown(x, y);
            }
        }
    }

    public void touchDragged() {
        if (isStageNotComplete()) {
            if (pauseButton.isStart())
                pauseMenu.touchDragged();
            else {
                pauseButton.getButton().deactivate();
                gamePlayComponent.touchDragged(getCursorPos().getX(), getCursorPos().getY());
            }
        }
    }

    public void touchUp() {
        if (isStageNotComplete()) {
            if (pauseButton.isStart())
                pauseMenu.touchUp();
            else {
                pauseButton.getButton().deactivate();
                gamePlayComponent.touchUp(getCursorPos().getX(), getCursorPos().getY());
            }
        }
    }

    private boolean isStageNotComplete() {
        return !stageMessage.isStageComplete() && !stageMessage.isExceedCardLimitAndStageNotComplete();
    }

    public void dispose() {
        dataDisplacer.dispose();
        gamePlayComponent.dispose();
        pauseMenu.dispose();
        playerCollection.dispose();
        pauseButton.dispose();
        cardLimitManager.dispose();
    }
}

class CardLimitManager{
    private final Recorder playRecord;
    private final Font cardLimitText;
    private int cardLimit;

    public CardLimitManager(){
        cardLimitText = new Font(22);
        cardLimitText.setColor(Color.WHITE);

        playRecord = new Recorder();
    }

    public void setCardLimit(int cardLimit) {
        this.cardLimit = cardLimit;
    }

    public Recorder getPlayRecord() {
        return playRecord;
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public void draw(SpriteBatch batch){
        cardLimitText.draw(
                batch,
                "cards: "+(cardLimit- playRecord.getRecord()),
                WindowSetting.GRID_X*8,
                WindowSetting.GRID_Y*8+WindowSetting.CENTER_Y);
    }

    public boolean isExceedCardLimit(){
        return playRecord.getRecord() >= cardLimit;
    }

    public void dispose(){
        cardLimitText.dispose();
    }
}

class StageMessage {
    private final Font text;

    enum Condition {WIN, LOSE, NEUTRAL}

    private Condition condition = Condition.NEUTRAL;
    private final TimeHandler transitionHandler;
    private final float x;
    private final float y;

    public StageMessage(float x, float y) {
        this.x = x;
        this.y = y;
        text = new Font(45);
        text.setColor(Color.WHITE);

        transitionHandler = new TimeHandler();
    }

    public final boolean isWin() {
        return condition == Condition.WIN;
    }

    public final boolean isLose() {
        return condition == Condition.LOSE;
    }

    public final void init() {
        condition = Condition.NEUTRAL;
        transitionHandler.resetPastedTime();
    }

    public final void draw(SpriteBatch batch) {
        if (isStageComplete() || isExceedCardLimitAndStageNotComplete()) {
            transitionHandler.updatePastedTime();

            float time = 2.5f;
            if (transitionHandler.getPastedTime() < time)
                text.draw(batch, getMessage(), x, y);
            else {
                if (isStageComplete())
                    condition = Condition.WIN;
                if (isExceedCardLimitAndStageNotComplete())
                    condition = Condition.LOSE;
            }
        }
    }

    private String getMessage() {
        String message = "";
        if (isStageComplete())
            message = "Complete";
        if (isExceedCardLimitAndStageNotComplete())
            message = "Exceed limit";
        return message;
    }

    public boolean isExceedCardLimitAndStageNotComplete() {
        return false;
    }

    public boolean isStageComplete() {
        return false;
    }

    public final void dispose() {
        text.dispose();
    }
}