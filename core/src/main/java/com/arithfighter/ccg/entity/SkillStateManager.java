package com.arithfighter.ccg.entity;

public class SkillStateManager {
    private enum SkillState {NEUTRAL, READY}
    private SkillState skillState = SkillState.NEUTRAL;

    public void setReady(){
        skillState = SkillState.READY;
    }

    public void init(){
        skillState = SkillState.NEUTRAL;
    }

    public boolean isSkillReady(){
        return skillState == SkillState.READY;
    }

    public boolean isSkillNeutral(){
        return skillState == SkillState.NEUTRAL;
    }
}
