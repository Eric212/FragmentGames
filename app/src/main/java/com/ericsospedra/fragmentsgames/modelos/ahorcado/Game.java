package com.ericsospedra.fragmentsgames.modelos.ahorcado;

import android.content.Context;

import com.ericsospedra.fragmentsgames.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    Random random = new Random();

    public String generarPalabra(Context context) {
        ArrayList<String> palabras = leerArchivo(context);
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





    public static ArrayList<String> leerArchivo(Context context) {
        ArrayList<String> palabras = new ArrayList<>();
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.words_es);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String palabra;
            while ((palabra = br.readLine()) != null) {
                palabras.add(palabra);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return palabras;
    }




}
