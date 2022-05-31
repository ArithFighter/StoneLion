package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.WindowSetting;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.GameCardController;
import com.arithfighter.not.system.RandomNumListProducer;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.widget.BetBrowser;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.dialog.Dialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final BetBrowser betBrowser;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private final NumberBoxQuantityPicker numberBoxQuantityPicker;
    private final int cardLimit = 15;
    private final TextDisplacer textDisplacer;
    private final GameCardController gameCards;
    private int yourTokens = 0;
    private final WarningDialog warningDialog;
    private int totalActiveGames;
    private final FontService fontService;

    public BetScreen(TextureService textureService, SoundManager soundManager) {
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        this.soundManager = soundManager;

        fontService = new FontService();

        betBrowser = new BetBrowser(textures);
        betBrowser.setFont(fontService.getBrowserFont());
        betBrowser.setPosition(500, 200);
        int[] betList = {5, 10, 20, 50, 100};
        betBrowser.setBetList(betList);

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setFont(fontService.getFont22());
        startButton.getButton().setPosition(1000, 80);

        textDisplacer = new TextDisplacer();
        textDisplacer.setFont(fontService.getFont24());

        int[] candidates = new int[]{2,4,7,9};
        numberBoxQuantityPicker = new NumberBoxQuantityPicker(candidates);

        gameCards = new GameCardController(textures);

        warningDialog = new WarningDialog(textures[10]);
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
        int q = 0;

        for (int i = 0; i < gameCards.getGameCards().length; i++) {
            if (gameCards.getGameCards()[i].isOn())
                q+=getQuantityTier(gameCards.getGameCards()[i].getBoxQuantity());
        }
        return betBrowser.getBet() * q;
    }

    private int getQuantityTier(int quantity){
        int value;

        if (quantity<3)
            value = 1;
        else if (quantity<5)
            value = 2;
        else if (quantity<8)
            value = 3;
        else
            value = 4;

        return value;
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
        numberBoxQuantityPicker.init();

        int[] array = numberBoxQuantityPicker.getQuantityArray();

        for (int i = 0; i < array.length; i++)
            gameCards.getGameCards()[i].setBoxQuantity(array[i]);
    }

    @Override
    public void init() {
        startButton.init();
        warningDialog.disable();
        gameCards.init();
    }

    @Override
    public void update() {
        startButton.update();
        betBrowser.update();

        totalActiveGames = getBet() / betBrowser.getBet();

        if (startButton.getButton().isOn()) {
            if (getBet() > yourTokens) {
                warningDialog.setNoEnoughToken();
                warningDialog.setShow();
            }
            if (isNoGameCardOn()) {
                warningDialog.setNoGameChoose();
                warningDialog.setShow();
            }
        }
    }

    private boolean isNoGameCardOn() {
        boolean flag = true;
        int length = gameCards.getGameCards().length;

        for (int i = 0; i < length; i++) {
            if (gameCards.getGameCards()[i].isOn())
                flag = false;
        }
        return flag;
    }

    @Override
    public void draw() {
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

    @Override
    public void dispose() {
        gameCards.dispose();
        warningDialog.dispose();
        fontService.dispose();
    }
}

class WarningDialog {
    private final Dialog dialog;
    private final Font font;
    private boolean isShow = false;

    public WarningDialog(Texture texture) {
        font = new Font(20);
        font.setColor(Color.BLACK);

        dialog = new Dialog(texture, 35);
        dialog.setFont(font);
        dialog.getPoint().set(
                WindowSetting.CENTER_X - dialog.getDialog().getWidget().getWidth() / 2,
                WindowSetting.CENTER_Y - dialog.getDialog().getWidget().getHeight() / 2
        );
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
        dialog.setContent1("You don't have enough ");
        dialog.setContent2("tokens.");
    }

    public void setNoGameChoose() {
        dialog.setContent1("Please choose one game ");
        dialog.setContent2("at least.");
    }

    public void draw(SpriteBatch batch) {
        if (isShow)
            dialog.drawDialog(batch);
    }

    public void dispose() {
        font.dispose();
    }
}

class TextDisplacer {
    private Font font;
    private String cardLimit;
    private String betHint;
    private String tokens;
    private String totalBet;

    public void setFont(Font font) {
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

class FontService{
    private final Font[] fonts;

    public FontService(){
        fonts = new Font[]{
                new Font(22),
                new Font(24),
                new Font(28),
        };
    }

    public Font getFont24() {
        return fonts[0];
    }

    public Font getFont22() {
        return fonts[1];
    }

    public Font getBrowserFont() {
        return fonts[2];
    }

    public void dispose(){
        for (Font f:fonts)
            f.dispose();
    }
}

class NumberBoxQuantityPicker {
    private final int[] quantityCandidates;
    private final RandomNumListProducer indexListProducer;
    private final int quantityArrayLength = 3;

    public NumberBoxQuantityPicker(int[] candidates) {
        quantityCandidates = candidates;

        RandomNumProducer indexPicker = new RandomNumProducer(quantityCandidates.length - 1, 0);

        indexListProducer = new RandomNumListProducer(indexPicker);
        indexListProducer.setMaxQuantity(quantityArrayLength);
    }

    public int[] getQuantityArray() {
        int[] quantityArray = new int[quantityArrayLength];
        List<Integer> indexArray = indexListProducer.getNumbers();

        for (int i = 0; i < quantityArrayLength; i++)
            quantityArray[i] = quantityCandidates[indexArray.get(i)];

        return quantityArray;
    }

    public void init() {
        indexListProducer.clear();
    }
}