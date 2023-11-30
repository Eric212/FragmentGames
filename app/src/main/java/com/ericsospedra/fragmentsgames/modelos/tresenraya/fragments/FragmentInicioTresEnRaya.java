package com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;


//Aquí crearemos el fragment para la pantalla de inicio del juego
public class FragmentInicioTresEnRaya extends Fragment {

    private Button bJugar;
    RadioButton rbUnJugador;
    RadioButton rbDosJugadores;
    private int numJugadoresTresEnRaya;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_tres_en_raya, container, false);
        bJugar = view.findViewById(R.id.bJugarTresEnRaya);
        rbUnJugador = view.findViewById(R.id.rbUnJugador);
        rbDosJugadores = view.findViewById(R.id.rbDosJugadores);
        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificar qué opción se seleccionó
                if (rbUnJugador.isChecked()) {
                    // Iniciar juego para 1 jugador
                    numJugadoresTresEnRaya = 1;
                    Toast.makeText(getContext(), "rb jugador 1 pulsado" + numJugadoresTresEnRaya, Toast.LENGTH_SHORT).show();
                    iniciarJuego();
                } else if (rbDosJugadores.isChecked()) {
                    // Iniciar juego para 2 jugadores
                    numJugadoresTresEnRaya = 2;
                    Toast.makeText(getContext(), "rb jugador 2 pulsado" + numJugadoresTresEnRaya, Toast.LENGTH_SHORT).show();
                    iniciarJuego();
                } else {
                    // Mostrar un Toast indicando que se debe seleccionar una opción
                    Toast.makeText(getContext(), "Elige una opción de jugadores", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void iniciarJuego() {
        //cuando desde home pulsemos el botón jugar, los jugadores comenzarán con puntuación 0
        TresEnRaya.puntuacionO = 0;
        TresEnRaya.puntuacionX = 0;

        FragmentJuegoTresEnRaya fragmentJuegoTresEnRaya = new FragmentJuegoTresEnRaya();
        fragmentJuegoTresEnRaya.setNumJugadores(numJugadoresTresEnRaya);

        // Realizar la transacción del fragmento
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(FragmentJuegoTresEnRaya.TAG);
        transaction.replace(R.id.fcvMenu, FragmentJuegoTresEnRaya.class, null).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Restablece el título de la Toolbar a "Mini juegos" cuando el fragmento se destruye u oculta
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Mini juegos");
    }

}