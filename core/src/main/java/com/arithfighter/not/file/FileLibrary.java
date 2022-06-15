package com.arithfighter.not.file;

public class FileLibrary {
    private final String[] widgetFiles = {
            "widget/Card_template.png",
            "widget/BoardArea.png",
            "widget/sum-Display-block.png",
            "widget/numberBox.png",
            "widget/Energy-bar.png",

            "widget/white-block.png",
            "widget/Button1.png",
            "widget/Golden_Square.png",
            "widget/arrow-left.png",
            "widget/arrow-right.png",

            "widget/dialog.png",
            "widget/wide-dialog.png"
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
            "spritesheet/card-fade-in.png",
            "spritesheet/card-fade-out.png",
            "spritesheet/gecko/gecko-default.png",
            "spritesheet/gecko/gecko-blink.png",
            "spritesheet/gecko/gecko-swing.png",

            "spritesheet/gecko/gecko-eating.png",
            "spritesheet/gecko/gecko-lick.png",
            "spritesheet/gecko/gecko-too-full.png",
            "spritesheet/gecko/gecko-full-eating.png",
            "spritesheet/gecko/gecko-spit.png",
            "spritesheet/lion/stone-lion.png"
    };

    public String[] getWidgetFiles(){
        return widgetFiles;
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
}
