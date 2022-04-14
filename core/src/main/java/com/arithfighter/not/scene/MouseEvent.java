package com.arithfighter.not.scene;

import com.arithfighter.not.CursorPositionAccessor;

public interface MouseEvent {
    void setCursorPos(CursorPositionAccessor cursorPos);
    void touchDown();
    void touchDragged();
    void touchUp();
}
