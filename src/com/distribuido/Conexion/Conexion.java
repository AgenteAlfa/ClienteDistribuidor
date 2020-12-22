package com.distribuido.Conexion;

import com.distribuido.Cadena.Cadena;

import java.io.*;
import java.net.InetSocketAddress;
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
            //System.out.println("AQUI TOY 1");
            OOS = new ObjectOutputStream(S.getOutputStream());
            //System.out.println("AQUI TOY 2");
            OIS = new ObjectInputStream(S.getInputStream());
            //System.out.println("AQUI TOY 3");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR TOY");
        }
    }
    public void Trabajar()
    {
        HObrero = new HiloObrero();
        HObrero.start();
        System.out.println("Este hilo esta trabajando");
    }
    public void Descansar(){HObrero.Apagar();}
    /**
     * Envia una orden a la conexion - Socket
     * la conexion respondera
     * */
    public void Ordenar(int orden,String cadena)
    {
        try {

            OOS.writeObject(orden);
            OOS.writeObject(cadena);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String Escuchar()
    {
        try {
            if (S.isConnected())
            return (String) OIS.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
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
                    //System.out.println("estoy en un bucle wiiiii");
                    //Lee un codigo de orden
                    orden = (int) OIS.readObject();
                    mensaje = (String) OIS.readObject();
                    System.out.println("ORDEN : " + orden);
                    System.out.println("MENSAJE : " + mensaje);
                    if (orden == 2) break;
                    /*la orden seria :
                    0: Calcular la integral segun el mensaje
                    1: Recibir funcion
                    2: Apagar
                    Forma del mensaje:
                    A B I
                    A = cota inferior
                    B = cota superior
                    I = intervalos en los que se ejecuta
                    ------------
                    3 + x + 6x^ 2 ... Se le directo por "Cadena"
                    -------------
                    null -> la orden de apagar no requiere cadena
                    *
                    * */
                    //Ejecuta la orden
                    OOS.writeObject(Cadena.EjecutarOrden(orden,mensaje));

                }

                System.out.println("Termine de trabajar...");
                OOS.close();
                OIS.close();
                S.close();

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
