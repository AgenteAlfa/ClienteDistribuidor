package com.distribuido.Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CentralDistribuidor {
    private final boolean Central;
    private ArrayList<Socket> Conexiones;

    private final ServerSocket SS;
    private Socket mSocket;



    public CentralDistribuidor() throws IOException {
        Central = false;
        SS = new ServerSocket(Conexion.PUERTO);
        Conexiones = new ArrayList<>();
        mSocket = new Socket(Conexion.IP,Conexion.PUERTO);
        Esperar();
    }

    public CentralDistribuidor(boolean central) throws IOException {
        SS = new ServerSocket(Conexion.PUERTO);
        Conexiones = new ArrayList<>();
        Central = central;
        if (!Central)
            mSocket = new Socket(Conexion.IP,Conexion.PUERTO);
        Esperar();
    }


    private void Esperar()
    {
        Scanner SC = new Scanner(System.in);
        System.out.println("Hola, Soy un " + (Central?"Central":"Distribuidor"));
        System.out.println("Estoy esperando a mis " + (Central?" Distribuidores":" Obreros") + " para trabajar");

        String cadena = "no data";
        Hilo_Esperador HE = new Hilo_Esperador();
        HE.start();
        System.out.println("... Preciona una tecla para empezar");
        SC.nextLine();
        HE.Apagar();
        System.out.println("Tengo " + Conexiones.size() + (Central?" Distribuidores":" Obreros"));

        if (Central)
        {
            System.out.println("COMO SOY CENTRAL EMPIEZO A ENVIAR DATOS A MIS DISTRIBUIDORES");
            System.out.println("Que mensaje envio ?...");

            cadena = SC.nextLine();
            EnviarTodos(cadena);
        }
         else
        {
            System.out.println("COMO SOY DISTRIBUIDOR ESPERO A QUE ENVIEN DATOS ");
            try {
                while (mSocket.getInputStream().available() > 0)
                {
                    System.out.println(mSocket.getInputStream().read());
                }
            //cadena = new String();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Se " + (Central?"Envio":"Recibio") + " " + cadena);

    }


    private void EnviarTodos(String str)
    {
        for (Socket S: Conexiones) {
            try {
                S.getOutputStream().write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class Hilo_Esperador extends Thread
    {
        private void Apagar(){Encendido = false; interrupt();}

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
