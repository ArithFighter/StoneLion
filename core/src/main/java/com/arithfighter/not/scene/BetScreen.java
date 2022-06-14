package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.gamecard.GameCardController;
import com.arithfighter.not.entity.gamecard.GameCardService;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.widget.a1.BetBrowser;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.dialog.Dialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final BetBrowser betBrowser;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private final int cardLimit = 15;
    private final TextDisplacer textDisplacer;
    private final GameCardController gameCards;
    private int yourTokens = 0;
    private final WarningDialog warningDialog;
    private int totalActiveGames;
    private final int[] betList = {5, 10, 20, 50, 100};

    public BetScreen(TextureService textureService, SoundManager soundManager, FontService fontService) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        this.soundManager = soundManager;

        betBrowser = new BetBrowser(textures);
        betBrowser.setFont(fontService.getFont28());
        betBrowser.setPosition(500, 200);
        betBrowser.setBetList(betList);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setFont(fontService.getFont22());
        startButton.getButton().setPosition(1000, 80);

        textDisplacer = new TextDisplacer();
        textDisplacer.setFont(fontService.getFont24());

        GameCardService gameCardService = new GameCardService(textures, fontService);
        gameCards = new GameCardController(gameCardService);

        warningDialog = new WarningDialog(textures[10]);
        warningDialog.setFont(fontService.getFont20());
    }

    public void setYourTokens(int yourTokens) {
        this.yourTokens = yourTokens;
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public int[] getNumberBoxQuantity() {
        int length = gameCards.getGameCards().length;
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            if (gameCards.getGameCards()[i].isOn())
                array[i] = gameCards.getGameCards()[i].getBoxQuantity();
        }
        return array;
    }

    public int getBet() {
        return betBrowser.getBet() * gameCards.getQuantityTier();
    }

    public boolean isStartGame() {
        return startButton.isStart();
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        if (warningDialog.isNotShow()) {
            betBrowser.on(x, y);

            startButton.getButton().on(x, y);

            gameCards.touchDown(x,y);
        } else {
            startButton.getButton().off();
            startButton.init();
            warningDialog.disable();
        }
    }

    @Override
    public void touchDragged() {
        if (warningDialog.isNotShow()) {
            betBrowser.off();

            startButton.getButton().off();
        }
    }

    @Override
    public void touchUp() {
        if (warningDialog.isNotShow()) {
            if (betBrowser.isButtonActive())
                soundManager.playTouchedSound();

            if (startButton.getButton().isOn())
                soundManager.playAcceptSound();

            betBrowser.off();

            startButton.getButton().off();
        }
    }

    public void setNumberBoxQuantity() {
        gameCards.setNumberBoxQuantity();

        setFirstGameCardIfTokensTooFew();
    }

    private void setFirstGameCardIfTokensTooFew(){
        if (yourTokens<betList[1])
            gameCards.getGameCards()[0].setBoxQuantity(gameCards.getFirstGameCardValue());
    }

    @Override
    public void init() {
        startButton.init();
        warningDialog.disable();
        gameCards.init();
    }

    private void update() {
        startButton.update();
        betBrowser.update();

        totalActiveGames = getBet() / betBrowser.getBet();

        handleStartButton();
    }

    private void handleStartButton(){
        if (startButton.getButton().isOn()) {
            if (getBet() > yourTokens) {
                warningDialog.setNoEnoughToken();
                warningDialog.setShow();
            }
            if (gameCards.isNoGameCardOn()) {
                warningDialog.setNoGameChoose();
                warningDialog.setShow();
            }
        }
    }

    @Override
    public void render() {
        update();

        SpriteBatch batch = getBatch();
        TextProvider textProvider = new TextProvider();
        String[] texts = textProvider.getBetScreenTexts();

        setFontManager(texts);

        textDisplacer.draw(batch);

        betBrowser.draw(batch);

        startButton.getButton().draw(batch, texts[2]);

        gameCards.draw(batch);

        warningDialog.draw(batch);
    }

    private void setFontManager(String[] texts){
        String totalBetCalculation =
                "total bet = " + betBrowser.getBet() + " X " + totalActiveGames + " = " + getBet();

        textDisplacer.setCardLimit(texts[0] + cardLimit);
        textDisplacer.setBetHint(texts[1]);
        textDisplacer.setTokens("Your tokens: " + yourTokens);
        textDisplacer.setTotalBet(totalBetCalculation);
    }
}

class WarningDialog {
    private final Dialog dialog;
    private boolean isShow = false;

    public WarningDialog(Texture texture) {
        dialog = new Dialog(texture, 35);
        dialog.getPoint().set(
                WindowSetting.CENTER_X - dialog.getDialog().getWidget().getWidth() / 2,
                WindowSetting.CENTER_Y - dialog.getDialog().getWidget().getHeight() / 2
        );
    }

    public void setFont(Font font){
        font.setColor(Color.BLACK);
        dialog.setFont(font);
    }

    public void setShow() {
        isShow = true;
    }

    public void disable() {
        isShow = false;
    }

    public boolean isNotShow() {
        return !isShow;
    }

    public void setNoEnoughToken() {
        dialog.setOriginString("You don't have enough tokens.");
    }

    public void setNoGameChoose() {
        dialog.setOriginString("Please choose one card at least.");
    }

    public void draw(SpriteBatch batch) {
        if (isShow)
            dialog.drawDialog(batch);
    }
}

class TextDisplacer {
    private Font font;
    private String cardLimit;
    private String betHint;
    private String tokens;
    private String totalBet;

    public void setFont(Font font) {
        font.setColor(Color.WHITE);
        this.font = font;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public void setBetHint(String betHint) {
        this.betHint = betHint;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public void setTotalBet(String totalBet) {
        this.totalBet = totalBet;
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, cardLimit, 800, 650);

        font.draw(batch, tokens, 800, 600);

        font.draw(batch, betHint, 400, 300);

        font.draw(batch, totalBet, 100, 150);
    }
}