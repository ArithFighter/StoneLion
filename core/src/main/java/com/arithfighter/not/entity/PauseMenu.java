package com.arithfighter.not.entity;

import com.arithfighter.not.audio.SoundManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.pojo.TextProvider;
import com.arithfighter.not.widget.Dialog;
import com.arithfighter.not.widget.SceneControlButton;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.arithfighter.not.widget.button.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
    private final ButtonProducer buttons;
    private final Dialog dialog;
    private final VisibleWidget background;
    private final SoundManager soundManager;
    private final TextProvider textProvider;
    private final MessageDisplacer messageDisplacer;

    public PauseMenu(Texture[] textures, SoundManager soundManager) {
        this.soundManager = soundManager;

        textProvider = new TextProvider();

        buttons = new ButtonProducer(textures);

        dialog = new Dialog(textures);
        dialog.setPoint(new Point(500,300));

        background = new SpriteWidget(textures[1], 5f);
        background.setPosition(500,200);

        messageDisplacer = new MessageDisplacer(textures);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);

        for (int i =0; i<buttons.getButtons().length;i++){
            Button b = buttons.getButtons()[i].getButton();
            b.draw(batch, textProvider.getPauseMenuTexts()[i]);
        }

        if (buttons.getQuit().isStart())
            dialog.draw(batch, textProvider.getPauseMenuTexts()[3], textProvider.getPauseMenuTexts()[4]);

        messageDisplacer.draw(batch);
    }

    public void update() {
        if (buttons.getQuit().isStart()){
            dialog.update();

            if (dialog.getNoButton().isStart()){
                buttons.getQuit().init();
                dialog.init();
            }
        } else {
            for (SceneControlButton button:buttons.getButtons())
                button.update();
        }
    }

    public void init() {
        for (SceneControlButton button:buttons.getButtons())
                button.init();
        dialog.init();
    }

    public boolean isReturnToMainMenu() {
        return dialog.getYesButton().isStart();
    }

    public boolean isResume(){
        return buttons.getResume().isStart();
    }

    public boolean isOpenOption(){
        return buttons.getOption().isStart();
    }

    public void touchDown(float x, float y) {
        if (buttons.getQuit().isStart()){
            dialog.activate(x, y);
        }else {
            for (SceneControlButton button:buttons.getButtons())
                button.getButton().activate(x, y);
        }
    }

    public void touchDragged(){
        if (buttons.getQuit().isStart()){
            dialog.deactivate();
        }else {
            for (SceneControlButton button:buttons.getButtons())
                button.getButton().deactivate();
        }
    }

    public void touchUp() {
        if (buttons.getQuit().isStart()){
            dialog.deactivate();
        }else {
            playSound();

            for (SceneControlButton button:buttons.getButtons())
                button.getButton().deactivate();
        }
    }

    private void playSound(){
        for (int i =0;i<buttons.getButtons().length;i++){
            Button b = buttons.getButtons()[i].getButton();
            if (b.isActive())
                soundManager.playAcceptSound();
        }
    }

    public void dispose() {
        for (SceneControlButton button:buttons.getButtons())
                button.dispose();
        dialog.dispose();
        messageDisplacer.dispose();
    }
}

class ButtonProducer{
    private final SceneControlButton[] buttons;

    public ButtonProducer(Texture[] textures){
        buttons = new SceneControlButton[3];

        for (int i =0; i< buttons.length;i++){
            buttons[i] = new SceneControlButton(textures[6], 1.8f);
            buttons[i].getButton().setPosition(540,250+150*i);
        }
    }

    public SceneControlButton[] getButtons(){
        return buttons;
    }

    public SceneControlButton getResume(){
        return buttons[2];
    }

    public SceneControlButton getOption(){
        return buttons[1];
    }

    public SceneControlButton getQuit(){
        return buttons[0];
    }
}

class MessageDisplacer{
    private final Font[] texts;
    private final VisibleWidget background;
    private int stages;
    private int wins;
    private int loses;
    private int tokens;

    public MessageDisplacer(Texture[] textures){
        texts = new Font[4];

        for (int i = 0; i< texts.length;i++){
            texts[i] = new Font(20);
            texts[i].setColor(Color.BLACK);
        }

        background = new SpriteWidget(textures[1], 6f);
        background.setPosition(0, 150);
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void draw(SpriteBatch batch){
        background.draw(batch);
        int x = 50;
        texts[0].draw(batch, "stage: "+stages, x,700);
        texts[1].draw(batch, "win: "+wins, x,600);
        texts[2].draw(batch, "lose: "+loses, x,500);
        texts[3].draw(batch, "tokens: "+tokens, x,400);
    }

    public void dispose(){
        for (Font font:texts)
            font.dispose();
    }
}