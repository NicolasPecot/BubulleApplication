package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by nicolas on 21/08/14.
 */
public class Cercle {
    int xc, yc, rayon;
    private Paint paint;

    public Cercle(int x, int y, int r) {
        xc = x;
        yc = y;
        rayon = r;
        paint = new Paint();
        paint.setColor(Color.rgb(50, 50, 50));
    }
    public boolean contains(int x, int y) {
        return (x - xc)*(x - xc) + (y - yc)*(y - yc) <= (rayon*rayon);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(xc, yc, rayon, paint);
    }
}
