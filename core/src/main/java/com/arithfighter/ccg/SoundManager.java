package com.arithfighter.ccg;

import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private final Music touchedSound;
    private final Music acceptSound;

    public SoundManager(Music[] sounds){
        acceptSound = sounds[0];
        touchedSound = sounds[3];
    }

    public void setVolume(float volume){
        acceptSound.setVolume(volume);
        touchedSound.setVolume(volume);
    }

    public void playAcceptSound(){
        acceptSound.play();
    }

    public void playTouchedSound(){
        touchedSound.stop();
        touchedSound.play();
    }

    public void dispose(){
        acceptSound.dispose();
        touchedSound.dispose();
    }
}
