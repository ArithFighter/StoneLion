package com.arithfighter.not.scene.scene;

import com.arithfighter.not.CursorPositionAccessor;
import com.arithfighter.not.entity.map.EnchantmentLevel;
import com.arithfighter.not.entity.map.Pentagram;
import com.arithfighter.not.entity.map.PlaceMark;
import com.arithfighter.not.file.texture.TextureGetter;
import com.arithfighter.not.file.texture.TextureService;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.font.FontService;
import com.arithfighter.not.pojo.LayoutSetter;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.Rectangle;
import com.arithfighter.not.scene.MouseEvent;
import com.arithfighter.not.scene.SceneEvent;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class EnchantmentMap extends SceneComponent implements SceneEvent, MouseEvent {
    private final Pentagram pentagram;
    private final VisibleWidget background;
    private final SceneControlButton startButton;
    private final Font mapName;
    private final Rectangle grid;

    public EnchantmentMap(TextureService textureService, FontService fontService){
        TextureGetter tg = new TextureGetter(textureService);

        background = new SpriteWidget(tg.getGuiMap().get("gui/w16h9-block.png"), 80);
        background.getSprite().setColor(Color.GOLDENROD);
        background.setPosition(0,0);

        LayoutSetter ls = new LayoutSetter();
        ls.setGrid(4,5);

        grid = ls.getGrid();

        startButton = new SceneControlButton(tg.getGuiMap().get("gui/Button1.png"), 1.8f);
        startButton.getButton().setFont(fontService.getFont22());
        startButton.getButton().setPosition(grid.getWidth()*3, grid.getHeight());

        Texture[] textures = new Texture[]{
                tg.getObjectMap().get("object/pentagram.png"),
                tg.getGuiMap().get("gui/white-block.png"),
                tg.getGuiMap().get("gui/Golden_Square.png")
        };
        pentagram = new Pentagram(textures, 8);
        pentagram.setPoint(new Point(grid.getWidth()*2, grid.getHeight()*1));

        mapName = fontService.getFont32();
        mapName.setColor(Color.BLACK);
    }

    public void setPlaceMarks(){
        PlaceMark[] placeMarks = pentagram.getPlaceMarks();
        EnchantmentLevel[] enchantmentLevels = getRandomEnchantmentLevels(placeMarks.length);

        for (int i = 0; i < placeMarks.length; i++)
            placeMarks[i].setLevel(enchantmentLevels[i]);
    }

    private EnchantmentLevel[] getRandomEnchantmentLevels(int length){
        EnchantmentLevel[] enchantmentLevels = new EnchantmentLevel[length];

        for (int i = 0; i < enchantmentLevels.length; i++) {
            if (i < 2)
                enchantmentLevels[i] = EnchantmentLevel.MID;
            else if (i < 4)
                enchantmentLevels[i] = EnchantmentLevel.LOW;
            else
                enchantmentLevels[i] = EnchantmentLevel.HIGH;
        }
        return enchantmentLevels;
    }

    public void setSelectedPlaceMarkToNone(){
        pentagram.getSelectedPlaceMark().setLevel(EnchantmentLevel.NONE);
    }

    public int getBellQuantity(){
        return pentagram.getSelectedPlaceMark().getLevel().getMinBell();
    }

    public boolean isStart(){
        boolean isStart = false;
        if (startButton.isStart()){
            if (getBellQuantity()>0)
                isStart = true;
            else
                startButton.init();
        }
        return isStart;
    }

    @Override
    public void touchDown() {
        CursorPositionAccessor c = getCursorPos();

        startButton.getButton().onWhenIsOnButton(c.getX(), c.getY());

        pentagram.on(c.getX(),c.getY());
    }

    @Override
    public void touchDragged() {
        startButton.getButton().off();
        pentagram.off();
    }

    @Override
    public void touchUp() {
        startButton.getButton().off();
        pentagram.off();
    }

    @Override
    public void init() {
        startButton.init();
    }

    @Override
    public void render() {
        background.draw(getBatch());

        startButton.update();
        startButton.getButton().draw(getBatch(), "Start");

        pentagram.draw(getBatch());

        mapName.draw(getBatch(), "Enchantment map", 0, grid.getHeight()*5- mapName.getSize());
    }
}
