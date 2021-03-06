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

    /**
     * Methode permettant de vérifier si les coordinnées passées en parametre sont dans le cercle courant
     * @param x abscisse vérifiée
     * @param y ordonnée vérifiée
     * @return true si on est dedans, false sinon
     */
    public boolean contains(int x, int y) {
        return (x - xc)*(x - xc) + (y - yc)*(y - yc) <= (2*rayon*rayon);
    }

    /**
     * Methode permettant de dessiner le cercle sur le canvas passé en paramètre
     * @param canvas zone de dessin
     */
    public void draw(Canvas canvas) {
        canvas.drawCircle(xc, yc, rayon, paint);
    }

    /**
     * Thread de réduction progressive du rayon du cercle courant
     */
    public Runnable reductionCercle = new Runnable() {
        @Override
        public void run() {
            rayon -= 1;
            if (rayon == 10) {
                t.interrupt();
                Log.i("cercles perdus", String.valueOf(Utilitaire.getInstance().cerclesPerdus));
            } else {
                h.postDelayed(this, 60);
            }
        }
    };
}
