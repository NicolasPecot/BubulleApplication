package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import java.util.LinkedList;

/**
 * Created by nicolas on 21/08/14.
 */
public class ListeCercles {

    // Liste contenant les cercles
    public LinkedList<Cercle> liste = new LinkedList<Cercle>();

    // Astuce pour transmettre les dimensions du layout à Dessin (qui est le View à afficher)
    public int xMax, yMax;

    // Pattern singleton pour "stocker" la liste
    private static final ListeCercles listeCercles = new ListeCercles();
    private ListeCercles(){}

    public static ListeCercles getInstance() {
        return listeCercles;
    }
}
