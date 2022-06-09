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
        for (VisualAnimatable va: visualAnimatable)
            va.getVisualEffect().setScale(scale);
    }

    public void setLastMousePoint(Point point){
        for (VisualAnimatable va: visualAnimatable)
            va.getVisualEffect().setLastMousePoint(point);
    }
    
    public void setStart(){
        for (VisualAnimatable va: visualAnimatable)
            va.getVisualEffect().setStart();
    }

    public void init(){
        for (VisualAnimatable va: visualAnimatable)
            va.getVisualEffect().init();
    }
}
