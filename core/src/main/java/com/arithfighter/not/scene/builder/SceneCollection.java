package com.arithfighter.not.scene.builder;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.scene.*;

class SceneCollection {
    private final Stage stage;
    private final Transition transition;
    private final Option option;
    private final DeckSelection deckSelection;
    private final GameOver gameOver;

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService) {
        stage = new Stage(textureService, soundManager, fontService);

        transition = new Transition(fontService);

        option = new Option(textureService, soundManager, fontService);

        deckSelection = new DeckSelection(textureService, fontService);

        gameOver = new GameOver(textureService, fontService);
    }

    public Stage getStage() {
        return stage;
    }

    public Transition getTransition() {
        return transition;
    }

    public Option getOption() {
        return option;
    }

    public DeckSelection getDeckSelection() {
        return deckSelection;
    }

    public GameOver getGameOver() {
        return gameOver;
    }
}
