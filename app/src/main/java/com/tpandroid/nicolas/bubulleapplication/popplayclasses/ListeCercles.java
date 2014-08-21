package com.tpandroid.nicolas.bubulleapplication.popplayclasses;

import java.util.LinkedList;

/**
 * Created by nicolas on 21/08/14.
 */
public class ListeCercles {

    public LinkedList<Cercle> liste = new LinkedList<Cercle>();
    private static final ListeCercles listeCercles = new ListeCercles();
    private ListeCercles(){}

    public static ListeCercles getInstance() {
        return listeCercles;
    }
}
