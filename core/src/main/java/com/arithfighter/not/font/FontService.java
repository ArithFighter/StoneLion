package com.arithfighter.not.font;

import java.util.HashMap;

public class FontService {
    private final HashMap<Integer, Font> fontMap;

    public FontService(){
        Font[] fonts = new Font[]{
                new Font(16),
                new Font(20),
                new Font(22),
                new Font(24),
                new Font(28),
                new Font(32),
                new Font(36),
        };

        int[] keys = new int[]{
                16, 20, 22, 24, 28, 32, 36
        };

        fontMap= new HashMap<>();

        for (int i = 0; i< fonts.length; i++)
            fontMap.put(keys[i], fonts[i]);
    }

    public Font getFont16(){
        return fontMap.get(16);
    }

    public Font getFont20(){
        return fontMap.get(20);
    }

    public Font getFont22() {
        return fontMap.get(22);
    }

    public Font getFont24() {
        return fontMap.get(24);
    }

    public Font getFont28() {
        return fontMap.get(28);
    }

    public Font getFont32(){
        return fontMap.get(32);
    }

    public Font getFont36(){
        return fontMap.get(36);
    }

    public void dispose(){
        for (Font f:fontMap.values())
            f.dispose();
    }
}
