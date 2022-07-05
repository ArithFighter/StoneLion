package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.widget.button.PanelButton;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DeckSelection extends SceneComponent implements SceneEvent, MouseEvent {
    private final PanelButton snakeButton;
    private final PanelButton craneButton;
    private final Font font;
    private final SceneControlButton startButton;
    private String deckName = "";
    private final Rectangle grid;

    public DeckSelection(TextureService textureService, FontService fontService){
        TextureRegion[] widgets = textureService.getTextureRegionMap().get(textureService.getKeys()[0]);
        TextureRegion[] object = textureService.getTextureRegionMap().get(textureService.getKeys()[2]);

        font = fontService.getFont36();

        LayoutSetter layout = new LayoutSetter();
        layout.setGrid(7,4);
        grid = layout.getGrid();

        snakeButton = new PanelButton(object[10], 5f);
        snakeButton.setPosition(grid.getWidth()*2, grid.getHeight()*2);

        craneButton = new PanelButton(object[9], 5f);
        craneButton.setPosition(grid.getWidth()*3.5f,grid.getHeight()*2);

        Font f = fontService.getFont22();
        f.setColor(Color.WHITE);

        startButton = new SceneControlButton(widgets[6], 1.8f);
        startButton.getButton().setFont(f);
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cpa = getCursorPos();
        int x = cpa.getX();
        int y = cpa.getY();

        snakeButton.on(x,y);
        craneButton.on(x,y);

        startButton.getButton().on(x,y);
    }

    @Override
    public void touchDragged() {
        offButtons();
    }

    @Override
    public void touchUp() {
        offButtons();
    }

    @Override
    public void init() {
        offButtons();
        startButton.init();
    }

    private void offButtons(){
        snakeButton.off();
        craneButton.off();
        startButton.getButton().off();
    }

    @Override
    public void render() {
        if (snakeButton.isOn())
            deckName = CharacterList.values()[0].toString();
        if (craneButton.isOn())
            deckName = CharacterList.values()[1].toString();

        font.draw(getBatch(), deckName, grid.getWidth()*5,grid.getHeight()*2);

        snakeButton.draw(getBatch());
        craneButton.draw(getBatch());

        setStartButtonPosition();

        startButton.update();
        startButton.getButton().draw(getBatch(), "Start");
    }

    private void setStartButtonPosition(){
        if (deckName.equals(""))
            startButton.getButton().setPosition(0,-grid.getHeight());
        else
            startButton.getButton().setPosition(grid.getWidth()*5, grid.getHeight());
    }

    public boolean isStartGame(){
        return startButton.isStart();
    }

    public int getDeckIndex(){
        int index = 0;

        for (int i = 0;i<CharacterList.values().length;i++){
            if (deckName.equals(CharacterList.values()[i].toString()))
                index = i;
        }
        return index;
    }
}
