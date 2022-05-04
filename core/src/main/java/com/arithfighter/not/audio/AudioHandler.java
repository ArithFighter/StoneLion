package com.arithfighter.not.audio;

import com.arithfighter.not.scene.OptionMenu;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioHandler {
    private final SoundManager soundManager;
    private final MusicManager musicManager;
    private OptionMenu optionMenu;

    public AudioHandler(Sound[] sounds, Music[] music) {
        soundManager = new SoundManager(sounds);

        musicManager = new MusicManager(music);
    }

    public void setOptionMenu(OptionMenu optionMenu) {
        this.optionMenu = optionMenu;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public void setAudioVolume() {
        float soundVolume = optionMenu.getSoundVolume() / 10f;
        soundManager.setVolume(soundVolume);

        float musicVolume = optionMenu.getMusicVolume() / 8f;
        musicManager.setVolume(musicVolume);
    }

    public void dispose() {
        soundManager.dispose();
        musicManager.dispose();
    }
}
