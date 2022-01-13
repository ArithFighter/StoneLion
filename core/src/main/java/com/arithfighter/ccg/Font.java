package com.arithfighter.ccg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {
    FreeTypeFontGenerator fontGenerator;
	FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
	BitmapFont font;

    public void create(int size){
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fontstyle/pcsenior.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        font = fontGenerator.generateFont(fontParameter);
    }

    public BitmapFont getBitmapFont(){
        return font;
    }

    public void dispose(){
        font.dispose();
        fontGenerator.dispose();
    }
}
