package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by nicolas on 21/08/14.
 */
public class Dessin extends View implements View.OnTouchListener {

    private static int RAYON_MAX = 50;
    private static int RAYON_MIN = 25;
    private Dessin moi = this;
    public Thread t;
    public LinkedList<Cercle> listeCercles;
    private Handler handler;
    private Random random = new Random();
    private int comptePoints = 0;

    private int xMax = 300;
    private int yMax = 300;

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);

        // Récupération des dimensions de l'écran (avec une marge)
        xMax = ListeCercles.getInstance().xMax - RAYON_MAX;
        yMax = ListeCercles.getInstance().yMax - 2 * RAYON_MAX;

        listeCercles = new LinkedList<Cercle>();

        // lancement de la génération des cercles
        t=new Thread(runAddCercles);
        t.run();
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
            Log.i("taille liste", String.valueOf(listeCercles.size()));
            Cercle cercleavirer = null;
            for (Cercle cercle : listeCercles) {
                if ((x < cercle.xc + cercle.rayon) &&
                        x > cercle.xc - cercle.rayon &&
                        y < cercle.yc + cercle.rayon &&
                        y > cercle.yc - cercle.rayon) {
                    cercleavirer = cercle;
                }
            }
            if (cercleavirer != null) {
                listeCercles.remove(cercleavirer);
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
        for (Cercle cercle : listeCercles) {
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
            if (listeCercles.size() <= 10) {

                int x = 0;
                int y = 0;
                int rayon = random.nextInt(RAYON_MAX);

                // Moyen pour que les cercles générés ne se superposent pas
                boolean coordOK = false;
                if (listeCercles.size() !=0){
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
                int i = random.nextInt(1000);
                handler.postDelayed(runAddCercles, (i > 250 ? i : 250));
            }
            else {
                //Toast.makeText()
                t.interrupt();
            }
            //}
            moi.invalidate();

        }
    };

}
