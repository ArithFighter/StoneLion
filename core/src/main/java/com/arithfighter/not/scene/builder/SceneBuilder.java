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
    private SceneEvent[] sceneEvents;

    public SceneBuilder() {
        SceneModelAccessor sma = new SceneModelAccessor();

        MouseEventListProducer m = new MouseEventListProducer();
        m.init(sma.getSceneModels());

        mouseEvents = new MouseEvent[m.getList().size()];
        for (int i = 0; i< mouseEvents.length;i++)
            mouseEvents[i] = m.getList().get(i);

        initSceneEvents(sma.getSceneModels());
    }

    private void initSceneEvents(SceneModel[] sceneModels){
        List<SceneEvent> sceneEventList = new ArrayList<>();
        for (SceneModel s:sceneModels){
            if (s.getSceneEvent()!=null)
                sceneEventList.add(s.getSceneEvent());
        }
        sceneEvents = new SceneEvent[sceneEventList.size()];
        for (int i =0; i< sceneEvents.length;i++)
            sceneEvents[i] = sceneEventList.get(i);
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

    public SceneModel[] getSceneModels(){
        List<SceneModel> sceneModelList = new ArrayList<>();
        for (GameScene g:GameScene.values()){
            if(g.getSceneModel()!=null)
                sceneModelList.add(g.getSceneModel());
        }

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