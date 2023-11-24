package com.ericsospedra.fragmentsgames.modelos.tresenraya;


import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.Game;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentJuegoTresEnRaya;

/**
 * @author eric
 * El constructor siempre necesitara de dos parametros
 * ya que extiende de Game
 */
//TODO: Implementar
public class TresEnRaya extends Game  {
    /**
     * @param nombre
     * @param icono
     */
   private FrameLayout fl;
   private int numJugadores;
   public static int puntuacionX = 0, puntuacionO = 0;
    public TresEnRaya(String nombre, String icono) {
        super(nombre,icono);
    }
}
