package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.entity.player.CharacterList;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.widget.button.PanelButton;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class DeckSelection extends SceneComponent implements SceneEvent, MouseEvent {
    private final PanelButton knightButton;
    private final PanelButton rogueButton;
    private final Font font;
    private final SceneControlButton startButton;
    private String deckName = "";

    public DeckSelection(TextureService textureService, FontService fontService){
        Texture[] widgets = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] panels = textureService.getTextures(textureService.getKeys()[2]);
        font = fontService.getFont36();

        knightButton = new PanelButton(panels[0], 1f);
        knightButton.setPosition(350,350);

        rogueButton = new PanelButton(panels[1], 1f);
        rogueButton.setPosition(600,350);

        Font f = fontService.getFont22();
        f.setColor(Color.WHITE);

        startButton = new SceneControlButton(widgets[6], 1.8f);
        startButton.getButton().setFont(f);
        startButton.getButton().setPosition(800,100);
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor cpa = getCursorPos();
        int x = cpa.getX();
        int y = cpa.getY();

        knightButton.on(x,y);
        rogueButton.on(x,y);
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
        knightButton.off();
        rogueButton.off();
        startButton.getButton().off();
    }

    @Override
    public void render() {
        if (knightButton.isOn())
            deckName = CharacterList.values()[0].toString();
        if (rogueButton.isOn())
            deckName = CharacterList.values()[1].toString();

        font.draw(getBatch(), deckName, 800,300);

        knightButton.draw(getBatch());
        rogueButton.draw(getBatch());

        startButton.update();
        startButton.getButton().draw(getBatch(), "Start");
    }

    public boolean isStartGame(){
        return startButton.isStart();
    }
}
