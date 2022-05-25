package com.arithfighter.not.scene;

import com.arithfighter.not.TextureService;
import com.arithfighter.not.font.Font;
import com.arithfighter.not.widget.button.SceneControlButton;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends SceneComponent implements SceneEvent, MouseEvent {
    private final Font font;
    private final SceneControlButton quitButton;

    public GameOver(TextureService textureService){
        Texture[] textures = textureService.getTextures(textureService.getKeys()[0]);
        quitButton = new SceneControlButton(textures[6], 2);
        quitButton.getButton().setPosition(600,150);

        font = new Font(40);
        font.setColor(Color.WHITE);
    }

    public boolean isQuit(){
        return quitButton.isStart();
    }

    @Override
    public void touchDown() {
        quitButton.getButton().on(getCursorPos().getX(), getCursorPos().getY());
    }

    @Override
    public void touchDragged() {
        quitButton.getButton().off();
    }

    @Override
    public void touchUp() {
        quitButton.getButton().off();
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
        font.draw(batch, "Game Over", 600,500);
        quitButton.getButton().draw(batch, "Quit");
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
