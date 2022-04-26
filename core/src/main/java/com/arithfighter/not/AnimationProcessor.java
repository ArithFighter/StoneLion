package com.arithfighter.not;

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
    private float scale = 1;

	float stateTime = 0;

    public AnimationProcessor(Texture texture, int cols, int rows){
        FrameProducer frameProducer = new FrameProducer(texture, cols, rows);

		animation = new Animation<>(0.166f, frameProducer.getFrames());
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