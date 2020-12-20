package com.distribuido.Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CentralDistribuidor {
    private final boolean Trabajador;
    private ArrayList<Socket> Conexiones;

    private ServerSocket SS;
    private Socket mSocket;



    public CentralDistribuidor(boolean trabajador) throws IOException {
        SS = new ServerSocket(Conexion.PUERTO);
        Trabajador = trabajador;
        if (Trabajador)
            mSocket = new Socket();
        Esperar();
    }


    private void Esperar()
    {
        Scanner SC = new Scanner(System.in);
        System.out.println("Hola, Soy un " + (Trabajador?"Central":"Distribuidor"));
        System.out.println("Estoy esperando a mis " + (Trabajador?"Distribuidores":"Obresos") + "para trabajar");



        SC.next();
        System.out.println("... Presionar una tecla para empezar a trabajar ya");


    }

    private class Hilo_Esperador extends Thread
    {
        private boolean Encendido = true;

        @Override
        public void run() {
            while(Encendido)
            {
                try {
                    Conexiones.add(SS.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
