package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nicolas on 21/08/14.
 */
public class Dessin extends View implements View.OnTouchListener {

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        Cercle cercle = new Cercle(140, 130, 30);
        ListeCercles.getInstance().liste.add(cercle);
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
            }
            if (ListeCercles.getInstance().liste.size() == 0) {
                double nb = ((Math.random()) * 10) + 1;
                Log.i("nb généré", String.valueOf(nb));
                for (int i = 0; i < (int) nb; i++) {
                    Cercle cercle = new Cercle((int) (Math.random() * 230) + 30, (int) (Math.random() * 230) + 60, (int) (Math.random() * 40) + 10);
                    ListeCercles.getInstance().liste.add(cercle);
                }
            }
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
}
