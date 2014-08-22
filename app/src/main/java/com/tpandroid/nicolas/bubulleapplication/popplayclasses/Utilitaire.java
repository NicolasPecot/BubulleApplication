package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import java.util.LinkedList;

/**
 * Created by nicolas on 21/08/14.
 */
public class Utilitaire {

    // Liste contenant les cercles
    @Deprecated
    public LinkedList<Cercle> liste = new LinkedList<Cercle>();

    // Astuce pour transmettre les dimensions du layout à Dessin (qui est le View à afficher)
    public int xMax, yMax;
    public int cerclesPerdus = 0;

    // Pattern singleton pour "stocker" la liste
    private static final Utilitaire UTILITAIRE = new Utilitaire();
    private Utilitaire(){}

    public static Utilitaire getInstance() {
        return UTILITAIRE;
    }
}
