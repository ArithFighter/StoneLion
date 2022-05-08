package com.arithfighter.not.entity;

import com.arithfighter.not.file.AnimationProcessor;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.Player;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.stagecomponent.Gecko;
import com.arithfighter.not.widget.stagecomponent.SumBox;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import static com.arithfighter.not.WindowSetting.*;

public class GamePlayComponent {
    private final Gecko gecko;
    private final NumberBoxDisplacer numberBoxDisplacer;
    private Player player;
    private final SumBox sumBox;
    private final Recorder scoreRecord = new Recorder();
    private final CardAnimation cardFadeOut;
    private final CardAnimation cardReset;
    private boolean isCardDrag = false;
    private LoopAnimation geckoBlink;

    public GamePlayComponent(Texture[] textures, Texture[] spriteSheets, SoundManager soundManager) {
        cardFadeOut = new CardAnimation(spriteSheets[1]);

        cardReset = new CardAnimation(spriteSheets[1]);

        int geckoX = CENTER_X + GRID_X * 5;
        int geckoY = GRID_Y * 6;

        gecko = new Gecko(spriteSheets[2]) {
            @Override
            public void initCardPosition() {
                cardReset.setStart();
                player.initHand();
            }

            @Override
            public void checkCardPlayed() {
                player.playCard();
                cardFadeOut.setStart();
            }
        };
        gecko.setPosition(geckoX, geckoY);

        numberBoxDisplacer = new NumberBoxDisplacer(textures) {
            @Override
            public void doWhenSumAndNumMatched() {
                scoreRecord.update(1);
                soundManager.playScoreSound();
            }
        };

        sumBox = new SumBox(textures[2]);
        sumBox.setPosition(CENTER_X + GRID_X * 6, GRID_Y * 11);

        geckoBlink = new LoopAnimation(spriteSheets[3], gecko.getScale(), 2,3);
        geckoBlink.setDrawPoint(new Point(geckoX, geckoY));
    }

    public int getScore() {
        return scoreRecord.getRecord();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        scoreRecord.reset();
        numberBoxDisplacer.init();
        player.init();
        cardFadeOut.init();
        cardReset.init();
    }

    public NumberBoxDisplacer getNumberBoxDisplacer() {
        return numberBoxDisplacer;
    }

    public void setNumberQuantity(int quantity) {
        numberBoxDisplacer.setBoxQuantity(quantity);
    }

    public void update(int mouseX, int mouseY) {
        numberBoxDisplacer.update(player.getSum());

        player.updateWhenTouchCard(mouseX, mouseY);
    }

    public void draw(SpriteBatch batch) {
        gecko.draw(batch);

        numberBoxDisplacer.draw(batch);

        sumBox.changeColor(player.getCondition());
        sumBox.draw(player.getSum(), batch);

        player.draw(batch);

        cardFadeOut.draw(batch, 0.4f, AnimationPos.CENTER);

        cardReset.draw(batch, 0.4f, AnimationPos.TOP_RIGHT);

        geckoBlink.draw(batch, 5);
    }

    public void touchDown(int mouseX, int mouseY) {
        isCardDrag = false;
        player.activateCard(mouseX, mouseY);
        cardReset.setLastMousePoint(player.getActiveCard().getInitPoint());
    }

    public void touchDragged(int mouseX, int mouseY) {
        if (player.isCardActive())
            isCardDrag = true;
        player.updateWhenDrag(mouseX, mouseY);
    }

    public void touchUp(int mouseX, int mouseY) {
        if (isCardDrag) {
            gecko.playCardToBasket(mouseX, mouseY);
            cardFadeOut.setLastMousePoint(new Point(mouseX, mouseY));
        }
    }

    public void dispose() {
        numberBoxDisplacer.dispose();
        sumBox.dispose();
    }
}

class LoopAnimation {
    private final AnimationProcessor processor;
    private final TimeHandler timeHandler;
    private Point drawPoint;

    public LoopAnimation(Texture spriteSheet, int scale, int cols, int rows){
        processor = new AnimationProcessor(spriteSheet, cols,rows);
        processor.setScale(scale);
        processor.setSpeed(0.08f);

        timeHandler = new TimeHandler();
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public void draw(SpriteBatch batch, float duration) {
        processor.setBatch(batch);

        if (timeHandler.getPastedTime()<duration) {
            handleAnimation();
        }else
            resetTimeAndAnimation();
    }

    private void handleAnimation() {
        timeHandler.updatePastedTime();

        processor.setPoint(drawPoint);
        processor.draw(drawPoint.getX(), drawPoint.getY());
    }

    private void resetTimeAndAnimation(){
        timeHandler.resetPastedTime();
        processor.init();
    }
}

class CardAnimation {
    private final AnimationProcessor processor;
    private boolean isStart = false;
    private final TimeHandler timeHandler;
    private Point lastMousePoint;
    private Point drawPoint;

    public CardAnimation(Texture texture) {
        processor = new AnimationProcessor(texture, 3, 3);
        processor.setScale(16);
        processor.setSpeed(0.08f);
        timeHandler = new TimeHandler();
        drawPoint = new Point();
        lastMousePoint = new Point();
    }

    public void setLastMousePoint(Point point) {
        lastMousePoint = point;
    }

    public void setStart() {
        isStart = true;
    }

    public void init() {
        isStart = false;
    }

    public void draw(SpriteBatch batch, float duration, AnimationPos pos) {
        processor.setBatch(batch);
        if (isStart) {
            handleAnimation(duration, pos);
        } else {
            resetTimeAndAnimation();
        }
    }

    private void handleAnimation(float duration, AnimationPos pos) {
        timeHandler.updatePastedTime();

        if (timeHandler.getPastedTime() < duration) {
            processor.setPoint(lastMousePoint);
            drawPoint = getPoint(pos);
            processor.draw(drawPoint.getX(), drawPoint.getY());
        } else
            isStart = false;
    }

    private void resetTimeAndAnimation(){
        timeHandler.resetPastedTime();
        processor.init();
    }

    private Point getPoint(AnimationPos pos) {
        float x = 0;
        float y = 0;

        if (pos == AnimationPos.CENTER) {
            x = processor.getPoint().getX() - processor.getWidth() / 2;
            y = processor.getPoint().getY() - processor.getHeight() / 2;
        }
        if (pos == AnimationPos.TOP_RIGHT) {
            x = processor.getPoint().getX();
            y = processor.getPoint().getY();
        }

        return new Point(x, y);
    }
}

enum AnimationPos {
    CENTER, TOP_RIGHT
}