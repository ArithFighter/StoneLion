package com.arithfighter.not.file;

public class FileLibrary {
    private final String[] guiFiles = {
            "gui/Card_template.png",
            "gui/BoardArea.png",
            "gui/sum-Display-block.png",
            "gui/numberBox.png",
            "gui/Energy-bar.png",

            "gui/white-block.png",
            "gui/Button1.png",
            "gui/Golden_Square.png",
            "gui/arrow-left.png",
            "gui/arrow-right.png",

            "gui/dialog.png",
            "gui/wide-dialog.png"
    };

    private final String[] cardFiles = {
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

    private final String[] panelFiles = {
            "panel/KNIGHT_panel.png",
            "panel/NINJA_panel.png",
            "panel/HUNTER_panel.png",
            "panel/PALADIN_panel.png",
            "panel/WARRIOR_panel.png",
    };

    private final String[] musicFiles = {
            "music/character-select.ogg",
            "music/theme.ogg",
            "music/game-complete.ogg"
    };

    private final String[] soundFiles = {
            "sound/accept.ogg",
            "sound/score.ogg",
            "sound/denied.ogg",
            "sound/select.ogg"
    };

    private final String[] sheetFiles = {
            "animation/card-fade-in.png",
            "animation/card-fade-out.png"
    };

    private final String[] objectFiles = {
            "object/stone-lion.png",
    };

    public String[] getGuiFiles(){
        return guiFiles;
    }

    public String[] getCardFiles(){
        return cardFiles;
    }

    public String[] getPanelFiles(){
        return panelFiles;
    }

    public String[] getMusicFiles() {
        return musicFiles;
    }

    public String[] getSoundFiles() {
        return soundFiles;
    }

    public String[] getSheetFiles() {
        return sheetFiles;
    }

    public String[] getObjectFiles() {
        return objectFiles;
    }
}
