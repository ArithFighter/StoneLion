package com.arithfighter.not;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.MouseEvent;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;
import java.util.List;

public class MouseAdapter extends InputAdapter {
    private final MouseEvent[] mouseEvents;
    private final GameScene[] sceneList;
    private GameScene gameScene;

    public MouseAdapter(MouseEvent[] mouseEvents) {
        this.mouseEvents = mouseEvents;

        sceneList = getMouseEventScenes();
    }

    private GameScene[] getMouseEventScenes(){
        List<GameScene> gameSceneList = new ArrayList<>();
        for (GameScene g:GameScene.values()){
            if (g.getSceneModel().getMouseEvent()!=null)
                gameSceneList.add(g);
        }
        GameScene[] mouseEventScenes = new GameScene[gameSceneList.size()];
        for (int i =0;i< mouseEventScenes.length;i++)
            mouseEventScenes[i] = gameSceneList.get(i);

        return mouseEventScenes;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < sceneList.length; i++) {
            if (gameScene == sceneList[i])
                mouseEvents[i].touchDown();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = 0; i < sceneList.length; i++) {
            if (gameScene == sceneList[i])
                mouseEvents[i].touchDragged();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < sceneList.length; i++) {
            if (gameScene == sceneList[i])
                mouseEvents[i].touchUp();
        }
        return true;
    }
}
