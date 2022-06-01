package com.arithfighter.not.entity;

import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCardController{
    private final GameCardCollection gameCardCollection;

    public GameCardController(Texture[] textures, FontService fontService){
        gameCardCollection = new GameCardCollection(textures, fontService);
    }

    public GameCard[] getGameCards(){
        return gameCardCollection.getGameCards();
    }

    public boolean isNoGameCardOn() {
        boolean flag = true;
        int length = getGameCards().length;

        for (int i = 0; i < length; i++) {
            if (getGameCards()[i].isOn())
                flag = false;
        }
        return flag;
    }

    public void draw(SpriteBatch batch){
        for (GameCard card : getGameCards())
            card.draw(batch);
    }

    public void touchDown(int x, int y){
        for (GameCard card : getGameCards())
                card.touchDown(x,y);
    }

    public void init(){
        for (GameCard card : getGameCards())
            card.init();
    }
}

class GameCardCollection {
    private final GameCard[] gameCards;

    public GameCardCollection(Texture[] textures, FontService fontService) {
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