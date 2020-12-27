package com.distribuido.Conexion.Distribuidor;

import com.distribuido.Conexion.AbsComunicacion;
import com.distribuido.Conexion.Configuracion;
import com.distribuido.Conexion.Coordinador.CDistribuidor;
import com.distribuido.Conexion.Coordinador.Coordinador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Distribuidor extends AbsComunicacion implements Runnable{
    private Thread Hilo_CenDis;
    private Hilo_Esperador HE;
    private ServerSocket SSocket;
    private ArrayList<DObrero> Obreros;

    public  final int NUM_INTERVALO = 30;
    public static Coordinador Intancia;
    private  BigDecimal[] Intervalos;

    private Distribuidor ESTE;


    public int IESIMO_INTERVALO;
    public int IESIMA_RESPUESTA;


    private BigDecimal Respuesta;

    public BigDecimal getRespuesta() {
        return Respuesta;
    }

    public Distribuidor(Socket socket) {
        super(socket);
        ESTE = this;
        try {
            SSocket = new ServerSocket(Configuracion.PUERTO_SECUNDARIO);
            SSocket.setSoTimeout(100);
            Obreros = new ArrayList<>();
            Intervalos = new BigDecimal[NUM_INTERVALO + 1];

            HE = new Hilo_Esperador();
            HE.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized String getINTERVALO() {
        String res = null;
        if ( IESIMO_INTERVALO < NUM_INTERVALO)
        {
            res = Intervalos[IESIMO_INTERVALO].toString() + " " + Intervalos[IESIMO_INTERVALO + 1];
            IESIMO_INTERVALO++;
        }
        return res;
    }

    public synchronized void addRes(BigDecimal entrada) {
        //System.out.println("Ya va : " + Respuesta.toString() + " -> " + NUM_INTERVALO);
        Respuesta = Respuesta.add(entrada);
        IESIMA_RESPUESTA++;
        if(IESIMA_RESPUESTA == NUM_INTERVALO)
            System.out.println("RESPUESTA FINAL de distribuidor: " + Respuesta.toString());
    }


    public void Iniciar()
    {
        HE.Apagar();
        Hilo_CenDis = new Thread(this);
        Hilo_CenDis.start();
    }
    private class Hilo_Esperador extends Thread
    {

        private void Apagar(){
            //System.out.println("ESPERADOR : DEJO DE ESPERAR DISTRIBUIDORES");
            Encendido = false;  }
        private boolean Encendido = true;

        @Override
        public void run() {
            while(Encendido)
            {
                //System.out.println("Coordinador -> Estoy esperando distribuidores");
                try {
                    Obreros.add(new DObrero(SSocket.accept() , ESTE));
                    //System.out.println("Coordinador -> Tengo " + Distribuidores.size() + " Distribuidores");
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
            //System.out.println("Yo ya termine -> ESPERADOR de distribuidor");
        }
    }

    public void Empezar(String S_intervalo)
    {
        HE.Apagar();
        //System.out.println("SOY Distribuidor y tengo " + Obreros.size());
        //Iniciar hilo comunicador si hay mas de 1 conexion, sino no hacer nada
        if (Obreros.size() > 0)
        {
            Respuesta = new BigDecimal(0);
            //Envia el intervalo a los Distribuidores...
            //Pone el punto final y inicial y rellena puntos medios
            Intervalos[0] = new BigDecimal(S_intervalo.split(" ")[0]);
            Intervalos[NUM_INTERVALO] = new BigDecimal(S_intervalo.split(" ")[1]);

            BigDecimal delta = Intervalos[NUM_INTERVALO].subtract(Intervalos[0])
                    .divide(new BigDecimal(NUM_INTERVALO),Configuracion.ESCALA, RoundingMode.DOWN);
            System.out.println(Hilo_CenDis.getName() + " - delta : " + delta.toString());
            for (int i = 1; i < NUM_INTERVALO; i++) {
                Intervalos[i] = Intervalos[i - 1].add(delta);
                //System.out.println(Intervalos[i].toString());
            }
            IESIMO_INTERVALO = 0;
            IESIMA_RESPUESTA = 0;
            for (DObrero obrero : Obreros) {
                obrero.Iniciar();
            }

        }else
            System.out.println("Coordinador -> No existen suficientes trabajadores para integrar");

    }


    @Override
    public void run() {
        while (true)
        {
            //Leer Cadena
            int Orden = LeerOrden();
            if(Orden == 2)
            {
                System.out.println("Trabajo terminado");
                break;
            }
            else
                {
                    String mensaje = Escuchar();
                    //System.out.println(Hilo_CenDis.getName() + " : " + mensaje);
                    switch (Orden)
                    {
                        case 0:
                            //Reenviar mensaje a obreros;
                            for (DObrero O: Obreros) {
                                O.Ordenar(0,mensaje);
                            }
                            break;
                        case 1:
                            //Dividir y enviar a todos los obreros
                            Empezar(mensaje);

                            //Emitir respuesta
                            while (IESIMA_RESPUESTA != NUM_INTERVALO){
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            Responder(Respuesta.toString());
                            break;
                    }
                }

        }
    }
}
