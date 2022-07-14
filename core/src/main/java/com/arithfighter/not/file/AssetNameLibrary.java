package com.arithfighter.not.file;

public class AssetNameLibrary {
    private final String[][] texturePathCollection;

    public AssetNameLibrary(){
        TexturePathGettable[] tpgs = new TexturePathGettable[]{
                new GuiPathName(),
                new CardPathName(),
                new PanelPathName(),
                new AnimationSheetPathName(),
                new ObjectPathName()
        };

        texturePathCollection = new String[tpgs.length][];

        for (int i = 0; i< tpgs.length; i++)
            texturePathCollection[i] = tpgs[i].getPaths();
    }

    public String[] getMusicPath() {
        MusicPathName musicPathName = new MusicPathName();
        return musicPathName.getMusicPaths();
    }

    public String[] getSoundPath() {
        SoundPathName soundPathName = new SoundPathName();
        return soundPathName.getSoundPaths();
    }

    public String[][] getTexturePathCollection() {
        return texturePathCollection;
    }
}

interface TexturePathGettable{
    String[] getPaths();
}

class GuiPathName implements TexturePathGettable{
    private final String[] paths = {
            "gui/Card_template.png",
            "gui/BoardArea.png",
            "gui/sum-Display-block.png",
            "gui/numberBox.png",
            "gui/Energy-bar.png",

            "gui/white-block.png",
            "gui/Button1.png",
            "gui/Golden_Square.png",
            "gui/arrow-left.png",
            "gui/dialog.png",

            "gui/wide-dialog.png",
            "gui/w16h9-block.png"
    };

    public String[] getPaths() {
        return paths;
    }
}

class CardPathName implements TexturePathGettable{
    private final String[] paths = {
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

    public String[] getPaths() {
        return paths;
    }
}

class PanelPathName implements TexturePathGettable{
    private final String[] paths = {
            "panel/snake.png",
            "panel/crane.png",
    };

    public String[] getPaths() {
        return paths;
    }
}

class AnimationSheetPathName implements TexturePathGettable{
    private final String[] paths = {
            "animation/card-fade-in.png",
            "animation/card-fade-out.png"
    };

    public String[] getPaths() {
        return paths;
    }
}

class ObjectPathName implements TexturePathGettable {
    private final String[] paths = {
            "object/stone-lion.png",
            "object/bell.png",
            "object/red-candle.png",
            "object/candle-fire.png",
            "object/Hand-CandleStick.png",

            "object/ghost-fire.png",
            "object/bamboo-forest.png",
            "object/candle-head.png",
            "object/candle-bottom.png",
            "object/light.png",

            "object/sight.png",
            "object/single-rope.png",
            "object/pillar-left.png",
            "object/pillar-right.png"
    };

    public String[] getPaths() {
        return paths;
    }
}

class MusicPathName{
    private final String[] musicPaths = {
            "music/character-select.ogg",
            "music/theme.ogg",
            "music/game-complete.ogg"
    };

    public String[] getMusicPaths() {
        return musicPaths;
    }
}

class SoundPathName{
    private final String[] soundPaths = {
            "sound/accept.ogg",
            "sound/score.ogg",
            "sound/denied.ogg",
            "sound/select.ogg"
    };

    public String[] getSoundPaths() {
        return soundPaths;
    }
}