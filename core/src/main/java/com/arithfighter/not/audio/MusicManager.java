package com.arithfighter.not.audio;

import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private final Music[] music;

    public MusicManager(Music[] music){
        this.music = music;
    }

    public void setVolume(float volume){
        for(Music m: music)
            m.setVolume(volume);
    }

    public void playMenuMusic(){
        music[1].dispose();
        music[0].play();
    }

    public void playTheme(){
        music[0].dispose();
        music[1].play();
    }

    public void dispose(){
        for (Music m: music)
            m.dispose();
    }
}
