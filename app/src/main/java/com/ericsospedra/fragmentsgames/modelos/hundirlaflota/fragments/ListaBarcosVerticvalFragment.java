package com.ericsospedra.fragmentsgames.modelos.hundirlaflota.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.ericsospedra.fragmentsgames.R;

public class ListaBarcosVerticvalFragment extends Fragment implements ListaBarcosFragment.IonAttach {
    public interface IonAttach{
        int getItemPosition(int position);
        View getItemTouched(View item);
    }
    public ListaBarcosVerticvalFragment() {
        super(R.layout.lista_barcos_vertical);
    }

    @Override
    public int getItemPosition(int position) {
        return 0;
    }

    @Override
    public View getItemTouched(View item) {
        return null;
    }
}
