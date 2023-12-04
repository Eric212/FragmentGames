package com.ericsospedra.fragmentsgames.modelos.ahorcado;


import android.content.Context;

import com.ericsospedra.fragmentsgames.R;
import com.ericsospedra.fragmentsgames.modelos.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author eric
 * El constructor siempre necesitara de dos parametros
 * ya que extiende de Game
 */
//
public class Ahorcado extends Game {
    /**
     * @param nombre
     * @param icono
     */
    public Ahorcado(String nombre, String icono) {
        super(nombre,icono);
    }

    Random random = new Random();
    ArrayList<String> palabras;
    private String palabraSecreta;
    private String palabraGuiones;
    private String palabraEspacios;
    private int intentos;
    private StringBuilder cadenaLetras = new StringBuilder();
    private IOnFinish listener;
    private boolean gameOver = false;
    private boolean letraEncontrada;
    private boolean hasGanado = false;

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public String getPalabraGuiones() {
        return palabraGuiones;
    }

    public String getPalabraEspacios() {
        return palabraEspacios;
    }

    public Ahorcado(IOnFinish listener){
        this.listener = listener;
    }

    public void onStart(Context context){
        leerArchivo(context);
    }

    public void createGame(){
        this.palabraSecreta = generarPalabra();
        this.palabraGuiones = toGuiones(palabraSecreta);
        this.palabraEspacios = toEspacios(palabraGuiones);
        this.intentos = 6;
        this.gameOver = false;
        this.hasGanado = false;
        this.letraEncontrada = false;
        this.cadenaLetras = new StringBuilder();
    }

    public String generarPalabra() {
        int num = random.nextInt(palabras.size());
        String palabra = palabras.get(num);
        return palabra;
    }
    public String toGuiones(String palabra){
        StringBuilder sb = new StringBuilder();
        int nLetras = palabra.length();
        char []palabraGuiones = new char[nLetras];
        int i = 0;
        while (i< palabraGuiones.length){
            sb.append("_");
            i++;
        }
        return sb.toString();
    }
    public String toEspacios(String palabra){
        String newPalabra = palabra.replace("", " ").trim();
        return newPalabra;
    }

    public boolean esLetraValida(String letra) {
        return letra.length() == 1 && Character.isLetter(letra.charAt(0));
    }

    public String comprobarLetra(String letra) {
        char letraP = normalizarCadena(letra).charAt(0);
        letraEncontrada = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            char letraActual = normalizarCadena(String.valueOf(palabraSecreta.charAt(i))).charAt(0);

            if (letraActual == letraP) {
                StringBuilder newPalabraGuiones = new StringBuilder(palabraGuiones);
                newPalabraGuiones.setCharAt(i, palabraSecreta.charAt(i));
                palabraGuiones = newPalabraGuiones.toString();
                letraEncontrada = true;
            }
        }

        comprobarAcierto(palabraGuiones);
        return palabraGuiones;
    }

    private String normalizarCadena(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
    }

    public void comprobarAcierto(String palabraGuiones){
        int indice = palabraGuiones.indexOf('_');
        if(indice == -1){
            gameOver = true;
            hasGanado = true;
        }
    }

    public void registrarFallo(String letra){
        intentos--;
        cadenaLetras.append(letra).append(", ");
        if(intentos == 0){
            gameOver = true;
            hasGanado = false;
        }

    }

    public boolean getHasGanado(){
        return hasGanado;
    }

    public boolean getLetraEncontrada(){
        return letraEncontrada;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getIntentos() {
        return intentos;
    }

    public StringBuilder getCadenaLetras() {
        return cadenaLetras;
    }

    public void leerArchivo(Context context) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                palabras = new ArrayList<>();
                try (InputStream inputStream = context.getResources().openRawResource(R.raw.words_es);
                     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

                    String palabra;
                    while ((palabra = br.readLine()) != null) {
                        palabras.add(palabra);
                    }
                    listener.onFinish();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
    }
}

