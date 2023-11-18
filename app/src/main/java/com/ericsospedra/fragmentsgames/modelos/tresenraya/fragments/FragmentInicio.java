package com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ericsospedra.fragmentsgames.R;

//Aquí crearemos el fragment para la pantalla de inicio del juego
public class FragmentInicio extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_tres_en_raya, container, false);
        return view;
    }
}