package com.arithfighter.not.scene;

import com.arithfighter.not.TextureManager;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends SceneComponent implements SceneEvent, MouseEvent{
    private final Font text;
    private final SceneControlButton quitButton;

    public GameOver(TextureManager textureManager){
        Texture[] textures = textureManager.getTextures(textureManager.getKeys()[0]);
        quitButton = new SceneControlButton(textures[6], 2);
        quitButton.getButton().setPosition(600,150);

        text = new Font(40);
        text.setColor(Color.WHITE);
    }

    public boolean isQuit(){
        return quitButton.isStart();
    }

    @Override
    public void touchDown() {
        quitButton.getButton().activate(getCursorPos().getX(), getCursorPos().getY());
    }

    @Override
    public void touchDragged() {
        quitButton.getButton().deactivate();
    }

    @Override
    public void touchUp() {
        quitButton.getButton().deactivate();
    }

    @Override
    public void init() {
        quitButton.init();
    }

    @Override
    public void update() {
        quitButton.update();
    }

    @Override
    public void draw() {
        SpriteBatch batch = getBatch();
        text.draw(batch, "Game Over", 600,500);
        quitButton.getButton().draw(batch, "Quit");
    }

    @Override
    public void dispose() {
        text.dispose();
        quitButton.dispose();
    }
}
