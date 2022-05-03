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

    public void init(){
        cursor-=cursor;
    }

    public void update(){
        String[][] conversations = conversationHolder.getConversations();

        handleSkipButton();

        //reset cursor with key R
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            cursor = 0;

        try{
            conversationDialog.setContent1(conversations[cursor][0]);
            conversationDialog.setContent2(conversations[cursor][1]);
        }catch (ArrayIndexOutOfBoundsException ignored){}
    }

    private void handleSkipButton(){
        if (!isButtonLock){
            updateCursorWhenButtonActivate();
        }
        isButtonLock = conversationDialog.getSkipButton().isActive();
    }

    private void updateCursorWhenButtonActivate(){
        if (conversationDialog.getSkipButton().isActive() )
            cursor++;
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