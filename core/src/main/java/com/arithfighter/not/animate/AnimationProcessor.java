package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationProcessor {
    private final Animation<TextureRegion> animation;
    private SpriteBatch batch;
    private Point point = new Point(0,0);
    private float width;
    private float height;
    private float scale = 1;

	float stateTime = 0;

    public AnimationProcessor(Texture texture, int cols, int rows){
        FrameProducer frameProducer = new FrameProducer(texture, cols, rows);

		animation = new Animation<>(0.166f, frameProducer.getFrames());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setSpeed(float perSec){
        animation.setFrameDuration(perSec);
    }

    public void setBatch(SpriteBatch batch){
        this.batch = batch;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point){
        this.point = point;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void draw(float x, float y){
		stateTime += Gdx.graphics.getDeltaTime();

		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        width = currentFrame.getRegionWidth()*scale;
        height = currentFrame.getRegionHeight()*scale;

		batch.draw(currentFrame, x, y, width, height);
    }

    public void init(){
        stateTime = 0;
    }
}

class FrameProducer{
    TextureRegion[] frames;

    public FrameProducer(Texture texture, int cols, int rows){
        TextureRegion[][] splitSheet = TextureRegion.split(texture,
				texture.getWidth() / cols,
				texture.getHeight() / rows);

        frames = new TextureRegion[cols * rows];

		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++)
				frames[index++] = splitSheet[i][j];
		}
    }

    public TextureRegion[] getFrames() {
        return frames;
    }
}