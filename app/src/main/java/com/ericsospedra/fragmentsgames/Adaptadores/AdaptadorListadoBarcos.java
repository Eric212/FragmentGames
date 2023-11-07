package com.ericsospedra.fragmentsgames.Adaptadores;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorListadoBarcos extends RecyclerView.Adapter<AdaptadorListadoBarcos.ListadoBarcosViewHolder> {
    @NonNull
    @Override
    public AdaptadorListadoBarcos.ListadoBarcosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListadoBarcos.ListadoBarcosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListadoBarcosViewHolder extends RecyclerView.ViewHolder {
        public ListadoBarcosViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
