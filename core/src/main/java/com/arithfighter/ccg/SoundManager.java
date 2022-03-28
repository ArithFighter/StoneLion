package com.arithfighter.ccg;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Sound touchedSound;
    private final Sound acceptSound;
    private final Sound scoreSound;
    private float volume;

    public SoundManager(Sound[] sounds){
        acceptSound = sounds[0];
        scoreSound = sounds[1];
        touchedSound = sounds[3];
    }

    public void setVolume(float volume){
        this.volume = volume;
    }

    public void playScoreSound(){
        scoreSound.stop();
        scoreSound.play(volume);
    }

    public void playAcceptSound(){
        acceptSound.stop();
        acceptSound.play(volume);
    }

    public void playTouchedSound(){
        touchedSound.stop();
        touchedSound.play(volume);
    }

    public void dispose(){
        acceptSound.dispose();
        touchedSound.dispose();
    }
}
