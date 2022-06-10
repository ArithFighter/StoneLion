package com.arithfighter.not.entity.gamecard;

import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;

public class GameCardService {
    private final GameCard[] gameCards;

    public GameCardService(Texture[] textures, FontService fontService) {
        int totalCards = 3;
        gameCards = new GameCard[totalCards];

        String[] codeArray = {"A", "B", "C"};
        for (int i = 0; i < totalCards; i++) {
            gameCards[i] = new GameCard(textures[1], fontService.getFont22(), fontService.getFont36());
            gameCards[i].setCardCode(codeArray[i]);
        }

        Point point = new Point(100, 400);
        gameCards[0].setPoint(point);

        int margin = 15;
        float x = gameCards[0].getRectangle().getWidth() + margin;

        for (int i = 1; i < totalCards; i++)
            gameCards[i].setPoint(new Point(point.getX() + x * i, point.getY()));
    }

    public GameCard[] getGameCards() {
        return gameCards;
    }
}
