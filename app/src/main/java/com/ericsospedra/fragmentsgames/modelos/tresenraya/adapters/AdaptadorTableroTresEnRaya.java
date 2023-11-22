package com.ericsospedra.fragmentsgames.modelos.tresenraya.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentJuegoTresEnRaya;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorTableroTresEnRaya extends RecyclerView.Adapter<AdaptadorTableroTresEnRaya.ViewHolder>{
    private Context context;
    private List<Bitmap> arrBm, arrJugadas;
    private Bitmap bmX, bmO;
    private Animation animationXyO, animationJugada;
    public AdaptadorTableroTresEnRaya(Context context, List<Bitmap> arrBm) {
        this.context = context;
        this.arrBm = arrBm;
        bmO = BitmapFactory.decodeResource(context.getResources(), R.drawable.circulo);
        bmX = BitmapFactory.decodeResource(context.getResources(), R.drawable.x);
        arrJugadas = new ArrayList<>();
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke1));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke2));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke3));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke4));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke5));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke6));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke7));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke8));
        animationJugada = AnimationUtils.loadAnimation(context, R.anim.anim_jugada);
        FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setAnimation(animationJugada);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.celda, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();

        holder.ivCeldaTablero.setImageBitmap(arrBm.get(adapterPosition));
        holder.ivCeldaTablero.setImageBitmap(arrBm.get(position));
        animationXyO = AnimationUtils.loadAnimation(context, R.anim.anim_o_x);
        holder.ivCeldaTablero.setAnimation(animationXyO);
        holder.ivCeldaTablero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrBm.get(adapterPosition) == null && !comprobarGanador()) { //si finaliza la partida no se permite pulsar más veces en el tablero
                    if(FragmentJuegoTresEnRaya.turnoO) {
                        arrBm.set(adapterPosition, bmO);
                        FragmentJuegoTresEnRaya.turnoO = false;
                        FragmentJuegoTresEnRaya.tvTurno.setText("Turno de X");
                    }else {
                        arrBm.set(adapterPosition, bmX);
                        FragmentJuegoTresEnRaya.turnoO = true;
                        FragmentJuegoTresEnRaya.tvTurno.setText("Turno de O");
                    }
                    holder.ivCeldaTablero.setAnimation(animationXyO);
                    if (comprobarGanador()) {
                        gana();
                    }
                    notifyItemChanged(adapterPosition);
                }
            }
        });
    }

    private void gana() {
       // FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.startAnimation(animationJugada);
    }

    private boolean comprobarGanador() {
        if (arrBm.get(0) == arrBm.get(3) && arrBm.get(3) == arrBm.get(6) && arrBm.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(2));
            return true;
        }else if(arrBm.get(1) == arrBm.get(4) && arrBm.get(4) == arrBm.get(7) && arrBm.get(1) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(3));
            return true;
        }else if(arrBm.get(2) == arrBm.get(5) && arrBm.get(5) == arrBm.get(8) && arrBm.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(1));
            return true;
        }else if(arrBm.get(2) == arrBm.get(4) && arrBm.get(4) == arrBm.get(6) && arrBm.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(0));
            return true;
        }else if(arrBm.get(0) == arrBm.get(4) && arrBm.get(4) == arrBm.get(8) && arrBm.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(1));
            return true;
        }else if(arrBm.get(0) == arrBm.get(1) && arrBm.get(1) == arrBm.get(2) && arrBm.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(5));
            return true;
        }else if(arrBm.get(3) == arrBm.get(4) && arrBm.get(4) == arrBm.get(5) && arrBm.get(3) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(6));
            return true;
        }else if(arrBm.get(6) == arrBm.get(7) && arrBm.get(7) == arrBm.get(8) && arrBm.get(6) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(7));
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return arrBm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivCeldaTablero;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCeldaTablero = itemView.findViewById(R.id.ivCeldaTablero);
        }
    }

    public List<Bitmap> getArrBm() {
        return arrBm;
    }

    public void setArrBm(List<Bitmap> arrBm) {
        this.arrBm = arrBm;
    }
}
