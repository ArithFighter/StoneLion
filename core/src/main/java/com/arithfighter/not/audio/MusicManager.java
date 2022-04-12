package com.arithfighter.not.audio;

import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private final Music menuMusic;
    private final Music gameTheme;

    public MusicManager(Music[] musics){
        menuMusic = musics[0];
        gameTheme = musics[1];
    }

    public void setVolume(float volume){
        menuMusic.setVolume(volume);
        gameTheme.setVolume(volume);
    }

    public void playMenuMusic(){
        gameTheme.dispose();
        menuMusic.play();
    }

    public void playTheme(){
        menuMusic.dispose();
        gameTheme.play();
    }

    public void dispose(){
        menuMusic.dispose();
        gameTheme.dispose();
    }
}
