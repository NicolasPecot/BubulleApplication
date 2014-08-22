package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;

/**
 * Created by nicolas on 21/08/14.
 */
public class Cercle {
    int xc, yc, rayon;
    private Cercle c = this;
    public Handler h;
    //public Dessin d;
    public Thread t;
    private Paint paint;

    public Cercle(int x, int y, int r) {
        xc = x;
        yc = y;
        rayon = r;
        h = new Handler();
        paint = new Paint();
        paint.setColor(Color.rgb(50, 50, 50));
        t = new Thread(reductionCercle);
        t.start();
    }
    public boolean contains(int x, int y) {
        return (x - xc)*(x - xc) + (y - yc)*(y - yc) <= (2*rayon*rayon);
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(xc, yc, rayon, paint);
    }

    public Runnable reductionCercle = new Runnable() {
        @Override
        public void run() {
            rayon -= 1;
            //Log.i("Thread Cercle", "taille rayon : " + String.valueOf(rayon));
            if (rayon <= 5) {
                Dessin.listeCercles.remove(c);
                Utilitaire.getInstance().cerclesPerdus++;
                Log.i("cercles perdus", String.valueOf(Utilitaire.getInstance().cerclesPerdus));
            } else {
                h.postDelayed(this, 60);
            }
        }
    };
}
