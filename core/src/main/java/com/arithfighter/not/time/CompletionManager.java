package com.arithfighter.not.time;

public class CompletionManager {
    private boolean isComplete;
    private final TimeHandler timeHandler;

    public CompletionManager() {
        isComplete = false;
        timeHandler = new TimeHandler();
    }

    public void init() {
        isComplete = false;
        timeHandler.resetPastedTime();
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void countDownCompletion() {
        timeHandler.updatePastedTime();
        if (timeHandler.getPastedTime() > 1.5f)
            isComplete = true;
    }
}
