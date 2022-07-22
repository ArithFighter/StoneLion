package com.arithfighter.not.scene.scene;

import com.arithfighter.not.entity.game.RemainPlayTimeManager;
import com.arithfighter.not.file.texture.TextureGetter;
import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.file.audio.SoundManager;
import com.arithfighter.not.entity.*;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.game.Game;
import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.pause.PauseMenu;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.time.Timer;
import com.arithfighter.not.widget.a1.Mask;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stage extends SceneComponent implements SceneEvent, MouseEvent {
    private final Game game;
    private final SceneControlButton pauseButton;
    private final PauseMenu pauseMenu;
    private final Timer timer;
    private GameVariation gameVariation = GameVariation.STANDARD;
    private int bellQuantity = 6;
    private final RemainPlayTimeManager remainPlayTimeManager;
    private Background background;
    private EnchantmentPillar enchantmentPillar;
    private Mask backgroundMask;

    public Stage(TextureService textureService, SoundManager soundManager, FontService fontService) {
        Recorder remainCardRecorder = new Recorder(30);
        remainPlayTimeManager = new RemainPlayTimeManager(remainCardRecorder, fontService.getFont32());

        game = new Game(textureService, soundManager, fontService.getFont32());
        game.setCharacter(CharacterList.SNAKE);
        game.getPlayerService().setRemainPlayTimeRecorder(remainPlayTimeManager.getRemainPlayTimeRecorder());

        timer = new Timer();
        timer.setTime(1.5f);

        TextureGetter tg = new TextureGetter(textureService);

        Texture[] pauseGui = {
                tg.getGuiMap().get("gui/BoardArea.png"),
                tg.getGuiMap().get("gui/Button1.png"),
                tg.getGuiMap().get("gui/dialog.png")
        };
        pauseMenu = new PauseMenu(pauseGui, soundManager, fontService.getFont20());

        pauseButton = new SceneControlButton(tg.getGuiMap().get("gui/Button1.png"), 1.5f);
        pauseButton.getButton().setPosition(1100, 620);
        pauseButton.getButton().setFont(fontService.getFont22());

        createBackGround(tg);
    }

    public void createBackGround(TextureGetter tg){
        background = new Background(tg.getObjectMap().get("object/bamboo-forest.png"));

        Texture[] enchantmentT = {
                tg.getObjectMap().get("object/pillar-left.png"),
                tg.getObjectMap().get("object/pillar-right.png"),
                tg.getObjectMap().get("object/single-rope.png")
        };
        enchantmentPillar = new EnchantmentPillar(enchantmentT);

        backgroundMask = new Mask(tg.getGuiMap().get("gui/w16h9-block.png"), 80);
        backgroundMask.setPosition(0,0);
    }

    public RemainPlayTimeManager getRemainCardManager() {
        return remainPlayTimeManager;
    }

    public void setGameVariation(GameVariation gameVariation) {
        this.gameVariation = gameVariation;
    }

    public void setBellQuantity(int bellQuantity) {
        this.bellQuantity = bellQuantity;
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

    public boolean isChangeScene(){
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

        background.draw(batch);

        enchantmentPillar.draw(batch);

        backgroundMask.draw(batch, 0.7f);

        game.setBatch(batch);
        game.draw(gameVariation, bellQuantity);

        if (pauseButton.isStart()) {
            pauseMenu.draw(batch);
        } else
            pauseButton.getButton().draw(batch, "Pause");

        float candleH = remainPlayTimeManager.getRemainPlayTimeRecorder().getRecord()*4;
        game.getCandleStick().setCandleHeight(candleH);
    }

    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (pauseButton.isStart())
            pauseMenu.touchDown(x, y);
        else {
            if (!game.isAllNumZero()){
                pauseButton.getButton().onWhenIsOnButton(x, y);
                game.touchDown(x, y);
            }
        }
    }

    public void touchDragged() {
        if (pauseButton.isStart())
            pauseMenu.touchDragged();
        else {
            if (!game.isAllNumZero()){
                pauseButton.getButton().off();
                game.touchDragged(getCursorPos().getX(), getCursorPos().getY());
            }
        }
    }

    public void touchUp() {
        if (pauseButton.isStart())
            pauseMenu.touchUp();
        else {
            if (!game.isAllNumZero()){
                pauseButton.getButton().off();
                game.touchUp(getCursorPos().getX(), getCursorPos().getY());
            }
        }
    }
}