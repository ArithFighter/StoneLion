package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.system.RandomNumProducer;
import com.arithfighter.not.widget.BetBrowser;
import com.arithfighter.not.widget.button.Button;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.TextProvider;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;
import java.util.List;

public class BetScreen extends SceneComponent implements SceneEvent, MouseEvent {
    private final BetBrowser betBrowser;
    private final SceneControlButton startButton;
    private final SoundManager soundManager;
    private final TextProvider textProvider;
    private final NumberBoxQuantityGenerator numberBoxQuantityGenerator;
    private final int cardLimit = 15;
    private final FontManager fontManager;
    private final GameCardCollection gameCards;

    public BetScreen(TextureManager textureManager, SoundManager soundManager) {
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        betBrowser = new BetBrowser(textures);
        betBrowser.setPosition(500, 150);
        betBrowser.setBetList(new int[]{5,10,20,50,100});

        startButton = new SceneControlButton(textures[6], 2f);
        startButton.getButton().setPosition(1000, 80);

        fontManager = new FontManager();

        numberBoxQuantityGenerator = new NumberBoxQuantityGenerator();

        gameCards = new GameCardCollection(textures);
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public int getNumberBoxQuantity() {
        return gameCards.getGameCards()[0].getBoxQuantity();
    }

    public int getBet() {
        return betBrowser.getBet();
    }

    public boolean isStartGame() {
        return startButton.isStart();
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cursorPos = getCursorPos();
        int x = cursorPos.getX();
        int y = cursorPos.getY();

        betBrowser.activate(x, y);

        startButton.getButton().on(x, y);
    }

    @Override
    public void touchDragged() {
        betBrowser.deactivate();

        startButton.getButton().off();
    }

    @Override
    public void touchUp() {
        if (betBrowser.isButtonActive())
            soundManager.playTouchedSound();

        if (startButton.getButton().isOn())
            soundManager.playAcceptSound();

        betBrowser.deactivate();

        startButton.getButton().off();

        for (GameCard card: gameCards.getGameCards())
            card.touchDown(getCursorPos().getX(), getCursorPos().getY());
    }

    public void setNumberBoxQuantity() {
        numberBoxQuantityGenerator.init();
        numberBoxQuantityGenerator.update();

        int[] array = numberBoxQuantityGenerator.getQuantityGroup();
        for (int i = 0;i<array.length;i++)
            gameCards.getGameCards()[i].setBoxQuantity(array[i]);
    }

    @Override
    public void init() {
        numberBoxQuantityGenerator.init();
        startButton.init();
        for(GameCard card: gameCards.getGameCards())
            card.init();
    }

    @Override
    public void update() {
        startButton.update();
        betBrowser.update();
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        String[] texts = textProvider.getBetScreenTexts();

        fontManager.setCardLimit(texts[0] + cardLimit);
        fontManager.setBet(texts[1]);
        fontManager.draw(batch);

        betBrowser.draw(batch);

        startButton.getButton().draw(batch, texts[2]);

        for (GameCard card: gameCards.getGameCards())
            card.draw(batch);
    }

    @Override
    public void dispose() {
        fontManager.dispose();
        betBrowser.dispose();
        startButton.dispose();
        for (GameCard card: gameCards.getGameCards())
            card.dispose();
    }
}

class GameCardCollection {
    private final GameCard[] gameCards;

    public GameCardCollection(Texture[] textures) {
        int totalCards = 3;
        gameCards = new GameCard[totalCards];

        String[] codeArray = {"A", "B", "C"};
        for (int i = 0; i < totalCards; i++){
            gameCards[i] = new GameCard(textures[1]);
            gameCards[i].setCardCode(codeArray[i]);
        }

        Point point = new Point(100, 400);
        gameCards[0].setPoint(point);

        int margin = 15;
        gameCards[1].setPoint(
                new Point(
                        gameCards[0].getRectangle().getWidth() + point.getX() + margin,
                        point.getY()
                )
        );
        gameCards[2].setPoint(
                new Point(
                        gameCards[0].getRectangle().getWidth()*2 + point.getX() + margin*2,
                        point.getY()
                )
        );
    }

    public GameCard[] getGameCards() {
        return gameCards;
    }
}

class GameCard {
    private final Font codeFont;
    private final Button gameCard;
    private final Rectangle rectangle;
    private final int fontSize;
    private Point point;
    private int boxQuantity;
    private String cardCode;

    public GameCard(Texture texture) {
        gameCard = new Button(texture, 3f);

        rectangle = new Rectangle(texture.getWidth() * 3, texture.getHeight() * 3);

        fontSize = 36;
        codeFont = new Font(fontSize);
        codeFont.setColor(Color.PURPLE);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setPoint(Point point) {
        this.point = point;
        gameCard.setPosition(point.getX(), point.getY());
    }

    public Point getPoint() {
        return point;
    }

    public int getBoxQuantity() {
        return boxQuantity;
    }

    public void setBoxQuantity(int boxQuantity) {
        this.boxQuantity = boxQuantity;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public void draw(SpriteBatch batch) {
        gameCard.draw(
                batch,
                boxQuantity + " box"
        );
        codeFont.draw(
                batch,
                cardCode,
                point.getX() + rectangle.getWidth() - fontSize,
                point.getY() + rectangle.getHeight() - fontSize / 2f
        );
    }

    public void init() {
        gameCard.off();
    }

    public void touchDown(float x, float y) {
        if (gameCard.isOnButton(x, y)) {
            if (gameCard.isOn())
                gameCard.off();
            else
                gameCard.on(x, y);
        }
    }

    public void dispose() {
        codeFont.dispose();
        gameCard.dispose();
    }
}

class FontManager {
    private final Font cardLimitFont;
    private final Font betFont;
    private String cardLimit;
    private String bet;

    public FontManager() {
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

    public void draw(SpriteBatch batch) {
        cardLimitFont.draw(batch, cardLimit, 900, 650);

        betFont.draw(batch, bet, 400, 250);
    }

    public void dispose() {
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

        indexPicker = new RandomNumProducer(quantityCandidates.length - 1, 0);
    }

    public int[] getQuantityGroup() {
        int[] quantityArray = new int[quantityArrayLength];

        for (int i = 0; i < quantityArrayLength; i++)
            quantityArray[i] = quantityGroup.get(i);

        return quantityArray;
    }

    public void update() {
        while (quantityGroup.size() < quantityArrayLength) {
            int candidateCursor = indexPicker.getRandomNum();
            quantityGroup.add(quantityCandidates[candidateCursor]);
        }
    }

    public void init() {
        quantityGroup.clear();
    }
}