package com.arithfighter.ccg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameCore {
    AssetManager assetManager = new AssetManager();
    GameDataDisplacer dataDisplacer;
    ScoreBoard scoreBoard;
    Hand hand;
    Desk desk;
    MouseListener mouseListener = new MouseListener();
    int mouseX;
    int mouseY;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    int score = 0;

    InputAdapter mouseAdapter = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            hand.checkActive(mouseX,mouseY);
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            hand.updateWhenDrag(mouseX,mouseY);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (desk.isOnDesk(mouseX,mouseY)){
                if (hand.isCardActive())
                    score++;
            }
            hand.resetHand();
            return true;
        }
    };

    public void create() {
        assetManager.load("Card_template.png", Texture.class);
        assetManager.finishLoading();

        assetManager.get("Card_template.png");

        dataDisplacer = new GameDataDisplacer();
        dataDisplacer.create();

        scoreBoard = new ScoreBoard();
        scoreBoard.create();

        hand = new Hand();
        hand.initHand(assetManager);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        Gdx.input.setInputProcessor(mouseAdapter);

        desk = new Desk(WindowSetting.GRID_X*4, WindowSetting.GRID_Y*9);
    }

    public void render() {
        assetManager.update(17);

        updateMousePosition();

        shapeRenderer.begin();
        drawShape();
        shapeRenderer.end();

        batch.begin();
        drawSprite();
        batch.end();
    }

    private void updateMousePosition() {
        mouseX = mouseListener.getMouseX();
        mouseY = mouseListener.getMouseY();
    }

    private void drawShape() {
        desk.draw(shapeRenderer);
    }

    private void drawSprite() {
        hand.draw(batch,mouseX,mouseY);
        dataDisplacer.draw(mouseX, mouseY, batch);
        scoreBoard.draw("ScoreBoard: "+score, batch);
    }

    public void dispose() {
        dataDisplacer.dispose();
        scoreBoard.dispose();
        batch.dispose();
    }
}
