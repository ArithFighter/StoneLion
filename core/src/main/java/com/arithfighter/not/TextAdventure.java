package com.arithfighter.not;

import com.arithfighter.not.pojo.ConversationHolder;
import com.arithfighter.not.widget.RawWidget;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.dialog.ConversationDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextAdventure{
    private final ConversationDialog conversationDialog;
    private int cursor = 0;
    private boolean isButtonLock = false;
    private ConversationHolder conversationHolder;
    private final MugShot mugShot;

    public TextAdventure(Texture[] widgets, Texture[] panels){
        conversationDialog = new ConversationDialog(widgets);

        mugShot = new MugShot(widgets[3], panels[0]);

        RawWidget widget = conversationDialog.getDialog().getWidget();
        mugShot.setPosition(widget.getWidth()*9/10, widget.getHeight()*0.7f);
    }

    public void setConversationHolder(ConversationHolder conversationHolder){
        this.conversationHolder = conversationHolder;
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

    public boolean isAllConversationFinished(){
        return cursor>=conversationHolder.getConversations().length;
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
        mugShot.draw(batch);
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

class MugShot{
    private final VisibleWidget mugShot;
    private final VisibleWidget panelBox;

    public MugShot(Texture texture, Texture panel){
        mugShot = new SpriteWidget(texture, 6f);

        panelBox = new SpriteWidget(panel, 0.9f);
    }

    public void setPosition(float x, float y){
        mugShot.setPosition(
                x-(mugShot.getWidget().getWidth()-panelBox.getWidget().getWidth())/2
                ,y-(mugShot.getWidget().getWidth()-panelBox.getWidget().getWidth())/2
        );
        panelBox.setPosition(x, y);
    }

    public void draw(SpriteBatch batch){
        mugShot.draw(batch);
        panelBox.draw(batch);
    }
}
