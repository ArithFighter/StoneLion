package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.widget.button.PanelButton;
import com.badlogic.gdx.graphics.Texture;

public class DeckSelection extends SceneComponent implements SceneEvent, MouseEvent {
    private PanelButton knightButton;
    private PanelButton rogueButton;

    public DeckSelection(TextureService textureService){
        Texture[] panels = textureService.getTextures(textureService.getKeys()[2]);

        knightButton = new PanelButton(panels[0], 1f);
        knightButton.setPosition(350,350);

        rogueButton = new PanelButton(panels[1], 1f);
        rogueButton.setPosition(600,350);
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cpa = getCursorPos();
        int x = cpa.getX();
        int y = cpa.getY();

        knightButton.on(x,y);
        rogueButton.on(x,y);
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
    }

    private void offButtons(){
        knightButton.off();
        rogueButton.off();
    }

    @Override
    public void render() {
        knightButton.draw(getBatch());
        rogueButton.draw(getBatch());
    }
}
