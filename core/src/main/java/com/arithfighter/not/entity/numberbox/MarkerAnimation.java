package com.arithfighter.not.entity.numberbox;

import com.arithfighter.not.animate.VisualEffect;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.time.TimeHandler;
import com.arithfighter.not.time.Timer;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MarkerAnimation {
    private final VisualEffect visualEffect;
    private final Timer timer;
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
        timer = new Timer();
        timer.setTime(1);
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void draw() {
        int ratePerSec = 8;

        if (index >= 0) {
            timer.update();

            visualEffect.animateFlashy(ratePerSec);

            if (timer.isTimesOut())
                init();
        }
    }

    public void init() {
        timer.init();
        index -= index + 1;
    }
}
