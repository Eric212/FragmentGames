package com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.adapters.AdaptadorTableroTresEnRaya;

import java.util.ArrayList;
import java.util.List;

//Aquí crearemos el fragment que mostrará la pantalla principal donde se juega
public class FragmentJuegoTresEnRaya extends Fragment {
    private RecyclerView rvTableroTresEnRaya;
    private AdaptadorTableroTresEnRaya adaptadorTableroTresEnRaya;
    public static boolean turnoO = true;
    public static TextView tvTurno;
    private Button bReiniciar;
    public static ImageView ivJugadaTresEnRaya;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juego_tres_en_raya, container, false);
        rvTableroTresEnRaya = view.findViewById(R.id.rvTableroTresEnRaya);
        tvTurno = view.findViewById(R.id.tvTurno);
        bReiniciar = view.findViewById(R.id.bReiniciar);
        ivJugadaTresEnRaya = view.findViewById(R.id.ivJugadaTresEnRaya);
        List<Bitmap> arrBm = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            arrBm.add(null);
        }
        adaptadorTableroTresEnRaya = new AdaptadorTableroTresEnRaya(getContext(), arrBm);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvTableroTresEnRaya.setLayoutManager(layoutManager);
        rvTableroTresEnRaya.setAdapter(adaptadorTableroTresEnRaya);
        bReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuegoTresEnRaya();
            }
        });
        return view;
    }

    private void reiniciarJuegoTresEnRaya() {
        List<Bitmap> arrBm = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            arrBm.add(null);
        }
        adaptadorTableroTresEnRaya.setArrBm(arrBm);
        adaptadorTableroTresEnRaya.notifyDataSetChanged();
        turnoO = true;
        tvTurno.setText("Turno de O");
        ivJugadaTresEnRaya.setImageBitmap(null);

    }
}