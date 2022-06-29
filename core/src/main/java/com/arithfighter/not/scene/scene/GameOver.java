package com.arithfighter.not.scene.scene;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class GameOver extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font font;
    private final SceneControlButton quitButton;
    private final Rectangle grid;

    public GameOver(TextureService textureService, FontService fontService){
        Texture[] widgets = textureService.getTextures(textureService.getKeys()[0]);

        font = fontService.getFont36();
        font.setColor(Color.WHITE);

        LayoutSetter layoutSetter = new LayoutSetter();
        layoutSetter.setGrid(2,3);

        grid = layoutSetter.getGrid();

        Font font22 = fontService.getFont22();
        font22.setColor(Color.WHITE);

        quitButton = new SceneControlButton(widgets[6], 1.8f);
        quitButton.getButton().setFont(font22);
        quitButton.getButton().setPosition(
                grid.getWidth()-widgets[6].getWidth()*0.9f,
                grid.getHeight()
        );
    }

    @Override
    public void touchDown() {
        quitButton.getButton().on(getCursorPos().getX(), getCursorPos().getY());
    }

    @Override
    public void touchDragged() {
        quitButton.getButton().off();
    }

    @Override
    public void touchUp() {
        quitButton.getButton().off();
    }

    @Override
    public void init() {
        quitButton.init();
    }

    @Override
    public void render() {
        String text = "GameOver";
        font.draw(getBatch(), text, grid.getWidth()-text.length()* font.getSize()/2f, grid.getHeight()*2);

        quitButton.update();
        quitButton.getButton().draw(getBatch(), "Quit");
    }

    public boolean isQuit(){
        return quitButton.isStart();
    }
}
