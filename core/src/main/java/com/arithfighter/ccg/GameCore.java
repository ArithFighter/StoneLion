package com.arithfighter.ccg;

import com.arithfighter.ccg.widget.Desk;
import com.arithfighter.ccg.widget.GameDataDisplacer;
import com.arithfighter.ccg.widget.Hand;
import com.arithfighter.ccg.widget.ScoreBoard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameCore {
    AssetManager assetManager = new AssetManager();
    CounterAssetsManager myAssetManager = new CounterAssetsManager();
    FileLibrary fileLibrary = new FileLibrary();
    GameDataDisplacer dataDisplacer;
    ScoreBoard scoreBoard;
    Hand hand;
    Desk desk;
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
        myAssetManager.loadTexture(assetManager, fileLibrary.getTextureFile());
        assetManager.finishLoading();

        dataDisplacer = new GameDataDisplacer();
        dataDisplacer.create();

        scoreBoard = new ScoreBoard();
        scoreBoard.create();

        hand = new Hand();
        hand.initHand(assetManager.get(fileLibrary.getTextureFile()[0]));

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        Gdx.input.setInputProcessor(mouseAdapter);

        desk = new Desk(
                WindowSetting.GRID_X*4,
                WindowSetting.GRID_Y*6,
                assetManager.get(fileLibrary.getTextureFile()[1]),
                18);
    }

    public void render() {
        assetManager.update(17);

        updateMousePosition();

        batch.begin();
        drawSprite();
        batch.end();
    }

    private void updateMousePosition() {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
    }

    private void drawSprite() {
        desk.draw(batch);
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
