package com.arithfighter.not.scene.builder;

import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.file.audio.SoundManager;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.scene.*;

public class SceneCollection {
    private final Stage stage;
    private final Transition transition;
    private final Option option;
    private final DeckSelection deckSelection;
    private final GameOver gameOver;
    private final EnchantmentMap enchantmentMap;

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService) {
        stage = new Stage(textureService, soundManager, fontService);

        transition = new Transition(textureService,fontService);

        option = new Option(textureService, soundManager, fontService);

        deckSelection = new DeckSelection(textureService, fontService);

        gameOver = new GameOver(textureService, fontService);

        enchantmentMap = new EnchantmentMap(textureService, fontService);
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

    public EnchantmentMap getEnchantmentMap() {
        return enchantmentMap;
    }
}
