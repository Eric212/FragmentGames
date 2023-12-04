package com.ericsospedra.fragmentsgames.modelos.ahorcado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ericsospedra.fragmentsgames.R;

public class FragmentAhorcado extends Fragment implements IOnFinish{

    Ahorcado ahorcadoGame;
    private ImageView ivAhorcado;
    private TextView tvIntentos;
    private TextView tvPalabra;
    private EditText edLetra;
    private TextView tvLetrasIntroducidas;
    private Button bJugar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ahorcado_layout, container, false);
        //esto sirve para cargar el fragment del ahorcado en el container del main activity
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivAhorcado = view.findViewById(R.id.ivAhorcado);
        tvIntentos = view.findViewById(R.id.tvIntentos);
        tvPalabra = view.findViewById(R.id.tvPalabraSecreta);
        edLetra = view.findViewById(R.id.editTextText);
        tvLetrasIntroducidas = view.findViewById(R.id.tvLetrasIntroducidas);
        bJugar = view.findViewById(R.id.bJugar);
        ahorcadoGame = new Ahorcado(this);
        ahorcadoGame.onStart(view.getContext());
    }

    @Override
    public void onFinish() {
        ahorcadoGame.createGame();
        tvPalabra.setText(ahorcadoGame.getPalabraEspacios());
        bJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClicked(view);
            }
        });
    }

    private void onButtonClicked(View view) {
                if(!ahorcadoGame.getGameOver()){
                    if(ahorcadoGame.getIntentos() > 0){
                        String letra = edLetra.getText().toString();
                        String palabraGuiones = ahorcadoGame.comprobarLetra(letra);
                        tvPalabra.setText(ahorcadoGame.toEspacios(palabraGuiones));
                        boolean hasGanado = ahorcadoGame.getHasGanado();
                        if(hasGanado){
                            Toast.makeText(view.getContext(),  "¡Has ganado! Pulsar el boton 'Jugar' para empezar", Toast.LENGTH_SHORT).show();
                        }
                        edLetra.setText("");
                        if(!ahorcadoGame.getLetraEncontrada()){
                            ahorcadoGame.registrarFallo(letra);
                            tvIntentos.setText(String.valueOf(ahorcadoGame.getIntentos()));
                            tvLetrasIntroducidas.setText(ahorcadoGame.getCadenaLetras());
                            String fotoName = "hangman_" + ahorcadoGame.getIntentos();
                            /*int resID = getResources().getIdentifier(fotoName, "drawable", getPackageName());
                            ivAhorcado.setImageResource(resID);*/
                        }
                    }if(ahorcadoGame.getIntentos() == 0){
                        //Has perdido!
                        Toast.makeText(view.getContext(), "¡Has perdido! Pulsar el boton 'Jugar' para empezar", Toast.LENGTH_SHORT).show();
                        tvPalabra.setText(ahorcadoGame.getPalabraSecreta());
                        ivAhorcado.setImageResource(R.drawable.hangman_0);
                    }
                }else{
                    ahorcadoGame.createGame();
                    tvIntentos.setText(String.valueOf(ahorcadoGame.getIntentos()));
                    tvLetrasIntroducidas.setText("");
                    tvPalabra.setText(ahorcadoGame.getPalabraGuiones());
                    tvPalabrita.setText(ahorcadoGame.getPalabraSecreta());
                    ivAhorcado.setImageResource(R.drawable.hangman_6);
        }
    }
}
