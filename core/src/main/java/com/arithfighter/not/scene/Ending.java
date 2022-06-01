package com.arithfighter.not.scene;

import com.arithfighter.not.TextAdventure;
import com.arithfighter.not.TextureService;
import com.arithfighter.not.font.FontService;
import com.badlogic.gdx.graphics.Texture;

public class Ending extends SceneComponent implements SceneEvent, MouseEvent {
    private final TextAdventure textAdventure;

    public Ending(TextureService textureService, FontService fontService){
        Texture[] widgets = textureService.getTextures(textureService.getKeys()[0]);
        Texture[] panels = textureService.getTextures(textureService.getKeys()[2]);

        textAdventure = new TextAdventure(widgets, panels, fontService);

        textAdventure.setConversations(new String[]{
                        "This is the ending when you finish all stages",
                        "The content would be depend on how you play the game"
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
    public void render() {
        textAdventure.update();
        textAdventure.draw(getBatch());
    }
}
