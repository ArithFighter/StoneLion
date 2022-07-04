package com.arithfighter.not.file;

public class TextureNames {
    private final String[] cardFiles = {
            "Number-re0",
            "Number-1",
            "Number-2",
            "Number-3",
            "Number-4",

            "Number-5",
            "Number-6",
            "Number-7",
            "Number-8",
            "Number-9",

            "Number-re12",
            "Number-re15",
            "Number-neg1",
            "Number-neg3",
            "Number-neg7",

            "Number-max",
    };

    private final String[] widgetNames = {
            "BoardArea",
            "sum-Display-block",
            "Energy-bar",
            "white-block",
            "Button",

            "arrow-head-left",
            "arrow-head-right",
            "dialog",
            "wide-dialog",
            "Grid",

            "highlight"
    };

    private final String[] objectNames = {
            "bamboo-forest",
            "bamboo-forest-re",
            "bamboos",
            "bamboos-re",
            "bell",

            "candle-fire",
            "Hand-CandleStick",
            "red-candle",
            "stone-lion",
            "white-bird",

            "white-snake"
    };

    public String[] getWidgetNames() {
        return widgetNames;
    }

    public String[] getCardFiles() {
        return cardFiles;
    }

    public String[] getObjectNames() {
        return objectNames;
    }
}
