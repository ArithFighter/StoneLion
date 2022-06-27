package com.arithfighter.not.pojo;

public class OptionManager {
    private float soundVolume;
    private float musicVolume;

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }
}
