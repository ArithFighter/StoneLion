package com.arithfighter.not.scene;

import com.arithfighter.not.GameSave;
import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureManager;
import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.entity.player.CharacterList;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneBuilder extends SceneCollection{
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder(TextureManager textureManager, SoundManager soundManager){
        super(textureManager, soundManager);

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

    public void updateScene(GameScene gameScene){
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].update();
            }
    }

    public void drawScene(GameScene gameScene) {
        for (int i = 0; i < GameScene.values().length; i++)
            if (gameScene == GameScene.values()[i]){
                sceneEvents[i].draw();
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

    public SceneCollection(TextureManager textureManager, SoundManager soundManager){
        characterMenu = new CharacterMenu(textureManager, soundManager);

        stage = new Stage(textureManager, soundManager);

        optionMenu = new OptionMenu(textureManager, soundManager);

        betScreen = new BetScreen(textureManager, soundManager);

        resultScreen = new ResultScreen(textureManager);

        ending = new Ending(textureManager);

        gameOver = new GameOver(textureManager);
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

        for (int i = 0;i<gameSave.getHighScoreKey().length;i++){
            characterMenu.setHighScore(
                    CharacterList.values()[i],
                    pref.getInteger(gameSave.getHighScoreKey()[i])
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