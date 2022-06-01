package com.arithfighter.not.widget.dialog;

import com.arithfighter.not.font.Font;
import com.arithfighter.not.pojo.Point;
import com.arithfighter.not.widget.SpriteWidget;
import com.arithfighter.not.widget.VisibleWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dialog {
    private Font font;
    private final VisibleWidget dialog;
    private String originString = "";
    private String content1;
    private String content2;
    private final Point point;

    public Dialog(Texture texture, float scale) {
        dialog = new SpriteWidget(texture, scale);

        point = new Point();
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setOriginString(String s) {
        originString = s;
    }

    public Point getPoint() {
        return point;
    }

    public VisibleWidget getDialog() {
        return dialog;
    }

    public void drawDialog(SpriteBatch batch) {
        dialog.setPosition(point.getX(), point.getY());
        dialog.draw(batch);

        float width = dialog.getWidget().getWidth();

        int fontSize = font.getSize();
        if (originString.length() * fontSize > width)
            splitOriginString();
        else{
            content1 = originString;
            content2 = "";
        }

        float height = dialog.getWidget().getHeight();

        font.setColor(Color.BLACK);
        font.draw(batch, content1, point.getX() + width / 15, point.getY() + height * 3 / 4);
        font.draw(batch, content2, point.getX() + width / 15, point.getY() + height * 5 / 8 - fontSize / 2f);
    }

    private void splitOriginString() {
        String[] array = originString.split(" ");

        regroupString(array);
    }

    private void regroupString(String[] array){
        StringBuilder temp1 = new StringBuilder();
        StringBuilder temp2 = new StringBuilder();

        int fontSize = font.getSize();
        float width = dialog.getWidget().getWidth();

        for (String s : array) {
            if (temp1.length() * fontSize < width - (s.length() + 5 )* fontSize)
                temp1.append(s).append(" ");
            else
                temp2.append(s).append(" ");
        }

        content1 = temp1.toString();
        content2 = temp2.toString();
    }
}
