package com.arithfighter.ccg.component;

public class SkillHandler {
    private SkillState skillState = SkillState.NEUTRAL;

    public SkillState getSkillFlag(){
        return skillState;
    }

    public void setActive(){
        skillState = SkillState.ACTIVE;
    }

    public void setReady(){
        skillState = SkillState.READY;
    }

    public void init(){
        skillState = SkillState.NEUTRAL;
    }

    public boolean isSkillActive(){
        return skillState == SkillState.ACTIVE;
    }

    public boolean isSkillReady(){
        return skillState == SkillState.READY;
    }

    public boolean isSkillNeutral(){
        return skillState == SkillState.NEUTRAL;
    }
}
