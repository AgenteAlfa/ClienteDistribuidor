package com.distribuido.Conexion.Obrero;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.AbsComunicacion;

import java.net.Socket;

public class Obrero extends AbsComunicacion implements Runnable{
    Thread Hilo;
    Cadena mCadena;
    public Obrero(Socket socket) {
        super(socket);
    }



    public void Iniciar()
    {
        Hilo = new Thread(this);
        Hilo.start();
    }

    public void run() {

        int orden;
        String mensaje;
        try
        {
            while (true)
            {
                ////System.out.println("Conexion -> estoy en un bucle wiiiii");
                //Lee un codigo de orden
                orden =  LeerOrden();
                mensaje = Escuchar();
                //System.out.println(Hilo.getName() + "Conexion -> ORDEN : " + orden);
                //System.out.println(Hilo.getName() + "Conexion -> MENSAJE : " + mensaje);
                if (orden == 2) break;
                else
                {
                    switch (orden)
                    {
                        case 0:
                            mCadena = new Cadena(mensaje);
                            break;
                        case 1:
                            Responder(mCadena.Integrar(mensaje).toString());
                            break;
                    }

                }

                //Responder("0");
            }

            //System.out.println("Obrero -> Termine de trabajar...");

        }catch (Exception E)
        {
            //System.out.println(E.getMessage());
        }
    }





}
