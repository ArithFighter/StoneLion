package com.arithfighter.ccg.file;

public class FileLibrary {
    private final String[] textures = {
            "widget/Card_template.png",
            "widget/BoardArea.png",
            "widget/sum-Display-block.png",
            "widget/numberBox.png",
            "widget/Energy-bar.png",
            "widget/white-block.png",
            "widget/Button1.png"
    };

    private final String[] cards = {
            "cards/Number-re0.png",
            "cards/Number-1.png",
            "cards/Number-2.png",
            "cards/Number-3.png",
            "cards/Number-4.png",
            "cards/Number-5.png",
            "cards/Number-6.png",
            "cards/Number-7.png",
            "cards/Number-8.png",
            "cards/Number-9.png",
            "cards/Number-re12.png",
            "cards/Number-re15.png",
            "cards/Number-neg1.png",
            "cards/Number-neg3.png",
            "cards/Number-neg7.png",
            "cards/Number-max.png",
    };

    public String[] getTextureFile(){
        return textures;
    }

    public String[] getCardFiles(){
        return cards;
    }
}
