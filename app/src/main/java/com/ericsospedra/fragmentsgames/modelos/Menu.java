package com.ericsospedra.fragmentsgames.modelos;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.Adaptadores.AdaptadorMenu;
import com.ericsospedra.fragmentsgames.Interfaces.IonClickListenner;
import com.ericsospedra.fragmentsgames.R;

import java.util.ArrayList;

/**
 * @author eric
 * Fragmento encargado de mostrar el menu para seleccionar los juegos
 * Cuidado con Game es una clase Abstracta no se puede instanciar
 * @throws InstantiationException
 */
public class Menu extends Fragment {
    public interface IonAttach{
        ArrayList<Game> getJuegos();
    }
    private ArrayList<Game> juegos;
    private AdaptadorMenu adaptador;
    private IonClickListenner listenner;

    /**
     * @author eric
     * Seteamos el layout que necesita nuestro Fragment
     * para poder acceder a las Views correspondientes
     */
    public Menu() {
        super(R.layout.fragment_menu);
    }

    /**
     * @author eric
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * Seteamos nuestro adaptador y mediante la View conseguimos el RecyclerView
     * y le seteamos el adaptador, preparamos el LayoutManager y añadimos un divisor
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adaptador = new AdaptadorMenu(juegos,listenner);
        RecyclerView rvMenu = view.findViewById(R.id.rvMenu);
        rvMenu.setAdapter(adaptador);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvMenu.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    /**
     * @author eric
     * @param context
     * Mediante el metodo onAttach preparamos los datos
     * los cuales conseguimos mediante nuestra interfaz(IonAttach)
     * ionAttach se setea con el context ya que el casteo que hacemos al mismo
     * es la activity que esta implementando la interfaz
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IonAttach ionAttach = (IonAttach) context;
        juegos = ionAttach.getJuegos();
        listenner = (IonClickListenner) context;
    }
}
