package com.arithfighter.not.pojo;

public class TextProvider {
    private final String[] betScreenTexts = {
            "card limit: ",
            "please bet",
            "Start"
    };

    private final String[] optionMenuTexts = {
            "Sound",
            "Music",
            "Return"
    };

    private final String[] resultScreenTexts = {
            "You Win",
            "You Loose",
            " Tokens remain",
            "continue",
            "Game Over",
            "Quit"
    };

    private final String[] pauseMenuTexts = {
            "Resume",
            "Option",
            "Quit",
            "the game progress will be lose. Are you sure?"
    };

    public String[] getPauseMenuTexts() {
        return pauseMenuTexts;
    }

    public String[] getBetScreenTexts() {
        return betScreenTexts;
    }

    public String[] getOptionMenuTexts() {
        return optionMenuTexts;
    }

    public String[] getResultScreenTexts() {
        return resultScreenTexts;
    }
}
