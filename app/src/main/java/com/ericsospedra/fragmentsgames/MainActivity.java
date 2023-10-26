package com.ericsospedra.fragmentsgames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ericsospedra.fragmentsgames.Interfaces.IonClickListenner;
import com.ericsospedra.fragmentsgames.modelos.Game;
import com.ericsospedra.fragmentsgames.modelos.Menu;
import com.ericsospedra.fragmentsgames.modelos.ahorcado.Ahoracdo;
import com.ericsospedra.fragmentsgames.modelos.hundirlaflota.HundirLaFlota;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Menu.IonAttach, IonClickListenner {
    private ArrayList<Game> juegos;

    /**
     * @author eric
     * Enumerado de Juegos donde almacenamos los juegos que estaran
     * disponibles en la app
     */
    private enum Juegos {
        AHORCADO("Ahorcado"),TRESENRAYA("Tres en raya"),HUNDIRLAFLOTA("Hundir la flota");
        private final String text;
        /**
         * @author eric
         * Enumeracion con los nombres de los juegos en la aplicacion,
         * en caso de modificacion añadir en el constructor
         * @param text
         *
         */
        Juegos(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public ArrayList<Game> getJuegos() {
        this.juegos = new ArrayList<>();
        Ahoracdo ahoracdo =new Ahoracdo(Juegos.AHORCADO.toString(),Juegos.AHORCADO.toString().replace(" ","").toLowerCase());
        TresEnRaya tresEnRaya =new TresEnRaya(Juegos.TRESENRAYA.toString(),Juegos.TRESENRAYA.toString().replace(" ","").toLowerCase());
        HundirLaFlota hundirLaFlota =new HundirLaFlota(Juegos.HUNDIRLAFLOTA.toString(),Juegos.HUNDIRLAFLOTA.toString().replace(" ","").toLowerCase());
        juegos.add(ahoracdo);
        juegos.add(tresEnRaya);
        juegos.add(hundirLaFlota);
        return juegos;
    }

    @Override
    public void onClick(int position) {

    }
}