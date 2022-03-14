package com.arithfighter.ccg.card;

public class StateManager {
    private enum State {ACTIVE, INACTIVE}
    private State state = State.INACTIVE;

    public void deactivate(){
        state = State.INACTIVE;
    }

    public void activate(){
        state = State.ACTIVE;
    }

    public boolean isActive(){
        return state == State.ACTIVE;
    }
}
