package com.arithfighter.not.scene;

import com.arithfighter.not.TextAdventure;
import com.arithfighter.not.TextureService;
import com.badlogic.gdx.graphics.Texture;

public class Ending extends SceneComponent implements SceneEvent, MouseEvent {
    private final TextAdventure textAdventure;

    public Ending(TextureService textureService){
        Texture[] widgets = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] panels = textureService.getTextures(textureService.getKeys()[2]);

        textAdventure = new TextAdventure(widgets, panels);

        textAdventure.setConversations(new String[][]{
                        {"This is the ending","when you finish all stages"},
                        {"The content would be depend", "on character or your score"}
                });
    }

    public boolean isLeave(){
        return textAdventure.isAllConversationFinished();
    }
    @Override
    public void touchDown() {
        textAdventure.touchDown(getCursorPos().getX(), getCursorPos().getY());
    }

    @Override
    public void touchDragged() {
        textAdventure.touchDragged();
    }

    @Override
    public void touchUp() {
        textAdventure.touchUp();
    }

    @Override
    public void init() {
        textAdventure.init();
    }

    @Override
    public void update() {
        textAdventure.update();
    }

    @Override
    public void draw() {
        textAdventure.draw(getBatch());
    }

    @Override
    public void dispose() {
        textAdventure.dispose();
    }
}
