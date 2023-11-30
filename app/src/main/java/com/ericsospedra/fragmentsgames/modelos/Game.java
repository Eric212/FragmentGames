package com.ericsospedra.fragmentsgames.modelos;

import android.widget.ImageView;

import androidx.fragment.app.Fragment;

/**
 * @author eric
 * Cuidado con Game es una clase Abstracta no se puede instanciar
 * @throws Fragment.InstantiationException
 */
public abstract class Game {
    private String nombre;
    private String icono;
    /**
     *
     * @param nombre
     * @param icono
     */
    public Game(String nombre,String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }
    public Game(){

    }
    /**
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return String
     */
    public String getIcono() {
        return icono;
    }
}
