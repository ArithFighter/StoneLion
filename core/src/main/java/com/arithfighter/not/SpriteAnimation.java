package com.arithfighter.not;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteAnimation {
    private final int FRAME_COLS, FRAME_ROWS;
    private final Animation<TextureRegion> animation;
    private SpriteBatch batch;
    private Point point = new Point(0,0);
    private float scale = 1;

	float stateTime = 0;

    public SpriteAnimation(Texture texture, int cols, int rows){
        FRAME_COLS = cols;
        FRAME_ROWS = rows;

		TextureRegion[][] splitSheet = getSplitSpriteSheet(texture);

		TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		addSpriteToFrame(splitSheet, frames);

		animation = new Animation<>(0.166f, frames);
    }

	private TextureRegion[][] getSplitSpriteSheet(Texture texture){
		return TextureRegion.split(texture,
				texture.getWidth() / FRAME_COLS,
				texture.getHeight() / FRAME_ROWS);
	}

	private void addSpriteToFrame(TextureRegion[][] splitSheet, TextureRegion[] walkFrames){
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++)
				walkFrames[index++] = splitSheet[i][j];
		}
	}

    public void setSpeed(float perSec){
        animation.setFrameDuration(perSec);
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void draw(){
		stateTime += Gdx.graphics.getDeltaTime();

		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        float width = currentFrame.getRegionWidth()*scale;
        float height = currentFrame.getRegionHeight()*scale;

		batch.draw(currentFrame,
                point.getX()-width/2,
                point.getY()-height/2,
                width,
                height);
    }

    public void init(){
        stateTime = 0;
    }
}
