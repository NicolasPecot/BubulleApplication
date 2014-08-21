package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

/**
 * Created by nicolas on 21/08/14.
 */
@Deprecated
public class ThreadAjoutCercles implements Runnable {

    @Override
    public void run() {
        int x = ((int) (Math.random()*100))+1;
        int y = ((int) (Math.random()*100)) + 30;
        int rayon = ((int) (Math.random()*10) + 10);
        Cercle cercle = new Cercle(x, y, rayon);
        ListeCercles.getInstance().liste.add(cercle);
    }
}
