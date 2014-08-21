package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by nicolas on 21/08/14.
 */
public class Dessin extends View implements View.OnTouchListener {

    private LinkedList<Cercle> listeCercles;

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        listeCercles = new LinkedList<Cercle>();
        Cercle cercle = new Cercle(140, 130, 30);
        listeCercles.add(cercle);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.i("taille liste", String.valueOf(listeCercles.size()));
            Cercle cercleavirer = null;
            for (Cercle cercle : listeCercles) {
                if ((x < cercle.xc + cercle.rayon) &&
                        x > cercle.xc - cercle.rayon &&
                        y < cercle.yc + cercle.rayon &&
                        y > cercle.yc - cercle.rayon) {
                    //listeCercles.remove(cercle);
                    cercleavirer = cercle;
                    Log.i("cercle touché", "kjdsnfshgbdf");
                }
            }
            if (cercleavirer != null) {
                listeCercles.remove(cercleavirer);
            }
            if (listeCercles.size() == 0) {
                double nb = ((Math.random()) * 10) + 1;
                Log.i("nb généré", String.valueOf(nb));
                for (int i = 0; i < (int) nb; i++) {
                    Cercle cercle = new Cercle((int) (Math.random() * 230) + 30, (int) (Math.random() * 230) + 60, (int) (Math.random() * 40) + 10);
                    listeCercles.add(cercle);
                }
            }
        }
        this.invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Cercle cercle : listeCercles) {
            cercle.draw(canvas);
        }
    }
}
