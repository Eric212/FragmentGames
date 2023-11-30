package com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.adapters.AdaptadorTableroTresEnRaya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Aquí crearemos el fragment que mostrará la pantalla donde se juega
public class FragmentJuegoTresEnRaya extends Fragment {

    private Context context;
    private RecyclerView rvTableroTresEnRaya; // El Recycler lo queremos para mostrar y gestionar dinámicamente las celdas del tablero del juego en la interfaz de usuario (se adapta a los cambios según las interacciones del usuario). Las celdas en el tablero pueden cambiar según las interacciones del usuario. Cada celda puede contener diferentes imágenes (representando X, O o ninguna) y estas imágenes pueden cambiar durante el juego.
    private AdaptadorTableroTresEnRaya adaptadorTableroTresEnRaya;
    FragmentInicioTresEnRaya fragmentInicioTresEnRaya;
    public static TextView tvTurno, tvPuntuacionO, tvPuntuacionX, tvGanador; //texView para indicar el turno de cada usuario, para indicar si el ganador es O o X, y el ganador final
    private Button bReiniciar, bGanaJugarDeNuevo, bHome;
    public static ImageView ivJugadaTresEnRaya; //esta imagen es donde se insertará la línea que representa quién gana, cruzando el tablero de lado a lado (dicha línea)
    public static ImageView ivSimboloGanador;
    public static RelativeLayout rlGanador; //este diseño se mostrará solamente si alguien gana una partida indicando el ganador
    public static String TAG = FragmentJuegoTresEnRaya.class.getName();
    private List<Bitmap> arrBmCeldas;
    private int numJugadores;
    private boolean isTurnoO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_juego_tres_en_raya, container, false);
        rvTableroTresEnRaya = view.findViewById(R.id.rvTableroTresEnRaya);
        tvTurno = view.findViewById(R.id.tvTurno);
        bReiniciar = view.findViewById(R.id.bReiniciar);
        bGanaJugarDeNuevo = view.findViewById(R.id.bGanaJugarDeNuevo);
        bHome = view.findViewById(R.id.bGanaHome);
        ivJugadaTresEnRaya = view.findViewById(R.id.ivJugadaTresEnRaya);
        rlGanador = view.findViewById(R.id.rlGanador);
        tvPuntuacionX = view.findViewById(R.id.tvPuntuacionX);
        tvPuntuacionO = view.findViewById(R.id.tvPuntuacionO);
        tvGanador = view.findViewById(R.id.tvGanador);
        ivSimboloGanador = view.findViewById(R.id.ivSimboloGanador);
        if(numJugadores == 1) {
            Toast.makeText(getContext(), "numJugadores" + numJugadores, Toast.LENGTH_SHORT).show();
        } else {
            numJugadores = 2;
        }

        isTurnoO = elegirTurnoAlAzar();

        //Creamos las celdas/cuadrículas del tablero, utilizaremos la clase Bitmap, para representar las imágenes de las celdas en el tablero del juego
        arrBmCeldas = new ArrayList<>(); //Bitmap es una clase que representa una imagen. Los objetos Bitmap se utilizan para contener, manipular y mostrar imágenes.
        for(int i = 0; i < 9; i++){ //aquí crearemos 9 espacios donde poder manipular imágenes (a este espacio donde poder insertar imágenes se añadirá cada jugada con el símbolo X u O
            arrBmCeldas.add(null); //inicialmente las cuadrículas/celdas estarán vacías. Este arreglo representará el estado inicial del tablero.
        }
        adaptadorTableroTresEnRaya = new AdaptadorTableroTresEnRaya(getContext(), arrBmCeldas, numJugadores, isTurnoO); //al adaptador le pasamos el contexto y el arreglo de Bitmaps, es decir la cuadrícula con 9 celdas vacías, para que en tiempo de ejecución se vaya pintando en ellas las interacciones del usuario en el juego (se irán insertando las X y las O).
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3); //establecemos la forma de cuadrícula
        rvTableroTresEnRaya.setLayoutManager(layoutManager); //le asignamos la cuadrícula a nuestro RecyclerView
        rvTableroTresEnRaya.setAdapter(adaptadorTableroTresEnRaya); //asignamos el adaptador al RecyclerView

        bReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuegoTresEnRaya();
            }
        });
        bGanaJugarDeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               jugarDeNuevo();
            }
        });
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuegoTresEnRaya();
                // Realizar la transacción del fragmento
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(FragmentJuegoTresEnRaya.TAG);
                transaction.replace(R.id.fcvMenu, FragmentInicioTresEnRaya.class, null).commit();
            }
        });
        return view;
    }

    //Este método se utiliza para reiniciar la partida, ya sea porque se ha terminado y se desea jugar de nuevo, o porque se quiera reiniciar
    private void reiniciarJuegoTresEnRaya() {
        arrBmCeldas = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            arrBmCeldas.add(null); //vaciamos las celdas de las posibles imágenes que haya (X e U) poniéndolas a null
        }
        adaptadorTableroTresEnRaya.setArrBmCeldas(arrBmCeldas); //le pasamos el array de nuevo vacío para comenzar de nuevo
        adaptadorTableroTresEnRaya.notifyDataSetChanged(); //notificamos que ha habido cambios al adaptador
        isTurnoO = elegirTurnoAlAzar();
        ivJugadaTresEnRaya.setImageBitmap(null); //quitamos cualquier posible línea que indicara una jugada ganadora
        TresEnRaya.puntuacionO = 0;
        TresEnRaya.puntuacionX = 0;

    }

    public void jugarDeNuevo() {
        arrBmCeldas = new ArrayList<>();
        rlGanador.setVisibility(View.INVISIBLE);
        for(int i = 0; i < 9; i++){
            arrBmCeldas.add(null); //vaciamos las celdas de las posibles imágenes que haya (X e U) poniéndolas a null
        }
        adaptadorTableroTresEnRaya.setArrBmCeldas(arrBmCeldas); //le pasamos el array de nuevo vacío para comenzar de nuevo
        ivJugadaTresEnRaya.setImageBitmap(null);
        adaptadorTableroTresEnRaya.notifyDataSetChanged(); //notificamos que ha habido cambios al adaptador
        isTurnoO = elegirTurnoAlAzar();
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public boolean elegirTurnoAlAzar() {
        Random random = new Random();
        isTurnoO = random.nextBoolean();
        Toast.makeText(getContext(), "turnoO: " + isTurnoO, Toast.LENGTH_SHORT).show();
        if(isTurnoO) {
            tvTurno.setText("Turno de O"); //indicamos de quién es el turno inicial
        }else{
            tvTurno.setText("Turno de X");
        }
        return isTurnoO;
    }

}