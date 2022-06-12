package com.arithfighter.not.entity.gamecard;

import com.arithfighter.not.system.RandomNumListProducer;
import com.arithfighter.not.system.RandomNumProducer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class GameCardController{
    private final GameCardService gameCardService;
    private final QuantityCandidateService quantityCandidates;
    private final NumberBoxQuantityPicker numberBoxQuantityPicker;

    public GameCardController(GameCardService gameCardService){
        this.gameCardService = gameCardService;

        quantityCandidates = new QuantityCandidateService();

        numberBoxQuantityPicker = new NumberBoxQuantityPicker(quantityCandidates.getCandidates());
    }

    public void initQuantityPicker(){
        numberBoxQuantityPicker.init();
    }

    public int[] getQuantityArray(){
        return numberBoxQuantityPicker.getQuantityArray();
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

    public int getFirstGameCardValue(){
        return quantityCandidates.getCandidates()[0];
    }

    public int getQuantityTier(){
        int q = 0;

        for (int i = 0; i < getGameCards().length; i++) {
            if (getGameCards()[i].isOn()){
                int b = getGameCards()[i].getBoxQuantity();

                q+= quantityCandidates.getQuantityTier(b);
            }
        }

        return q;
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

class QuantityCandidateService{
    private final int[] candidates = new int[]{2,4,7,9};

    public int[] getCandidates(){
        return candidates;
    }

    public int getQuantityTier(int quantity){
        int value = 0;

        for (int i = candidates.length-1;i>=0;i--){
            if (quantity<candidates[i]+1)
                value = i+1;
        }
        return value;
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