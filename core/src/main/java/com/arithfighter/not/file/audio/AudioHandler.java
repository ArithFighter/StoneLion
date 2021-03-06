package com.arithfighter.not.file.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioHandler {
    private final SoundManager soundManager;
    private final MusicManager musicManager;

    public AudioHandler(Sound[] sounds, Music[] music) {
        soundManager = new SoundManager(sounds);

        musicManager = new MusicManager(music);
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public void setSoundVolume(float volume) {
        soundManager.setVolume(volume/10);
    }

    public void setMusicVolume(float volume){
        musicManager.setVolume(volume/10);
    }

    public void dispose() {
        soundManager.dispose();
        musicManager.dispose();
    }
}
