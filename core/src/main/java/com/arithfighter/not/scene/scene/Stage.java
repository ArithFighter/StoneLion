package com.arithfighter.not.scene.scene;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.game.Game;
import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.game.RemainCardManager;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.time.Timer;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends SceneComponent implements SceneEvent, MouseEvent {
    private final Game game;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;
    private final Timer timer;
    private GameVariation gameVariation = GameVariation.STANDARD;
    private int boxQuantity = 6;
    private final RemainCardManager remainCardManager;
    private final CandleStick candleStick;

    public Stage(TextureService textureService, SoundManager soundManager, FontService fontService) {
        Texture[] gui = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] object = textureService.getTextures(textureService.getKeys()[4]);

        remainCardManager = new RemainCardManager(new Recorder(50), fontService.getFont32());

        game = new Game(textureService, soundManager, fontService.getFont32());
        game.setCharacter(CharacterList.SNAKE);
        game.getPlayerService().setRemainCardRecorder(remainCardManager.getRemainCardRecorder());

        Texture[] pauseGui = {gui[1], gui[6], gui[9]};
        pauseMenu = new PauseMenu(pauseGui, soundManager, fontService.getFont20());

        pauseButton = new SceneControlButton(gui[6], 1.8f);
        pauseButton.getButton().setPosition(1000, 600);
        pauseButton.getButton().setFont(fontService.getFont22());

        timer = new Timer();
        timer.setTime(1.5f);

        Texture[] candleT = {object[2],object[3], object[4]};
        candleStick = new CandleStick(candleT);
        candleStick.setPoint(new Point(50,50));
    }

    public RemainCardManager getRemainCardManager() {
        return remainCardManager;
    }

    public void setGameVariation(GameVariation gameVariation) {
        this.gameVariation = gameVariation;
    }

    public void setBoxQuantity(int boxQuantity) {
        this.boxQuantity = boxQuantity;
    }

    public void setDeck(CharacterList characterList){
        game.setCharacter(characterList);
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public void init() {
        game.init();
        pauseMenu.init();
        pauseButton.init();
        timer.init();
    }

    public boolean isComplete(){
        return timer.isTimesOut();
    }

    private void update() {
        if (pauseButton.isStart()) {
            pauseMenu.update();

            if (pauseMenu.isResume()) {
                pauseButton.init();
                pauseMenu.init();
            }
        } else {
            pauseButton.update();

            game.update(getCursorPos().getX(), getCursorPos().getY());

            if (game.isAllNumZero())
                timer.update();
        }
    }

    public void render() {
        update();

        SpriteBatch batch = getBatch();

        game.setBatch(batch);
        game.draw(gameVariation, boxQuantity);

        if (pauseButton.isStart()) {
            pauseMenu.draw(batch);
        } else
            pauseButton.getButton().draw(batch, "Pause");

        remainCardManager.draw(batch, 100,100);

        candleStick.draw(batch);
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (pauseButton.isStart())
            pauseMenu.touchDown(x, y);
        else {
            pauseButton.getButton().on(x, y);
            game.touchDown(x, y);
        }
    }

    public void touchDragged() {
        if (pauseButton.isStart())
            pauseMenu.touchDragged();
        else {
            pauseButton.getButton().off();
            game.touchDragged(getCursorPos().getX(), getCursorPos().getY());
        }
    }

    public void touchUp() {
        if (pauseButton.isStart())
            pauseMenu.touchUp();
        else {
            pauseButton.getButton().off();
            game.touchUp(getCursorPos().getX(), getCursorPos().getY());
        }
    }
}