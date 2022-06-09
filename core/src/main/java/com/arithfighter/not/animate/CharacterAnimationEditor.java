package com.arithfighter.not.animate;

public class CharacterAnimationEditor implements CharacterAnimatable {
    private CharacterAnimation animation;

    public void setAnimation(CharacterAnimation animation) {
        this.animation = animation;
    }

    @Override
    public CharacterAnimation getAnimation() {
        return animation;
    }
}
