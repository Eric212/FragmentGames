package com.ericsospedra.fragmentsgames.Adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.Interfaces.IonClickListenner;
import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.Game;

import java.util.ArrayList;

/**
 * @author eric
 * Adaptador del RecyclerView Menu
 * Se encarga tanto de la creacion de los items como de capturar los clicks
 */
public class AdaptadorMenu extends RecyclerView.Adapter<AdaptadorMenu.GameViewHolder> {
    private final ArrayList<Game> juegos;
    private final IonClickListenner listener;

    /**
     * @author eric
     * @param juegos
     * @param listener
     */
    public AdaptadorMenu(ArrayList<Game> juegos, IonClickListenner listener) {
        this.juegos = juegos;
        this.listener = listener;
    }

    /**
     * @author eric
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return GameViewHolder El cual tiene cargado ya la view que le corresponde
     */
    @NonNull
    @Override
    public AdaptadorMenu.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_menu,parent,false));
    }

    /**
     * @author eric
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     * Sustituimos el onBindViewHolder por defecto por el nuestro accediendo directamente
     * al onBindGame que hemos creado en GameViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull AdaptadorMenu.GameViewHolder holder, int position) {
        holder.onBindGame(juegos.get(position));
    }

    /**
     * @author eric
     * @return int Devolvemos el tamaño de la lista que alimenta al RecyclerView
     */
    @Override
    public int getItemCount() {
        return juegos.size();
    }

    /**
     * @author eric
     *Esta es nuesta clase ViewHolder personalizada para este escenario
     */
    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView nombre;
        private final ImageView icono;

        /**
         * @param itemView
         * Con el view correspondiente valorizamos nuestro atributos
         */
        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvJuegoTresEnRaya);
            this.icono = itemView.findViewById(R.id.ivImagen);
            itemView.setOnClickListener(this);
        }

        /**
         * @param juego
         * Aqui accedemos a los datos del item que nos devuelve el adaptador al
         * recorrer la lista y seteamos los el nombre y el icono
         */
        @SuppressLint("DiscouragedApi")
        public void onBindGame(Game juego) {
            nombre.setText(juego.getNombre());
            icono.setImageResource(itemView.getContext().getResources().getIdentifier(juego.getIcono(),"drawable",itemView.getContext().getPackageName()));
            itemView.setOnClickListener(this);
        }

        /**
         * @param v The view that was clicked.
         * Accedemos al listenner que implementa el onClick y le pasamos
         * la posición en la que se encuentra el item que a sido seleccionado
         */
        @Override
        public void onClick(View v) {
            if(listener !=null){
                listener.onClick(getAdapterPosition());
            }
        }
    }
}
