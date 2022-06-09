package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CardAnimation {
    private final AnimationProcessor processor;
    private boolean isStart = false;
    private final TimeHandler timeHandler;
    private final AnimatePointAccessor pointAccessor;
    private float duration;
    private Point lastMousePoint;
    private Point drawPoint;

    public CardAnimation(Texture texture, int cols, int rows) {
        processor = new AnimationProcessor(texture, cols, rows);
        timeHandler = new TimeHandler();
        drawPoint = new Point();
        lastMousePoint = new Point();
        pointAccessor = new AnimatePointAccessor(processor);
    }

    public void setFrameDuration(float perSec) {
        processor.setFrameDuration(perSec);
    }

    public void setDuration(float second) {
        this.duration = second;
    }

    public void setScale(float scale){
        processor.setScale(scale);
    }

    public void setLastMousePoint(Point point) {
        lastMousePoint = point;
    }

    public void setStart() {
        isStart = true;
    }

    public void init() {
        timeHandler.resetPastedTime();
        isStart = false;
    }

    public void draw(SpriteBatch batch,AnimationPos pos) {
        processor.setBatch(batch);
        if (isStart) {
            handleAnimation(pos);
        } else {
            resetTimeAndAnimation();
        }
    }

    private void handleAnimation(AnimationPos pos) {
        timeHandler.updatePastedTime();

        if (timeHandler.getPastedTime() < duration) {
            processor.setPoint(lastMousePoint);
            drawPoint = pointAccessor.getPoint(pos);
            processor.draw(drawPoint.getX(), drawPoint.getY());
        } else
            isStart = false;
    }

    private void resetTimeAndAnimation() {
        timeHandler.resetPastedTime();
        processor.init();
    }
}

class AnimatePointAccessor{
    private final AnimationProcessor processor;

    public AnimatePointAccessor(AnimationProcessor processor){
        this.processor = processor;
    }

    public Point getPoint(AnimationPos pos) {
        float x = 0;
        float y = 0;

        if (pos == AnimationPos.CENTER) {
            x = processor.getPoint().getX() - processor.getWidth() / 2;
            y = processor.getPoint().getY() - processor.getHeight() / 2;
        }
        if (pos == AnimationPos.TOP_RIGHT) {
            x = processor.getPoint().getX();
            y = processor.getPoint().getY();
        }

        return new Point(x, y);
    }
}
