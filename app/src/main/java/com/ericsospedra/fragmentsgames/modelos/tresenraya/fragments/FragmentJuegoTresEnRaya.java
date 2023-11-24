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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.adapters.AdaptadorTableroTresEnRaya;

import java.util.ArrayList;
import java.util.List;

//Aquí crearemos el fragment que mostrará la pantalla donde se juega
public class FragmentJuegoTresEnRaya extends Fragment {
    private Context context;

    public interface IOnAttachListener {
        int getNumJugadoresTresEnRaya();
    }

    private RecyclerView rvTableroTresEnRaya; // El Recycler lo queremos para mostrar y gestionar dinámicamente las celdas del tablero del juego en la interfaz de usuario (se adapta a los cambios según las interacciones del usuario). Las celdas en el tablero pueden cambiar según las interacciones del usuario. Cada celda puede contener diferentes imágenes (representando X, O o ninguna) y estas imágenes pueden cambiar durante el juego.
    private AdaptadorTableroTresEnRaya adaptadorTableroTresEnRaya;
    public static boolean turnoO = true; //esta variable servirá para determinar el turno de cada jugador
    public static TextView tvTurno, tvPuntuacionO, tvPuntuacionX, tvGanador; //texView para indicar el turno de cada usuario, para indicar si el ganador es O o X, y el ganador final
    private Button bReiniciar, bGanaJugarDeNuevo, bHome;
    public static ImageView ivJugadaTresEnRaya; //esta imagen es donde se insertará la línea que representa quién gana, cruzando el tablero de lado a lado (dicha línea)
    public static ImageView ivSimboloGanador;
    public static RelativeLayout rlGanador; //este diseño se mostrará solamente si alguien gana una partida indicando el ganador
    public static String TAG = FragmentJuegoTresEnRaya.class.getName();
    private int numJugadoresTresEnRaya;
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
        //Creamos las celdas/cuadrículas del tablero, utilizaremos la clase Bitmap, para representar las imágenes de las celdas en el tablero del juego
        List<Bitmap> arrBmCeldas = new ArrayList<>(); //Bitmap es una clase que representa una imagen. Los objetos Bitmap se utilizan para contener, manipular y mostrar imágenes.
        for(int i = 0; i < 9; i++){ //aquí crearemos 9 espacios donde poder manipular imágenes (a este espacio donde poder insertar imágenes se añadirá cada jugada con el símbolo X u O
            arrBmCeldas.add(null); //inicialmente las cuadrículas/celdas estarán vacías. Este arreglo representará el estado inicial del tablero.
        }

        /*
        CREAMOS EL ADAPTADOR
        Que gestionar los datos y cómo se presentan en la cuadrícula del recyclerview.
        Aquí le diremos al adaptador que tiene 9 elementos para mostrar en el RecyclerView.
        Cuando asignamos este adaptador al RecyclerView, el RecyclerView utiliza la cantidad de elementos en la lista (arrBmCeldas)
        que le hemos pasado al adaptador, para determinar el número total de elementos y, en combinación con el GridLayoutManager
        que vamos a configurar para 3 columnas, calculará implícitamente el número de filas necesario para mostrar todos
        los elementos en una cuadrícula de 3x3.
         */

        adaptadorTableroTresEnRaya = new AdaptadorTableroTresEnRaya(getContext(), arrBmCeldas, numJugadoresTresEnRaya); //al adaptador le pasamos el contexto y el arreglo de Bitmaps, es decir la cuadrícula con 9 celdas vacías, para que en tiempo de ejecución se vaya pintando en ellas las interacciones del usuario en el juego (se irán insertando las X y las O).

        /*
        A continuación CREAMOS un RECYCLERVIEW en forma de cuadrícula, que será el tablero

        Vamos a establecer el GridLayoutManager para organizar las celdas en una cuadrícula de 3 columnas y 3 filas.
        Cada elemento en arrBmCeldas se corresponde con una celda del tablero. El número de filas en el GridLayoutManager
        está implícitamente determinado por la cantidad total de elementos y el número de columnas. El número de elementos
        en el RecyclerView se determina por la cantidad de elementos en la lista de datos que se pasa al adaptador.
        En este caso, la lista de datos es arrBmCeldas, que contiene 9 elementos (uno para cada celda en la cuadrícula).
        En este método onCreateView del FragmentJuegoTresEnRaya, estamos creando el adaptador con esta lista de datos.
        Número de filas= número total de elementos del recyclerview/número de columnas. Como tenemos 9 celdas (elementos),
        y estamos configurando 3 columnas, tendremos 9/3= 3 filas. Por lo tanto, en este caso, tendremos una cuadrícula de 3x3.

        Este RecyclerView tendrá asignado el adaptador que hemos creado (AdaptadorTableroTresEnRaya).

        Pero antes, le damos la forma deseada al recyclerview.
         */
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
                rlGanador.setVisibility(View.INVISIBLE);
                reiniciarJuegoTresEnRaya();
            }
        });
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuegoTresEnRaya();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    //Este método se utiliza para reiniciar la partida, ya sea porque se ha terminado y se desea jugar de nuevo, o porque se quiera reiniciar
    private void reiniciarJuegoTresEnRaya() {
        List<Bitmap> arrBmCeldas = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            arrBmCeldas.add(null); //vaciamos las celdas de las posibles imágenes que haya (X e U) poniéndolas a null
        }
        adaptadorTableroTresEnRaya.setArrBmCeldas(arrBmCeldas); //le pasamos el array de nuevo vacío para comenzar de nuevo
        adaptadorTableroTresEnRaya.notifyDataSetChanged(); //notificamos que ha habido cambios al adaptador
        turnoO = true; //comienza el turno de nuevo en O (por defecto lo elegí así)
        tvTurno.setText("Turno de O"); //indicamos de quién es el turno inicial
        ivJugadaTresEnRaya.setImageBitmap(null); //quitamos cualquier posible línea que indicara una jugada ganadora
        TresEnRaya.puntuacionO = 0;
        TresEnRaya.puntuacionX = 0;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Asegúrate de que la actividad implementa la interfaz IOnAttachListener
        if (context instanceof IOnAttachListener) {
            IOnAttachListener attachListener = (IOnAttachListener) context;
            // Obtenemos los datos
            numJugadoresTresEnRaya = attachListener.getNumJugadoresTresEnRaya();
        } else {
            throw new ClassCastException(context.toString() + " debe implementar la interfaz IOnAttachListener");
        }
    }
}