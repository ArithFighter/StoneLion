package com.arithfighter.ccg;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Sound touching;
    private final Sound accepting;
    private final Sound scoring;
    private final Sound returning;
    private float volume;

    public SoundManager(Sound[] sounds){
        accepting = sounds[0];
        scoring = sounds[1];
        returning = sounds[2];
        touching = sounds[3];
    }

    public void setVolume(float volume){
        this.volume = volume;
    }

    public void playReturnSound(){
        returning.play(volume);
    }

    public void playScoreSound(){
        scoring.stop();
        scoring.play(volume);
    }

    public void playAcceptSound(){
        accepting.stop();
        accepting.play(volume);
    }

    public void playTouchedSound(){
        touching.stop();
        touching.play(volume);
    }

    public void dispose(){
        accepting.dispose();
        touching.dispose();
    }
}
