package com.distribuido.Conexion;

import com.distribuido.Cadena.Cadena;

import java.io.*;
import java.net.Socket;

public class Conexion {
    public static final int PUERTO = 1234;
    public static final String IP = "192.168.0.14";

    private Socket S;
    private ObjectInputStream  OIS;
    private ObjectOutputStream OOS;

    private HiloObrero HObrero;

    public Conexion (Socket s)
    {
        S = s;
        try {
            OIS = new ObjectInputStream(S.getInputStream());
            OOS = new ObjectOutputStream(S.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void Trabajar()
    {
        HObrero = new HiloObrero();
        HObrero.start();
    }
    public void Descansar(){HObrero.Apagar();}
    public void Ordenar(int orden,String cadena)
    {
        try {
            OOS.writeObject(orden);
            OOS.writeObject(cadena);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class HiloObrero extends Thread
    {
        private boolean Encendido = true;
        @Override
        public void run() {
            int orden;
            String mensaje;
            try
            {
                while (Encendido)
                {
                    //Lee un codigo de orden
                    orden = (int) OIS.readObject();
                    mensaje = (String) OIS.readObject();
                    /*la orden seria :
                    0: Calcular la integral segun el mensaje
                    1: Apagar
                    Forma del mensaje:
                    A B I
                    A = cota inferior
                    B = cota superior
                    I = intervalos en los que se ejecuta
                    *
                    * */
                    //Ejecuta la orden
                    OOS.writeObject(Cadena.Datos.EjecutarOrden(orden,mensaje));

                }


            }catch (Exception E)
            {
                System.out.println(E.getMessage());
            }
        }
        public void Apagar(){
            Encendido = false;
        }
    }



}
