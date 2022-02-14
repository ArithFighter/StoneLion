package com.arithfighter.ccg.asset;

public class FileLibrary {
    String[] textures = {
            "Card_template.png",
            "desk.png",
            "sum-Display-block.png",
            "numberBox.png"
    };

    String[] cards = {
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
