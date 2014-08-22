package com.tpandroid.nicolas.bubulleapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.tpandroid.nicolas.bubulleapplication.popplayclasses.Utilitaire;
import com.tpandroid.nicolas.bubulleapplication.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class PopPlayActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            // Masquage de la barre d'actions
            getActionBar().hide();
        }

        // Récupération et stockage des dimensions de l'écran afin d'avoir un affichage sur toute la surface
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Utilitaire.getInstance().xMax = dm.widthPixels;
        Utilitaire.getInstance().yMax = dm.heightPixels;

        Utilitaire.getInstance().cerclesPerdus = 0;

        setContentView(R.layout.activity_pop_play);
    }

    /**
     * Fin de l'activity quand on appuie sur le bouton retour
     */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
