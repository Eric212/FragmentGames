package com.ericsospedra.fragmentsgames.modelos.tresenraya;


import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.Game;

/**
 * @author eric
 * El constructor siempre necesitara de dos parametros
 * ya que extiende de Game
 */
//TODO: Implementar
public class TresEnRaya extends Game {
    /**
     * @param nombre
     * @param icono
     */
   private FrameLayout fl;
    public TresEnRaya(String nombre, String icono) {
        super(nombre,icono);
    }

   // fl = findViewById(R.id.fragment_juego_tres_en_raya);
}
