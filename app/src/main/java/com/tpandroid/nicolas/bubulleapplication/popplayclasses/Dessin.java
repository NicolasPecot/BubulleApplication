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

    private static int RAYON_MAX = 50;
    private static int RAYON_MIN = 25;
    private Dessin moi = this;
    private Handler handler;
    private Random random = new Random();
    private int comptePoints = 0;

    private int xMax = 300;
    private int yMax = 300;

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);

        handler = new Handler();
//        Cercle cercle = new Cercle(140, 130, 30);
//        ListeCercles.getInstance().liste.add(cercle);

        // Récupération des dimensions de l'écran (avec une marge)
        xMax = ListeCercles.getInstance().xMax - RAYON_MAX;
        yMax = ListeCercles.getInstance().yMax - 2 * RAYON_MAX;

        // lancement de la génération des cercles
        runAddCercles.run();
    }

    /**
     * Methode permettant de faire despawn un cercle quand on le touche.
     * <p/>
     * Quand on touche l'écran, la méthode parcours la liste des cercles créés afin de voir si
     * le contact a été fait dans un cercle. Si c'est le cas, on le supprime de la liste.
     *
     * @param v     vue oùa lieu l'event onTouch
     * @param event type d'event onTouch
     * @return boolean
     */
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
                }
            }
            if (cercleavirer != null) {
                ListeCercles.getInstance().liste.remove(cercleavirer);
                comptePoints++;
                Log.i("compteur points", String.valueOf(comptePoints));
            }
        }
        this.invalidate();
        return true;
    }

    /**
     * Affichage des cercles créés dans la liste
     *
     * @param canvas zone de dessin
     */
    @Override
    protected void onDraw(Canvas canvas) {
        for (Cercle cercle : ListeCercles.getInstance().liste) {
            cercle.draw(canvas);
        }
    }

    /**
     * Processus de création continue des cercles.
     * <p/>
     * Tant que la liste contient moins de 10 cercles, le thread se charge d'en créer un et de l'ajouter
     * à la liste. Les coordonnées et le rayon du cercle sont aléatoires (compris dans une fourchette).
     * Une fois le cercle créé (ou pas selon la taille de la liste), on attend un temps aléatoire avant
     * de relancer l'opération (entre 250 et 1000 ms)
     */
    public Runnable runAddCercles = new Runnable() {
        @Override
        public void run() {
            if (ListeCercles.getInstance().liste.size() <= 10) {

                int x = 0;
                int y = 0;
                int rayon = random.nextInt(RAYON_MAX);

                // Moyen pour que les cercles générés ne se superposent pas
                boolean coordOK = false;
                if (ListeCercles.getInstance().liste.size() !=0){
                    while (!coordOK) {
                        x = random.nextInt(xMax) + RAYON_MAX;
                        y = random.nextInt(yMax) + RAYON_MAX;
                        for (Cercle c : ListeCercles.getInstance().liste) {
                            coordOK = !c.contains(x, y);
                        }
                    }
                }

                Cercle cercle = new Cercle((x > xMax ? xMax : x), (y > yMax ? yMax : y),
                        (rayon < RAYON_MIN ? RAYON_MIN : rayon));
                ListeCercles.getInstance().liste.add(cercle);
            }
            //if (comptePoints <= 20) {
            int i = random.nextInt(1000);
            handler.postDelayed(runAddCercles, (i > 250 ? i : 250));
            //}
            moi.invalidate();
        }
    };


}
