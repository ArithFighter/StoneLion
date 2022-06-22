package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MarkerAnimation {
    private final VisualEffect visualEffect;
    private final TimeHandler timeHandler;
    private SpriteBatch batch;
    private int index = -1;
    private final VisibleWidget mark;

    public MarkerAnimation(Texture texture, NumberBox[] numberBoxes) {
        mark = new SpriteWidget(texture, 1.2f);
        visualEffect = new VisualEffect() {
            @Override
            public void renderEffect() {
                Point point = numberBoxes[index].getPoint();
                mark.setPosition(point.getX(), point.getY());
                mark.draw(batch);
            }
        };
        timeHandler = new TimeHandler();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void draw() {
        int ratePerSec = 8;
        float durationSec = 1f;

        if (index >= 0) {
            timeHandler.updatePastedTime();

            visualEffect.animateFlashy(ratePerSec);

            if (timeHandler.getPastedTime() > durationSec)
                init();
        }
    }

    public void init() {
        timeHandler.resetPastedTime();
        index -= index + 1;
    }
}
