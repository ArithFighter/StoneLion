package com.arithfighter.not.animate.se;

public class SpecialEffectService implements SpecialAnimatable {
    private SpecialEffect specialEffect;

    public void setSpecialEffect(SpecialEffect specialEffect) {
        this.specialEffect = specialEffect;
    }

    @Override
    public SpecialEffect getSpecialEffect() {
        return specialEffect;
    }
}
