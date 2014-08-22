package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by nicolas on 21/08/14.
 */
public class Dessin extends View implements View.OnTouchListener {

    public static final int RAYON_MAX = 55;
    public static final int RAYON_MIN = 55;
    public static final int TIMER_MIN = 500;
    public static final int TIMER_MAX = 1000;
    public static final int NB_MAX_CERCLES = 20;
    public int difficulty = 0;
    public Thread t;
    public static LinkedList<Cercle> listeCercles;
    public Dessin moi = this;
    private Handler handler;
    private Random random = new Random();
    private int comptePoints = 0;

    private int xMax = 300;
    private int yMax = 300;

    public Dessin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);

        // Récupération des dimensions de l'écran (avec une marge)
        xMax = Utilitaire.getInstance().xMax - RAYON_MAX;
        yMax = Utilitaire.getInstance().yMax - 2 * RAYON_MAX;

        listeCercles = new LinkedList<Cercle>();
        handler = new Handler();

        // lancement de la génération des cercles
        t = new Thread(runAddCercles);
        t.run();

        affichage.run();
        difficultyOverTime.run();
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
            Cercle cercleavirer = null;
            for (Cercle cercle : listeCercles) {
                if ((x < cercle.xc + cercle.rayon) &&
                        x > cercle.xc - cercle.rayon &&
                        y < cercle.yc + cercle.rayon &&
                        y > cercle.yc - cercle.rayon) {
                    cercle.t.interrupt();
                    cercleavirer = cercle;
                }
            }
            if (cercleavirer != null) {
                //cercleavirer.t.interrupt();
                listeCercles.remove(cercleavirer);
                comptePoints++;
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

            // Parcours de la liste de cercles afin d'identifier ceux ayant été trop réduits en rayon
            // et de les supprimer. Le compteur cerclesPerdus est incrémenté dans ce cas.
            Cercle cc = null;
            for (Cercle c : listeCercles) {
                if (c.rayon == 10) {
                    c.t.interrupt();
                    cc = c;
                    Utilitaire.getInstance().cerclesPerdus++;
                }
            }
            if (cc != null) {
                listeCercles.remove(cc);
            }

            if (listeCercles.size() <= NB_MAX_CERCLES) {

                int x = random.nextInt(xMax) + RAYON_MAX;
                int y = random.nextInt(yMax) + RAYON_MAX;
                int rayon = random.nextInt(RAYON_MAX);

                // Moyen pour que les cercles générés ne se superposent pas (trop)
                boolean coordOK = false;
                if (listeCercles.size() != 0) {
                    while (!coordOK) {
                        x = random.nextInt(xMax) + RAYON_MAX;
                        y = random.nextInt(yMax) + RAYON_MAX;
                        coordOK = true;
                        for (Cercle c : listeCercles) {
                            coordOK = !c.contains(x, y);
                            if (!coordOK){
                                break;
                            }
                        }
                    }
                }

                // Création et ajout du nouveau cercle
                Cercle cercle = new Cercle((x > xMax ? xMax : x), (y > yMax ? yMax : y),
                        (rayon < RAYON_MIN ? RAYON_MIN : rayon));
                listeCercles.add(cercle);

                // Si on a loupé trop de cercles, on perd : interruption du thread de spawn et popup
                if (Utilitaire.getInstance().cerclesPerdus == 5) {
                    Toast.makeText(getContext(), "Perdu ! \n " + String.valueOf(comptePoints) + " Points", Toast.LENGTH_SHORT).show();
                    t.interrupt();
                } else {
                    int i = random.nextInt(TIMER_MAX - difficulty);
                    handler.postDelayed(runAddCercles, (i > TIMER_MIN-difficulty ? i : TIMER_MIN-difficulty));
                }
            } else {
                Toast.makeText(getContext(), "Perdu ! \n " + String.valueOf(comptePoints) + " Points", Toast.LENGTH_SHORT).show();
                t.interrupt();
            }
            moi.invalidate();
        }
    };

    /**
     * Parcours continu de la liste des cercles afin de pouvoir avoir une animation fluide
     * pour la réduction des rayons des cercles.
     */
    public Runnable affichage = new Runnable() {
        @Override
        public void run() {
            moi.invalidate();
            handler.postDelayed(affichage, 60);
        }
    };

    /**
     * Augmentation de la difficulté, donc de la vitesse d'apparition de cercles
     * avec le temps. -1ms toutes les secondes jusqu'a un plafond de 200ms.
     */
    public Runnable difficultyOverTime = new Runnable() {
        @Override
        public void run() {
            if (difficulty < 200) {
                difficulty++;
                handler.postDelayed(difficultyOverTime, 1000);
            } else {
                Log.i("Difficulté Max", "atteinte");
            }
        }
    };

}
