package com.arithfighter.not.scene.scene;

import com.arithfighter.not.file.texture.TextureGetter;
import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;

public class EnchantmentMap extends SceneComponent implements SceneEvent, MouseEvent {
    private final VisibleWidget pentagram;
    private final VisibleWidget background;
    private final SceneControlButton startButton;

    public EnchantmentMap(TextureService textureService, FontService fontService){
        TextureGetter tg = new TextureGetter(textureService);

        background = new SpriteWidget(tg.getGuiMap().get("gui/w16h9-block.png"), 80);
        background.getSprite().setColor(Color.GOLDENROD);
        background.setPosition(0,0);

        LayoutSetter ls = new LayoutSetter();
        ls.setGrid(4,5);
        Rectangle grid = ls.getGrid();

        startButton = new SceneControlButton(tg.getGuiMap().get("gui/Button1.png"), 1.8f);
        startButton.getButton().setFont(fontService.getFont22());
        startButton.getButton().setPosition(grid.getWidth()*3, grid.getHeight());

        pentagram = new SpriteWidget(tg.getObjectMap().get("object/pentagram.png"), 8);
        pentagram.setPosition(
                grid.getWidth()*2-pentagram.getWidget().getWidth()/2,
                grid.getHeight()*1
                );
    }

    @Override
    public void touchDown() {
        startButton.getButton().on(getCursorPos().getX(), getCursorPos().getY());
    }

    @Override
    public void touchDragged() {
        startButton.getButton().off();
    }

    @Override
    public void touchUp() {
        startButton.getButton().off();
    }

    @Override
    public void init() {
        startButton.init();
    }

    @Override
    public void render() {
        background.draw(getBatch());

        startButton.getButton().draw(getBatch(), "Start");

        pentagram.draw(getBatch());
    }
}
