package com.arithfighter.not.entity.gamecard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCardController{
    private final GameCardService gameCardService;

    public GameCardController(GameCardService gameCardService){
        this.gameCardService = gameCardService;
    }

    public GameCard[] getGameCards(){
        return gameCardService.getGameCards();
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