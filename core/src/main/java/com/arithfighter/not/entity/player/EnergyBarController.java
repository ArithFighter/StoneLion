package com.arithfighter.not.entity.player;

import com.arithfighter.not.pojo.Recorder;
import com.arithfighter.not.widget.bar.EnergyBar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnergyBarController {
    private EnergyBar energyBar;
    private final Recorder energyRecorder;
    private int energyGain;

    public EnergyBarController(CharacterList character) {
        energyRecorder = new Recorder();

        setEnergyGain(character);
    }

    public void setEnergyBar(EnergyBar energyBar) {
        this.energyBar = energyBar;
    }

    private void setEnergyGain(CharacterList character) {
        //Rogue gain more energy than other characters when play card
        if (character == CharacterList.ROGUE)
            energyGain = 8;
        else
            energyGain = 5;
    }

    public void reset() {
        energyRecorder.reset();
    }

    public void update() {
        if (energyBar.isNotFull())
            energyRecorder.update(energyGain);
    }

    public void draw(SpriteBatch batch) {
        energyBar.setEnergy(energyRecorder.getRecord());
        energyBar.draw(batch);
    }

    public boolean isMaxEnergy() {
        return energyRecorder.getRecord() >= energyBar.getMax();
    }
}
