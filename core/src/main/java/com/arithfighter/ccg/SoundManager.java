package com.arithfighter.ccg;

import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private final Music touchedSound;

    public SoundManager(Music[] sounds){
        touchedSound = sounds[3];
    }

    public void setVolume(float volume){
        touchedSound.setVolume(volume);
    }

    public void playTouchedSound(){
        touchedSound.stop();
        touchedSound.play();
    }

    public void dispose(){
        touchedSound.dispose();
    }
}
