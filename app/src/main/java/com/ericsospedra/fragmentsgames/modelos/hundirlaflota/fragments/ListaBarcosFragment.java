package com.ericsospedra.fragmentsgames.modelos.hundirlaflota.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.ericsospedra.fragmentsgames.R;

public class ListaBarcosFragment extends Fragment {
    public interface IonAttach{
        int getItemPosition(int position);
        View getItemTouched(View item);
    }
    public ListaBarcosFragment() {
        super(R.layout.lista_barcos);
    }
}
