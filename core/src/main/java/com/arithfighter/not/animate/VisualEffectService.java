package com.arithfighter.not.animate;

public class VisualEffectService implements VisualAnimatable{
    private VisualEffect visualEffect;

    public void setVisualEffect(VisualEffect visualEffect) {
        this.visualEffect = visualEffect;
    }

    @Override
    public VisualEffect getVisualEffect() {
        return null;
    }
}
