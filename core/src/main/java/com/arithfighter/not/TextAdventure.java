package com.arithfighter.not;

import com.arithfighter.not.widget.dialog.ConversationDialog;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextAdventure{
    private final ConversationDialog conversationDialog;
    private final String[][] conversations = {
            {"123456", "rpg dialog"},
            {"rpg dialog test", "Hello world!!"},
            {"My name is ArithFighter", "Math is great"}};
    private int cursor = 0;
    private boolean isButtonLock = false;

    public TextAdventure(Texture[] textures){
        conversationDialog = new ConversationDialog(textures);
    }

    public void update(){
        if (!isButtonLock){
            if (conversationDialog.getSkipButton().isActive() ) {
                cursor++;
                isButtonLock = true;
            }
        }
        isButtonLock = conversationDialog.getSkipButton().isActive();

        if (cursor> conversations.length-1)
            cursor = 0;
        conversationDialog.setContent1(conversations[cursor][0]);
        conversationDialog.setContent2(conversations[cursor][1]);
    }

    public void draw(SpriteBatch batch){
        conversationDialog.draw(batch);
    }

    public void touchDown(int x, int y) {
        conversationDialog.activate(x, y);
    }

    public void touchDragged() {
        conversationDialog.deactivate();
    }

    public void touchUp() {
        conversationDialog.deactivate();
    }

    public void dispose(){
        conversationDialog.dispose();
    }
}
