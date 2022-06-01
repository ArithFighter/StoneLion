package com.arithfighter.not.font;

public class FontService {
    private final Font[] fonts;

    public FontService(){
        fonts = new Font[]{
                new Font(20),
                new Font(22),
                new Font(24),
                new Font(28),
        };
    }

    public Font getFont20(){
        return fonts[0];
    }

    public Font getFont24() {
        return fonts[1];
    }

    public Font getFont22() {
        return fonts[2];
    }

    public Font getFont28() {
        return fonts[3];
    }

    public void dispose(){
        for (Font f:fonts)
            f.dispose();
    }
}
