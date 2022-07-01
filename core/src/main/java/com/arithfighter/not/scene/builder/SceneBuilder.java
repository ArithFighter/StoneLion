package com.arithfighter.not.scene.builder;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.scene.GameScene;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.scene.SceneModel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class SceneBuilder {
    private final MouseEvent[] mouseEvents;
    private final SceneEvent[] sceneEvents;

    public SceneBuilder() {
        SceneModelAccessor sma = new SceneModelAccessor();
        sma.init();

        MouseEventListProducer m = new MouseEventListProducer();
        m.init(sma.getSceneModels());

        mouseEvents = new MouseEvent[m.getList().size()];
        for (int i = 0; i< mouseEvents.length;i++)
            mouseEvents[i] = m.getList().get(i);

        SceneEventListProducer s = new SceneEventListProducer();
        s.init(sma.getSceneModels());

        sceneEvents = new SceneEvent[s.getList().size()];
        for (int i =0; i< sceneEvents.length;i++)
            sceneEvents[i] = s.getList().get(i);
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
        for (GameScene g:GameScene.values())
            addSceneModel(g);
    }

    private void addSceneModel(GameScene g){
        if(g.getSceneModel()!=null)
            sceneModelList.add(g.getSceneModel());
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

    public void init(SceneModel[] sceneModels){
        for (SceneModel s:sceneModels){
            if (s.getMouseEvent()!=null)
                mouseEventList.add(s.getMouseEvent());
        }
    }

    public List<MouseEvent> getList(){
        return mouseEventList;
    }
}

class SceneEventListProducer{
    private final List<SceneEvent> sceneEventList = new ArrayList<>();

    public void init(SceneModel[] sceneModels){
        for (SceneModel s:sceneModels){
            if (s.getSceneEvent()!=null)
                sceneEventList.add(s.getSceneEvent());
        }
    }

    public List<SceneEvent> getList(){
        return sceneEventList;
    }
}