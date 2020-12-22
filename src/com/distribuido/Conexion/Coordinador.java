package com.distribuido.Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Coordinador extends ServerSocket {
    private Coordinador este;
    private ArrayList<Conexion> Conexiones;
    private Hilo_Esperador HE ;

    public Coordinador() throws IOException {
        super(Conexion.PUERTO);
        este = this;
        Conexiones = new ArrayList<>();
    }

    public void Esperar()
    {
        HE = new Hilo_Esperador();
        HE.start();
    }
    public void Empezar()
    {
        HE.Apagar();
        //Iniciar hilo comunicador si hay mas de 1 conexion, sino no hacer nada
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
                    Conexiones.add(new Conexion(este.accept()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
