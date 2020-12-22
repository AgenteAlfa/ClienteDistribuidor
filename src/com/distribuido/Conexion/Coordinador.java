package com.distribuido.Conexion;

import com.distribuido.Cadena.Cadena;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Coordinador extends ServerSocket {
    private Coordinador este;
    private ArrayList<Conexion> Conexiones;
    private Hilo_Esperador HE ;
    private Hilo_Coordinador HC;

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
    public void Empezar(String C,String I)
    {
        HE.Apagar();
        //Iniciar hilo comunicador si hay mas de 1 conexion, sino no hacer nada
        if (Conexiones.size() > 0)
        {
            HC = new Hilo_Coordinador(C,I);
            HC.start();
        }else
            System.out.println("No existen suficientes trabajadores para integrar");

    }


    private class Hilo_Coordinador extends Thread
    {
        private final String Cad;
        private final String Inter;
        public Hilo_Coordinador(String cadena, String intervalo){
            Cad = cadena;
            Inter = intervalo;
        }

        private void Apagar(){Encendido = false; interrupt();}
        private boolean Encendido = true;
        @Override
        public void run() {
            BigDecimal RES = new BigDecimal("0");
            for (int i = 0; i < Conexiones.size(); i++) {
                System.out.println("ESTOY DANDO ORDENES");
                Conexion mC = Conexiones.get(i);
                mC.Ordenar(1,Cad);
                RES = RES.add(new BigDecimal(mC.Escuchar()));
                mC.Ordenar(0,Inter);
                RES = RES.add(new BigDecimal(mC.Escuchar()));
                mC.Ordenar(2,"");
                //RES = RES.add(new BigDecimal(mC.Escuchar()));
                System.out.println("Resultado de hilo : " + RES.toString());
            }

            /*
            while(Encendido)
            {
                try {


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
        }

    }


    private class Hilo_Esperador extends Thread
    {
        private void Apagar(){Encendido = false; interrupt(); }

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
