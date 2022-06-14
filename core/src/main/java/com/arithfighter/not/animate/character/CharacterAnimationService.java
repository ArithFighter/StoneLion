package com.arithfighter.not.animate.character;

public class CharacterAnimationService implements CharacterAnimatable {
    private CharacterAnimation animation;

    public void setAnimation(CharacterAnimation animation) {
        this.animation = animation;
    }

    @Override
    public CharacterAnimation getAnimation() {
        return animation;
    }
}
