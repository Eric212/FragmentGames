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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentJuegoTresEnRaya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdaptadorTableroTresEnRaya extends RecyclerView.Adapter<AdaptadorTableroTresEnRaya.ViewHolder>{

    private Context context; //Representa el contexto de la aplicación Android.
    public List<Bitmap> arrBmCeldas;
    private List<Bitmap> arrJugadas; //lista que contiene objetos Bitmap que representan las imágenes de las celdas del tablero del juego. Creamos un arreglo de imágenes para guardar por un lado las celdas (que se pasan al adaptador desde la clase FragmentJuego, como las líneas (fotos con líneas en negro) que indicarán quién gana al finalizar la partida
    private Bitmap bmX, bmO, empate; //creamos dos objetos Bitmap para insertar las imágenes de las tiradas X e O, que son 2 fotos que están en drawable. Serán objetos Bitmap que representan las imágenes de las tiradas X e O, respectivamente.
    private Animation animationXyO, animationJugada, animationGanador; //Son objetos de la clase Animation que representan las animaciones que se aplicarán a las imágenes de las tiradas y las líneas. .Aquí guardamos un efecto creado en un archivo xml en la carpeta anim, un efecto para las líneas que aparecen y también para cuando pulsamos para jugar con X u O, para que cuando se interactúa con el juego, al pulsar en la pantalla, los elementos que aparecen tengan un efecto como de ir construyéndose poco a poco, desde una transparencia hasta opaco, es más amable la aparición de los elementos.
    private int numJugadores;
    boolean isTurnoO;
    private String simboloGanador = "O";
    int adapterPosition;



    //CONSTRUCTOR DEL ADAPTADOR, le pasamos por parámetro el contexto y el arreglo de elementos necesarios para crear el diseño
    //A parte tendrá otros elementos necesarios para la creación total del diseño, como son los definidos en las variables de esta clase.
    public AdaptadorTableroTresEnRaya(Context context, List<Bitmap> arrBmCeldas, int numJugadores, boolean isTurnoO) {
        this.context = context;
        this.arrBmCeldas = arrBmCeldas;
        this.numJugadores = numJugadores;
        this.isTurnoO = isTurnoO;
        bmO = BitmapFactory.decodeResource(context.getResources(), R.drawable.circulo); //Guardamos en el BitmapO la imagen del círculo
        bmX = BitmapFactory.decodeResource(context.getResources(), R.drawable.x); //Guardamos en el objeto BitmapX la imagen de la X
        empate = BitmapFactory.decodeResource(context.getResources(), R.drawable.empate);
        arrJugadas = new ArrayList<>(); //aquí guardaremos todas las fotos de las líneas, para según qué jugada insertar una u otra
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke1));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke2));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke3));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke4));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke5));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke6));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke7));
        arrJugadas.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.stroke8));
        animationJugada = AnimationUtils.loadAnimation(context, R.anim.anim_jugada); //esto será la animación para las líneas, que aparecen al finalizar la partida indicando el usuario ganador
        animationGanador = AnimationUtils.loadAnimation(context, R.anim.anim_ganador);
        FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setAnimation(animationJugada); //estas imágenes de las líneas se insertan en el contenedor (una imagen insertada en el fragmento del juego, con las mismas dimensiones que el recyclerview), que hemos llamado ivJugadaTresEnRaya
        // Llamamos a jugadaIA al comienzo del juego si el turno inicial es de X y se eligió 1 jugador (le tocaría a la CPU)
        if (!isTurnoO && numJugadores == 1) {
            Toast.makeText(context.getApplicationContext(), "entró en adaptador", Toast.LENGTH_SHORT).show();
            jugadaIA();
            notifyDataSetChanged();
        }
    }

    /*
    Método onCreateViewHolder
    En este método onCreateViewHolder del adaptador, se crea un nuevo ViewHolder para cada elemento de la lista o cuadrícula.
    Luego, en el método onBindViewHolder, se utiliza ese ViewHolder para actualizar las vistas individuales con los datos
    correspondientes al elemento en esa posición.
    Así, se infla el diseño de la celda.xml (R.layout.celda) para cada elemento de la cuadrícula
    (tenemos 9 elementos en total diseñados en FragmentJuegoTresEnRaya).
    Esto creará una instancia de ViewHolder para cada elemento/celda que contendrá la referencia a la ImageView (ivCeldaTablero)
    con ese diseño. Así, para cada celda de la cuadrícula total, se le asigna la imageView de celda,
    que a su vez tiene dentro el diseño de un cuadrado en blanco (las celdas) insertado en la propiedad background.
    Resumiento, cada celda de las 9 que hay, se infla con el diseño celda.xml, que es una imageview vacía en forma de cuadrado
    que servirá de espacio para insertar el bitmapX o el bitmapO.
    */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.celda, parent, false)); //inflamos el diseño de la Celda, que es una imagen a la cual le insertamos el diseño cuadrado de la celda/cuadrícula, que es otro diseño que tenemos en drawable. Este diseño se inserta en el archivo xml de la celda con la propiedad android:background, de manera que esta imagen adquiere la forma del fondo que hemos creado, siendo éste un cuadrado. R.layout.celda se refiere al diseño de cada elemento de la cuadrícula. Cuando se infla este diseño, se crea un nuevo ViewHolder para representar ese elemento.
    }

    /*
    Método onBindViewHolder
    En este métodos se utiliza el ViewHolder para actualizar el contenido de las vistas dentro de ese elemento de la cuadrícula.
    A cada celda de la cuadrícula se le asignará la imagen correspondiente, según el turno (x o Y) y según la posición
    se asignará en un lugar determinado de la cuadrícula.
    */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        adapterPosition = holder.getAdapterPosition(); //obtiene el Bitmap (celda concreta de las 9) correspondiente a la posición actual del adaptador, es decir, devuelve la posición del ítem (la celda/mini cuadrícula) actual en la lista de datos del adaptador (en este caso el adaptador muestra las 9 celdas guardadas en arrBmCeldas, y al pulsar sobre una, nos devuelve la posición de esa celda pulsada). Así, se utiliza esa posición para obtener el bitmap (espacio para la imagen) correspondiente de arrBmCeldas y establecerlo como contenedor de la imagen que contendrá X u O
        holder.ivCeldaTablero.setImageBitmap(arrBmCeldas.get(adapterPosition));//holder.ivCeldaTablero es la ImageView contenida en la vista de la celda. Es decir, cada celda de las 9 del tablero tiene su propia ImageView. Esta línea de código, coloca la imagen almacenada en arrBmCeldas en la celda del tablero actual representada por holder.ivCeldaTablero.
        animationXyO = AnimationUtils.loadAnimation(context, R.anim.anim_o_x); //insertamos el diseño de la animación a la variable donde se guarda
        holder.ivCeldaTablero.setAnimation(animationXyO); //asignamos la animación para que aparezca al pulsar en el contenedor de las celdas al insertar las imágenes X y O. Cada tirada se le asignará animación a la imagen X y O que se inserte
        /*
        Método onClick
        Cuando se pulsa sobre el elemento cuadrícula, en una posición concreta,
        se insertará la imagen de la jugada X u O
         */
        holder.ivCeldaTablero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    adapterPosition = holder.getAdapterPosition();
                    jugar();
                    notifyItemChanged(adapterPosition); //se notifica al adaptador que la celda en cuestión ha cambiado para que se vuelva a dibujar, es decir, actualizar visualmente la celda en la pantalla con los nuevos datos proporcionados por el adaptador.
            }
        });

    }

    public void jugar() {
        if (arrBmCeldas.get(adapterPosition) == null && !comprobarGanador()) { //O
            if (isTurnoO) {
                arrBmCeldas.set(adapterPosition, bmO); //en la posición de la celda = adapterPosition (donde pulso) pon bmO sustituyendo la imagen en blanco que es null por bmO
                isTurnoO = false;
                FragmentJuegoTresEnRaya.tvTurno.setText("Turno de X");
                notifyItemChanged(adapterPosition);

                // Si es 1 jugador, después de que el jugador hace su jugada, realiza la jugada de la IA
                if (numJugadores == 1) {
                    jugadaIA();
                }
            } else { //X
                if (numJugadores == 1) {
                    jugadaIA();
                }else {
                    arrBmCeldas.set(adapterPosition, bmX);
                    isTurnoO = true;
                    FragmentJuegoTresEnRaya.tvTurno.setText("Turno de O");
                }
            }

            if (!comprobarGanador()) {
                comprobarEmpate();
            } else {
                gana();
            }
        }
    }

    private void jugadaIA() {
        Toast.makeText(context.getApplicationContext(), "Entró en jugadaIA", Toast.LENGTH_SHORT).show();
        // Lógica de la IA para decidir dónde colocar bmX
        int celdaElegidaIA = obtenerCeldaTiradaIA();
        Toast.makeText(context.getApplicationContext(), "celdaTiradaIA " + celdaElegidaIA, Toast.LENGTH_SHORT).show();
        if (celdaElegidaIA == -1) { //fin partida porque no hay celdas libres
        }else {
            // Realizar la jugada de la IA
            arrBmCeldas.set(celdaElegidaIA, bmX);
            notifyItemChanged(celdaElegidaIA);
            // Cambia el turno
            isTurnoO = true;
            FragmentJuegoTresEnRaya.tvTurno.setText("Turno de O");
            notifyDataSetChanged(); //actualizamos interfaz de usuario
        }
    }

    //este método buscará entre las 9 celdas aquellas quqe estén vacías, y de esas elegirá 1 al azar donde poner su tirada
    private int obtenerCeldaTiradaIA() {
        Toast.makeText(context.getApplicationContext(), "entró en obtenerCeldaIA", Toast.LENGTH_SHORT).show();
        // Buscamos una celda vacía de manera aleatoria:
        List<Integer> celdasVacias = obtenerCeldasVacias();
        if (!celdasVacias.isEmpty()) {
            Random random = new Random();
            return celdasVacias.get(random.nextInt(celdasVacias.size())); //de las posiciones vacías devolvemos una al azar
        }
        // Si no hay celdas vacías, devolvemos -1 y significará que la partida finaliza
        return -1;
    }

    //con este método obtendremos el total de celdas vacías donde podrá tirar la IA
    private List<Integer> obtenerCeldasVacias() {
        List<Integer> celdasVacias = new ArrayList<>();
        for (int i = 0; i < arrBmCeldas.size(); i++) {
            if (arrBmCeldas.get(i) == null) { //si en las 9 celdas alguna no tiene foto X u O insertada se añadirá a celdasVacias
                celdasVacias.add(i);
            }
        }
        return celdasVacias;
    }

    private boolean comprobarGanador() {
        if (arrBmCeldas.get(0) == arrBmCeldas.get(3) && arrBmCeldas.get(3) == arrBmCeldas.get(6) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(2));
            comprobarSimboloGanador(0);
            return true;
        } else if (arrBmCeldas.get(1) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(7) && arrBmCeldas.get(1) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(3));
            comprobarSimboloGanador(1);
            return true;
        } else if (arrBmCeldas.get(2) == arrBmCeldas.get(5) && arrBmCeldas.get(5) == arrBmCeldas.get(8) && arrBmCeldas.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(4));
            comprobarSimboloGanador(2);
            return true;
        } else if (arrBmCeldas.get(0) == arrBmCeldas.get(1) && arrBmCeldas.get(1) == arrBmCeldas.get(2) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(5));
            comprobarSimboloGanador(0);
            return true;
        } else if (arrBmCeldas.get(3) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(5) && arrBmCeldas.get(3) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(6));
            comprobarSimboloGanador(3);
            return true;
        } else if (arrBmCeldas.get(6) == arrBmCeldas.get(7) && arrBmCeldas.get(7) == arrBmCeldas.get(8) && arrBmCeldas.get(6) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(7));
            comprobarSimboloGanador(6);
            return true;
        } else if (arrBmCeldas.get(0) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(8) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(1));
            comprobarSimboloGanador(0);
            return true;
        } else if (arrBmCeldas.get(2) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(6) && arrBmCeldas.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(0));
            comprobarSimboloGanador(2);
            return true;
        }
        return false;
    }

    //si se produce un empate es porque se han realizado todas las tiradas posibles (9) y no hay ganador
    private void comprobarEmpate() {
        int contador = 0;
        for (int i = 0; i < arrBmCeldas.size(); i++) {
            if (arrBmCeldas.get(i) != null) {
                contador++;
            }
            if (contador == 9) {
                FragmentJuegoTresEnRaya.rlGanador.setVisibility(View.VISIBLE);
                FragmentJuegoTresEnRaya.rlGanador.setAnimation(obtenerAnimacionJugada());
                FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(empate);
                FragmentJuegoTresEnRaya.tvGanador.setText("¡Empate!");
            }
        }
    }

    private void gana() {
        FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.startAnimation(obtenerAnimacionJugada());
        FragmentJuegoTresEnRaya.rlGanador.setAnimation(obtenerAnimacionGanador());
        FragmentJuegoTresEnRaya.rlGanador.setVisibility(View.VISIBLE);
        FragmentJuegoTresEnRaya.rlGanador.startAnimation(obtenerAnimacionGanador());
        if (simboloGanador.equals("O")) {
            FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(bmO);
            TresEnRaya.puntuacionO++;
            FragmentJuegoTresEnRaya.tvPuntuacionO.setText("O: " + TresEnRaya.puntuacionO);
        } else {
            FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(bmX);
            TresEnRaya.puntuacionX++;
            FragmentJuegoTresEnRaya.tvPuntuacionX.setText("X: " + TresEnRaya.puntuacionX);
        }
        FragmentJuegoTresEnRaya.tvGanador.setText("¡Ganaste :)");
    }

    private void comprobarSimboloGanador(int i) { //le pasamos la primera celda de la jugada ganadora para saber qué imagen tiene si O o X
        if (arrBmCeldas.get(i) == bmO) { //si la imagen que hay en la celda es igual a la imagen que representa el jugador O
            simboloGanador = "O";
        } else {
            simboloGanador = "X";
        }
    }


    public Animation obtenerAnimacionJugada() {
        return animationJugada;
    }


    public Animation obtenerAnimacionXyO() {
        return animationXyO;
    }

    public Animation obtenerAnimacionGanador() {
        return animationGanador;
    }

    /*
     DISPOSICIÓN DE LAS CELDAS DEL TABLERO
        0 | 1 | 2
        ---------
        3 | 4 | 5
        ---------
        6 | 7 | 8
     */

    @Override
    public int getItemCount() {
        return arrBmCeldas.size();
    }

    /*
    Método ViewHolder
    El ViewHolder se encarga de mantener las referencias a las vistas individuales dentro de un elemento de la lista o cuadrícula
    del recyclerview.
    Cada instancia de ViewHolder (que contiene la referencia a ivCeldaTablero) está asociada a un elemento de la cuadrícula
    (9 en total), y el método onBindViewHolder se encargará de actualizar el contenido.
    Se creará un ViewHolder para cada elemento de la cuadrícula en el método onCreateViewHolder del adaptador.

    */
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivCeldaTablero; //en el xml de celda tenemos esta imagen, que es donde se insertará el diseño del cuadrado en blanco que tenemos hecho en drawable, que es un cuadrado en blanco, que se insertará en la imagen, y ésta adquirirá esta forma, es decir, celda.xml será cada uno de los mini cuadrados de la cuadrícula de 3x3
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCeldaTablero = itemView.findViewById(R.id.ivCeldaTablero);
        }
    }

    public void setArrBmCeldas(List<Bitmap> arrBmCeldas) {
        this.arrBmCeldas = arrBmCeldas;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }


}
