package com.arithfighter.ccg.card;

public class StateManager {
    enum State {ACTIVE, INACTIVE}
    State state = State.INACTIVE;

    public void inActive(){
        state = State.INACTIVE;
    }

    public void active(){
        state = State.ACTIVE;
    }

    public boolean isActive(){
        return state == State.ACTIVE;
    }
}
