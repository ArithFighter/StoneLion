package com.arithfighter.ccg;

public class SkillHandler {
    private SkillFlag skillFlag = SkillFlag.NEUTRAL;

    public SkillFlag getSkillFlag(){
        return skillFlag;
    }

    public void setActive(){
        skillFlag = SkillFlag.ACTIVE;
    }

    public void setReady(){
        skillFlag = SkillFlag.READY;
    }

    public void init(){
        skillFlag = SkillFlag.NEUTRAL;
    }

    public boolean isSkillActive(){
        return skillFlag == SkillFlag.ACTIVE;
    }

    public boolean isSkillReady(){
        return skillFlag == SkillFlag.READY;
    }

    public boolean isSkillNeutral(){
        return skillFlag == SkillFlag.NEUTRAL;
    }
}
