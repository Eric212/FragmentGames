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
import com.ericsospedra.fragmentsgames.modelos.tresenraya.TresEnRaya;
import com.ericsospedra.fragmentsgames.modelos.tresenraya.fragments.FragmentJuegoTresEnRaya;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorTableroTresEnRaya extends RecyclerView.Adapter<AdaptadorTableroTresEnRaya.ViewHolder>{
    private Context context; //Representa el contexto de la aplicación Android.
    private List<Bitmap> arrBmCeldas, arrJugadas; //lista que contiene objetos Bitmap que representan las imágenes de las celdas del tablero del juego. Creamos un arreglo de imágenes para guardar por un lado las celdas (que se pasan al adaptador desde la clase FragmentJuego, como las líneas (fotos con líneas en negro) que indicarán quién gana al finalizar la partida
    private Bitmap bmX, bmO, empate; //creamos dos objetos Bitmap para insertar las imágenes de las tiradas X e O, que son 2 fotos que están en drawable. Serán objetos Bitmap que representan las imágenes de las tiradas X e O, respectivamente.
    private Animation animationXyO, animationJugada, animationGanador; //Son objetos de la clase Animation que representan las animaciones que se aplicarán a las imágenes de las tiradas y las líneas. .Aquí guardamos un efecto creado en un archivo xml en la carpeta anim, un efecto para las líneas que aparecen y también para cuando pulsamos para jugar con X u O, para que cuando se interactúa con el juego, al pulsar en la pantalla, los elementos que aparecen tengan un efecto como de ir construyéndose poco a poco, desde una transparencia hasta opaco, es más amable la aparición de los elementos.
    private String simboloGanador = "O";
    private int numJugadores;
    //CONSTRUCTOR DEL ADAPTADOR, le pasamos por parámetro el contexto y el arreglo de elementos necesarios para crear el diseño
    //A parte tendrá otros elementos necesarios para la creación total del diseño, como son los definidos en las variables de esta clase.
    public AdaptadorTableroTresEnRaya(Context context, List<Bitmap> arrBmCeldas, int numJugadores) {
        this.context = context;
        this.arrBmCeldas = arrBmCeldas;
        this.numJugadores = numJugadores;
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
    Este método se llama cuando se necesita mostrar o actualizar un elemento (celda) en una posición específica de la cuadrícula.
    Aquí, holder hace referencia al ViewHolder asociado a esa posición, y se actualiza la imagen en ivCeldaTablero con
    la imagen correspondiente desde arrBmCeldas.
    */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition(); //obtiene el Bitmap (celda concreta de las 9) correspondiente a la posición actual del adaptador, es decir, devuelve la posición del ítem (la celda/mini cuadrícula) actual en la lista de datos del adaptador (en este caso el adaptador muestra las 9 celdas guardadas en arrBmCeldas, y al pulsar sobre una, nos devuelve la posición de esa celda pulsada). Así, se utiliza esa posición para obtener el bitmap (espacio para la imagen) correspondiente de arrBmCeldas y establecerlo como contenedor de la imagen que contendrá X u O
        holder.ivCeldaTablero.setImageBitmap(arrBmCeldas.get(adapterPosition));//holder.ivCeldaTablero es la ImageView contenida en la vista de la celda. Es decir, cada celda de las 9 del tablero tiene su propia ImageView. Esta línea de código, coloca la imagen almacenada en arrBmCeldas en la celda del tablero actual representada por holder.ivCeldaTablero.
        animationXyO = AnimationUtils.loadAnimation(context, R.anim.anim_o_x); //insertamos el diseño de la animación a la variable donde se guarda
        holder.ivCeldaTablero.setAnimation(animationXyO); //asignamos la animación para que aparezca al pulsar en el contenedor de las celdas al insertar las imágenes X y O

        /*
        Método onClick
        Cuando se pulsa sobre el elemento cuadrícula, en una posición concreta,
        que es la vista creada por el ViewHolder mostrando y apuntando a los elementos del RecyclerView, en este caso la cuadrícula con celdas.

        Cuando pulsemos en una de las celdas de la cuadrícula (que es pulsar en un lugar concreto de
        la vista del diseño creado por el ViewHolder)
        La traza del método onClick sería:  cada vez que el usuario hace clic en una celda,
        se realiza una jugada, se verifica si hay un ganador, se actualiza la interfaz de usuario (insertando una nueva imagen)
        y se notifica al adaptador que la celda en cuestión ha cambiado para que se vuelva a dibujar.
         */
        holder.ivCeldaTablero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si la celda está vacia y aún no hay todavía un ganador (comprobarGanador no es true) se podrá jugar
                if(arrBmCeldas.get(adapterPosition) == null && !comprobarGanador()) { //si no se cumple alguna de estas condiciones, es decir, si comprobarGanador() es true (ya hay ganador) y/o ninguna posición es null, porque se han completado las jugadas, ya no se permiten más jugadas y finaliza el juego
                    if(FragmentJuegoTresEnRaya.turnoO) { //si el turno es del jugadorO (estará en true)
                        arrBmCeldas.set(adapterPosition, bmO); //donde pulse se insertará la imagen de la O
                        FragmentJuegoTresEnRaya.turnoO = false; //pondremos a false su turno para que le toque al jugador X
                        FragmentJuegoTresEnRaya.tvTurno.setText("Turno de X"); //insertamos en el textView el turno del otro jugador
                    }else { //si el turnoO es false, es que le toca al turnoX
                        arrBmCeldas.set(adapterPosition, bmX); //en la celda pulsada se insertará la foto con la X
                        FragmentJuegoTresEnRaya.turnoO = true;
                        FragmentJuegoTresEnRaya.tvTurno.setText("Turno de O");
                    }
                    holder.ivCeldaTablero.setAnimation(animationXyO); //cada tirada se le asignará animación a la imagen X y O que se inserte
                    if (comprobarGanador()) {  //comprobamos si tras completar la jugada existe un ganador, si es true es que sí y entra en el if
                        gana(); //si hay ganador se ejecutará este método, dándole animación a la línea que señala la jugada ganadora
                    }
                    notifyItemChanged(adapterPosition); //se notifica al adaptador que la celda en cuestión ha cambiado para que se vuelva a dibujar, es decir, actualizar visualmente la celda en la pantalla con los nuevos datos proporcionados por el adaptador.
                   /*
                   Que se vuelva a dibujar significa que, cuando un usuario realiza una jugada,
                   el contenido del RecyclerView se actualiza para reflejar el cambio en el tablero de juego.
                   Las celdas en el tablero pueden cambiar según las interacciones del usuario.
                   Cada celda puede contener diferentes imágenes (representando X, O o ninguna) y
                   estas imágenes pueden cambiar durante el juego.
                   Cuando se notifica al adaptador que una celda en particular ha cambiado mediante
                   notifyItemChanged(adapterPosition), significa que se le está diciendo al RecyclerView que
                   vuelva a dibujar esa celda específica, y actualice la vista de la celda en la posición proporcionada.
                   El adaptador invocará los métodos necesarios, como onBindViewHolder, para actualizar la vista
                   con la información más reciente de la celda.
                    */
                }
            }
        });
        if(!comprobarGanador()) {
            comprobarEmpate();
        }
    }

    public void juegoDosJugadores() {

    }

    private void juegoUnJugadorConIA() {
        /*
        Lógica para la inteligencia artificial
        Aquí debemos determinar la posición en la que la IA realizará su jugada
        Podemos usar algún algoritmo para hacerlo
         */
     //   int posicionIA = determinarPosicionIA();

        // Realizar la jugada de la IA
       // arrBmCeldas.set(posicionAI, bmX);

        // Notificar al adaptador que la celda en la que la IA jugó ha cambiado
        //notifyItemChanged(posicionAI);

        // Verificar si la IA ganó después de su jugada
        if (comprobarGanador()) {
            gana();
        }
    }

    private void comprobarEmpate() {
        int contador = 0;
        for (int i = 0; i < arrBmCeldas.size(); i++) {
            if(arrBmCeldas.get(i) != null) {
                contador++;
            }
            if(contador==9) {
                FragmentJuegoTresEnRaya.rlGanador.setVisibility(View.VISIBLE);
                FragmentJuegoTresEnRaya.rlGanador.setAnimation(animationJugada);
                FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(empate);
                FragmentJuegoTresEnRaya.tvGanador.setText("¡Empate!");
            }
        }
    }

    /*
    Método gana()
    Este método se llama en caso de que la partida haya finalizado con un ganador,
    para que la línea que señala la partida ganadora
    se inserte con la animación concreta para que visualmente se vea mejor.
     */
    private void gana() {
       FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.startAnimation(animationJugada);
       FragmentJuegoTresEnRaya.rlGanador.setAnimation(animationGanador);
       FragmentJuegoTresEnRaya.rlGanador.setVisibility(View.VISIBLE);
       FragmentJuegoTresEnRaya.rlGanador.startAnimation(animationGanador);
       if (simboloGanador.equals("O")) {
           FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(bmO);
           TresEnRaya.puntuacionO++;
           FragmentJuegoTresEnRaya.tvPuntuacionO.setText("O: " + TresEnRaya.puntuacionO);
       }else {
           FragmentJuegoTresEnRaya.ivSimboloGanador.setImageBitmap(bmX);
           TresEnRaya.puntuacionX++;
           FragmentJuegoTresEnRaya.tvPuntuacionX.setText("X: " + TresEnRaya.puntuacionX);
       }
       FragmentJuegoTresEnRaya.tvGanador.setText("¡Ganaste :)");
    }

    /*
     DISPOSICIÓN DE LAS CELDAS DEL TABLERO
        0 | 1 | 2
        ---------
        3 | 4 | 5
        ---------
        6 | 7 | 8
     */

    /*
     Método comprobarGanador()

     Este método comprobarGanador() se encarga de verificar si hay un ganador en el juego de Tres en Raya,
     revisa todas las posibles combinaciones de celdas que podrían indicar una victoria para X o O.
     Por ejemplo, la primera condición verifica la primera columna para ver si las celdas la imagen insertada es igual,
     y no son nulas. De ser así, establece una imagen en ivJugadaTresEnRaya usando arrJugadas.get(2), que es la línea que
     cruza de arriba a abajo el tablero indicando la victoria del jugador X u O. Así con todas la posibles combinaciones
     ganadoras de jugadas, que en total son 8. Si hubiera una partida ganadora, comprobarGanador() devolvería true,
     como se existe ganador en la partida y por tanto se termina dicha partida, determina el fin del juego si es true.
     De no ser así, devolvería false, indicando que la partida está por terminar o no hubo ganador.
     */
    private boolean comprobarGanador() {
        if (arrBmCeldas.get(0) == arrBmCeldas.get(3) && arrBmCeldas.get(3) == arrBmCeldas.get(6) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(2));
            comprobarSimboloGanador(0);
            return true;
        }else if(arrBmCeldas.get(1) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(7) && arrBmCeldas.get(1) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(3));
            comprobarSimboloGanador(1);
            return true;
        }else if(arrBmCeldas.get(2) == arrBmCeldas.get(5) && arrBmCeldas.get(5) == arrBmCeldas.get(8) && arrBmCeldas.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(4));
            comprobarSimboloGanador(2);
            return true;
        }else if(arrBmCeldas.get(0) == arrBmCeldas.get(1) && arrBmCeldas.get(1) == arrBmCeldas.get(2) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(5));
            comprobarSimboloGanador(0);
            return true;
        }else if(arrBmCeldas.get(3) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(5) && arrBmCeldas.get(3) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(6));
            comprobarSimboloGanador(3);
            return true;
        }else if(arrBmCeldas.get(6) == arrBmCeldas.get(7) && arrBmCeldas.get(7) == arrBmCeldas.get(8) && arrBmCeldas.get(6) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(7));
            comprobarSimboloGanador(6);
            return true;
        }else if(arrBmCeldas.get(0) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(8) && arrBmCeldas.get(0) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(1));
            comprobarSimboloGanador(0);
            return true;
        }else if(arrBmCeldas.get(2) == arrBmCeldas.get(4) && arrBmCeldas.get(4) == arrBmCeldas.get(6) && arrBmCeldas.get(2) != null) {
            FragmentJuegoTresEnRaya.ivJugadaTresEnRaya.setImageBitmap(arrJugadas.get(0));
            comprobarSimboloGanador(2);
            return true;
        }
        return false;
    }

    private void comprobarSimboloGanador(int i) { //le pasamos la primera celda de la jugada ganadora para saber qué imagen tiene si O o X
        if (arrBmCeldas.get(i) == bmO) { //si la imagen que hay en la celda es igual a la imagen que representa el jugador O
            simboloGanador = "O";
        }else {
            simboloGanador = "X";
        }
    }

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
        private ImageView ivCeldaTablero; //en el xml de celda tenemos esta imagen, que es donde se insertará el diseño de la cuadrícula que tenemos hecho en drawable, que es un cuadrado en blanco, que se insertará en la imagen, y ésta adquirirá esta forma, es decir, celda.xml será cada uno de los mini cuadrados de la cuadrícula de 3x3
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCeldaTablero = itemView.findViewById(R.id.ivCeldaTablero);
        }
    }

    public List<Bitmap> getArrBmCeldas() {
        return arrBmCeldas;
    }

    public void setArrBmCeldas(List<Bitmap> arrBmCeldas) {
        this.arrBmCeldas = arrBmCeldas;
    }
}
