package com.arithfighter.not.file.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final Sound[] sounds;
    private float volume;

    public SoundManager(Sound[] sounds){
        this.sounds = sounds;
    }

    public void setVolume(float volume){
        this.volume = volume;
    }

    public void playReturnSound(){
        sounds[2].play(volume);
    }

    public void playScoreSound(){
        sounds[1].stop();
        sounds[1].play(volume);
    }

    public void playAcceptSound(){
        sounds[0].stop();
        sounds[0].play(volume);
    }

    public void playTouchedSound(){
        sounds[3].stop();
        sounds[3].play(volume);
    }

    public void dispose(){
        for (Sound sound:sounds)
            sound.dispose();
    }
}
