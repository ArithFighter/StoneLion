package com.arithfighter.not;

import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.MouseEvent;
import com.badlogic.gdx.InputAdapter;

public class MouseAdapter extends InputAdapter {
    private final MouseEvent[] mouseEvents;
    private GameScene gameScene;

    public MouseAdapter(MouseEvent[] mouseEvents) {
        this.mouseEvents = mouseEvents;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < GameScene.values().length; i++) {
            if (gameScene == GameScene.values()[i])
                mouseEvents[i].touchDown();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = 0; i < GameScene.values().length; i++){
            if (gameScene == GameScene.values()[i])
                mouseEvents[i].touchDragged();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < GameScene.values().length; i++) {
            if (gameScene == GameScene.values()[i])
                mouseEvents[i].touchUp();
        }
        return true;
    }
}
