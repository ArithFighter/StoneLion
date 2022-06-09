package com.arithfighter.not.animate;

import com.arithfighter.not.pojo.Point;

public class VisualEffectController {
    private final VisualAnimatable[] visualAnimatable;
    
    public VisualEffectController(VisualAnimatable[] visualAnimatable) {
        this.visualAnimatable = visualAnimatable;
    }
    
    public VisualAnimatable[] getVisualAnimatable() {
        return visualAnimatable;
    }

    public void setScale(int scale){
        for (VisualAnimatable ga: visualAnimatable)
            ga.getVisualEffect().setScale(scale);
    }

    public void setLastMousePoint(Point point){
        for (VisualAnimatable ga: visualAnimatable)
            ga.getVisualEffect().setLastMousePoint(point);
    }

    public void init(){
        for (VisualAnimatable ga: visualAnimatable)
            ga.getVisualEffect().init();
    }
}
