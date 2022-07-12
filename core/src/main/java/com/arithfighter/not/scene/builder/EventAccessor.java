package com.arithfighter.not.scene.builder;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.scene.SceneModel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class EventAccessor {
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public EventAccessor() {
        SceneModelAccessor sma = new SceneModelAccessor();
        sma.init();

        MouseEventListProducer m = new MouseEventListProducer();
        mouseEvents = m.getMouseEvents(sma.getSceneModels());

        SceneEventListProducer s = new SceneEventListProducer();
        sceneEvents = s.getSceneEvents(sma.getSceneModels());
    }

    public void setBatch(SpriteBatch batch) {
        for (SceneEvent sceneEvent : sceneEvents)
            sceneEvent.setBatch(batch);
    }

    public void setCursorPos(CursorPositionAccessor cursorPos) {
        for (MouseEvent mouseEvent : mouseEvents)
            mouseEvent.setCursorPos(cursorPos);
    }

    public MouseEvent[] getMouseEvents() {
        return mouseEvents;
    }

    public SceneEvent[] getSceneEvents() {
        return sceneEvents;
    }
}

class SceneModelAccessor{
    private final List<SceneModel> sceneModelList = new ArrayList<>();

    public void init(){
        for (GameScene g:GameScene.values()){
            if(g.getSceneModel()!=null)
                sceneModelList.add(g.getSceneModel());
        }
    }

    public SceneModel[] getSceneModels(){
        SceneModel[] sceneModels = new SceneModel[sceneModelList.size()];

        for (int i = 0; i<sceneModels.length;i++)
            sceneModels[i] = sceneModelList.get(i);

        return sceneModels;
    }
}

class MouseEventListProducer{
    private final List<MouseEvent> mouseEventList = new ArrayList<>();

    private void init(SceneModel[] sceneModels){
        for (SceneModel s:sceneModels){
            if (s.getMouseEvent()!=null)
                mouseEventList.add(s.getMouseEvent());
        }
    }

    public MouseEvent[] getMouseEvents(SceneModel[] sceneModels){
        init(sceneModels);

        MouseEvent[] mouseEvents = new MouseEvent[mouseEventList.size()];
        for (int i = 0; i< mouseEvents.length;i++)
            mouseEvents[i] = mouseEventList.get(i);

        return mouseEvents;
    }
}

class SceneEventListProducer{
    private final List<SceneEvent> sceneEventList = new ArrayList<>();

    private void init(SceneModel[] sceneModels){
        for (SceneModel s:sceneModels){
            if (s.getSceneEvent()!=null)
                sceneEventList.add(s.getSceneEvent());
        }
    }

    public SceneEvent[] getSceneEvents(SceneModel[] sceneModels){
        init(sceneModels);

        SceneEvent[] sceneEvents = new SceneEvent[sceneEventList.size()];
        for (int i =0; i< sceneEvents.length;i++)
            sceneEvents[i] = sceneEventList.get(i);

        return sceneEvents;
    }
}