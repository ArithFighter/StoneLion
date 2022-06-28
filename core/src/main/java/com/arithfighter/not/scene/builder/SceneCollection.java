package com.arithfighter.not.scene.builder;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.scene.DeckSelection;
import com.arithfighter.not.scene.scene.Option;
import com.arithfighter.not.scene.scene.Stage;
import com.arithfighter.not.scene.scene.Transition;

class SceneCollection {
    private final Stage stage;
    private final Transition transition;
    private final Option option;
    private final DeckSelection deckSelection;

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService) {
        stage = new Stage(textureService, soundManager, fontService);
        transition = new Transition(fontService);
        option = new Option(textureService, soundManager, fontService);
        deckSelection = new DeckSelection(textureService);
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
}
