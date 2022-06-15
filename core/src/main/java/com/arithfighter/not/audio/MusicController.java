package com.arithfighter.not.audio;

import com.arithfighter.not.scene.GameScene;

public class MusicController {
    private final AudioHandler audioHandler;
    private GameScene gameScene;

    public MusicController(AudioHandler audioHandler) {
        this.audioHandler = audioHandler;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public void playBackgroundMusic() {
        MusicManager musicManager = audioHandler.getMusicManager();

        if (gameScene == GameScene.STAGE)
            musicManager.playTheme();
    }
}
