package com.arithfighter.not.scene.controller;

import com.arithfighter.not.entity.game.GameVariation;
import com.arithfighter.not.entity.numberbox.NumberBoxService;
import com.arithfighter.not.system.RandomNumProducer;

class StageDeployer {
    private int cursor = 1;
    private final RandomNumProducer randomQuantity;

    StageDeployer() {
        int maxQuantity = new NumberBoxService().getQuantity();
        randomQuantity = new RandomNumProducer(maxQuantity, 6);
    }

    public void init(){
        cursor = 1;
    }

    public void update() {
        cursor++;
    }

    public int getQuantity() {
        return randomQuantity.getRandomNum();
    }

    public GameVariation getVariation() {
        GameVariation gv;

        if (cursor < 2)
            gv = GameVariation.STANDARD;
        else if (cursor < 3)
            gv = GameVariation.FOG;
        else if (cursor < 4)
            gv = GameVariation.TABOO;
        else
            gv = GameVariation.TRANSFORM;

        return gv;
    }

    public boolean isReachFinalStage(int count){
        return cursor>=count;
    }
}
