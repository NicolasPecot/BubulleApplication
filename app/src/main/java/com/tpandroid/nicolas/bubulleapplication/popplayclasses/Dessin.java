package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by nicolas on 21/08/14.
 */
public class Dessin extends View implements View.OnTouchListener {

    private Dessin moi = this;
    private Handler handler;
    private Random random = new Random();
    private int comptePoints = 0;

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        handler = new Handler();
        Cercle cercle = new Cercle(140, 130, 30);
        ListeCercles.getInstance().liste.add(cercle);
        runAddCercles.run();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.i("taille liste", String.valueOf(ListeCercles.getInstance().liste.size()));
            Cercle cercleavirer = null;
            for (Cercle cercle : ListeCercles.getInstance().liste) {
                if ((x < cercle.xc + cercle.rayon) &&
                        x > cercle.xc - cercle.rayon &&
                        y < cercle.yc + cercle.rayon &&
                        y > cercle.yc - cercle.rayon) {
                    cercleavirer = cercle;
                    Log.i("cercle touché", "kjdsnfshgbdf");
                }
            }
            if (cercleavirer != null) {
                ListeCercles.getInstance().liste.remove(cercleavirer);
                comptePoints++;
                Log.i("compteur points", String.valueOf(comptePoints));
            }
            /*if (ListeCercles.getInstance().liste.size() == 0) {
                double nb = ((Math.random()) * 10) + 1;
                Log.i("nb généré", String.valueOf(nb));
                for (int i = 0; i < (int) nb; i++) {
                    Cercle cercle = new Cercle((int) (Math.random() * 230) + 30, (int) (Math.random() * 230) + 60, (int) (Math.random() * 40) + 10);
                    ListeCercles.getInstance().liste.add(cercle);
                }
            }*/
        }
        this.invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Cercle cercle : ListeCercles.getInstance().liste) {
            cercle.draw(canvas);
        }
    }

    public Runnable runAddCercles = new Runnable() {
        @Override
        public void run() {
            if (ListeCercles.getInstance().liste.size() <= 10) {
                int x = random.nextInt(300);
                int y = random.nextInt(300) + 30;
                int rayon = random.nextInt(40);
                Cercle cercle = new Cercle(x, y, (rayon < 25 ? 25 : rayon));
                ListeCercles.getInstance().liste.add(cercle);
            }
            //if (comptePoints <= 20) {
            int i = random.nextInt(1000);
            Log.i("delai", String.valueOf(i));
            handler.postDelayed(runAddCercles, (i > 250 ? i : 250));
            //}
            moi.invalidate();
        }
    };


}
