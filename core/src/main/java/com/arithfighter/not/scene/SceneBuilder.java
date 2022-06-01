package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.FontService;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(TextureService textureService, SoundManager soundManager, FontService fontService){
        super(textureService, soundManager, fontService);

        mouseEvents = new MouseEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getResultScreen(),
                getGameOver(),
                getEnding(),
                getOptionMenu()
        };

        sceneEvents = new SceneEvent[]{
                getCharacterMenu(),
                getBetScreen(),
                getStage(),
                getResultScreen(),
                getGameOver(),
                getEnding(),
                getOptionMenu()
        };
    }

    public void setBatch(SpriteBatch batch){
        for (SceneEvent sceneEvent:sceneEvents){
            sceneEvent.setBatch(batch);
        }
    }

    public void setCursorPos(CursorPositionAccessor cursorPos){
        for (MouseEvent mouseEvent:mouseEvents){
            mouseEvent.setCursorPos(cursorPos);
        }
    }

    public MouseEvent[] getMouseEvents(){
        return mouseEvents;
    }

    public void renderScene(GameScene gameScene) {
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].render();
            }
    }

    public void dispose(){
        for (SceneEvent sceneEvent:sceneEvents)
            sceneEvent.dispose();
    }
}

class SceneCollection{
    private final CharacterMenu characterMenu;
    private final Stage stage;
    private final OptionMenu optionMenu;
    private final BetScreen betScreen;
    private final ResultScreen resultScreen;
    private final Ending ending;
    private final GameOver gameOver;

    public SceneCollection(TextureService textureService, SoundManager soundManager, FontService fontService){
        characterMenu = new CharacterMenu(textureService, soundManager, fontService);

        stage = new Stage(textureService, soundManager, fontService);

        optionMenu = new OptionMenu(textureService, soundManager, fontService);

        betScreen = new BetScreen(textureService, soundManager, fontService);

        resultScreen = new ResultScreen(textureService);

        ending = new Ending(textureService);

        gameOver = new GameOver(textureService);
    }

    public void setAudioVolume(GameSave gameSave){
        Preferences pref = gameSave.getPreferences();
        String soundVolumeKey = gameSave.getOptionKeys()[0];
        String musicVolumeKey = gameSave.getOptionKeys()[1];

        optionMenu.setSoundVolume(pref.getInteger(soundVolumeKey));
        optionMenu.setMusicVolume(pref.getInteger(musicVolumeKey));
    }

    public void setHighScore(GameSave gameSave){
        Preferences pref = gameSave.getPreferences();

        for (int i = 0; i<gameSave.getTokenKey().length; i++){
            characterMenu.setSavedTokens(
                    CharacterList.values()[i],
                    pref.getInteger(gameSave.getTokenKey()[i])
            );
        }
    }

    public CharacterMenu getCharacterMenu() {
        return characterMenu;
    }

    public Stage getStage() {
        return stage;
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public BetScreen getBetScreen(){
        return betScreen;
    }

    public ResultScreen getResultScreen(){
        return resultScreen;
    }

    public GameOver getGameOver(){
        return gameOver;
    }

    public Ending getEnding() {
        return ending;
    }
}