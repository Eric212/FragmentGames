package com.ericsospedra.fragmentsgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ericsospedra.fragmentsgames.Interfaces.IonClickListenner;
import com.ericsospedra.fragmentsgames.modelos.Game;
import com.ericsospedra.fragmentsgames.modelos.Menu;
import com.ericsospedra.fragmentsgames.modelos.ahorcado.Ahorcado;
import com.ericsospedra.fragmentsgames.modelos.hundirlaflota.HundirLaFlota;
import com.ericsospedra.fragmentsgames.modelos.hundirlaflota.fragments.HundirLaFlotaFragment;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentInicioTresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentJuegoTresEnRaya;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Menu.IonAttach, IonClickListenner {
    private ArrayList<Game> juegos;
    private Toolbar toolbar;
    private int numJugadoresTresEnRaya;


    /**
     * @author eric
     * Enumerado de Juegos donde almacenamos los juegos que estaran
     * disponibles en la app
     */
    private enum Juegos {
        AHORCADO("Ahorcado"), TRESENRAYA("Tres en raya"), HUNDIRLAFLOTA("Hundir la flota");
        private final String text;

        /**
         * @param text
         * @author eric
         * Enumeracion con los nombres de los juegos en la aplicacion,
         * en caso de modificacion añadir en el constructor
         */
        Juegos(final String text) {
            this.text = text;
        }

        @NonNull
        @Override
        public String toString() {
            return text;
        }
    }

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tbMenu);
        toolbar.setTitle("Mini juegos");
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        if (Objects.requireNonNull(manager.findFragmentById(R.id.fcvMenu)).getClass().equals(Menu.class)) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        } else {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_ahorcado) {
            Toast.makeText(this, "Ahorcado", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Ahorcado");
        } else if (itemId == R.id.action_tresenraya) {
            Toast.makeText(this, "Tres en raya", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Tres En Raya");
            manager.beginTransaction().replace(R.id.fcvMenu, FragmentInicioTresEnRaya.class, null).commit();
        } else {
            Toast.makeText(this, "Hundir la flota", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Hundir la flota");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<Game> getJuegos() {
        this.juegos = new ArrayList<>();
        Ahorcado ahorcado = new Ahorcado(Juegos.AHORCADO.toString(), Juegos.AHORCADO.toString().replace(" ", "").toLowerCase());
        TresEnRaya tresEnRaya = new TresEnRaya(Juegos.TRESENRAYA.toString(), Juegos.TRESENRAYA.toString().replace(" ", "").toLowerCase());
        HundirLaFlota hundirLaFlota = new HundirLaFlota(Juegos.HUNDIRLAFLOTA.toString(), Juegos.HUNDIRLAFLOTA.toString().replace(" ", "").toLowerCase());
        juegos.add(ahorcado);
        juegos.add(tresEnRaya);
        juegos.add(hundirLaFlota);
        return juegos;
    }

    @Override
    public void onClick(int position) {
        switch (juegos.get(position).getIcono()) {
            case "ahorcado":
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMenu, Menu.class, null).commit();
                invalidateOptionsMenu();
                break;
            case "tresenraya":
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMenu, FragmentInicioTresEnRaya.class, null).commit();
                toolbar.setTitle("Tres En Raya");
                invalidateOptionsMenu();
                break;
            case "hundirlaflota":
                manager.beginTransaction().setReorderingAllowed(true).addToBackStack(null).replace(R.id.fcvMenu, HundirLaFlotaFragment.class, null).commit();
                invalidateOptionsMenu();
                break;
        }
    }
}