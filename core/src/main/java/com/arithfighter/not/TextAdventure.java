package com.arithfighter.not;

import com.arithfighter.not.widget.dialog.ConversationDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextAdventure{
    private final ConversationDialog conversationDialog;
    private int cursor = 0;
    private boolean isButtonLock = false;
    private final ConversationHolder conversationHolder;

    public TextAdventure(Texture[] textures){
        conversationDialog = new ConversationDialog(textures);
        conversationHolder = new ConversationHolder();
    }

    public void update(){
        if (!isButtonLock){
            if (conversationDialog.getSkipButton().isActive() )
                cursor++;
        }
        isButtonLock = conversationDialog.getSkipButton().isActive();

        String[][] conversations = conversationHolder.getConversations();

        if (cursor > conversations.length-1)
            cursor = conversations.length-1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
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

class ConversationHolder{
    private final String[][] conversations = {
            {"123456", "rpg dialog"},
            {"rpg dialog test", "Hello world!!"},
            {"My name is ArithFighter", "Math is great"}
    };

    public String[][] getConversations() {
        return conversations;
    }
}