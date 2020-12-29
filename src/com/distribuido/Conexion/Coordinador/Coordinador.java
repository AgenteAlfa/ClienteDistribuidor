package com.distribuido.Conexion.Coordinador;

import com.distribuido.Conexion.Configuracion;
import com.distribuido.Ventana.Ventana;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Coordinador extends ServerSocket {

    public static final int NUM_INTERVALO = 20;
    public Coordinador Intancia;
    private final BigDecimal[] Intervalos;
    private long tiempo;
    private Coordinador ESTE;


//10.33954199040433515858122508

    public BigDecimal getRespuesta() {
        return Respuesta;
    }

    public int IESIMO_INTERVALO;
    public int IESIMA_RESPUESTA;
    private BigDecimal Respuesta;
    private final ArrayList<CDistribuidor> Distribuidores;
    private Hilo_Esperador HE ;
    private Ventana mVentana;


    public Coordinador(Ventana ventana) throws IOException {
        super(Configuracion.PUERTO_PRIMARIO);
        setSoTimeout(100);
        Distribuidores = new ArrayList<>();
        Intervalos = new BigDecimal[NUM_INTERVALO + 1];
        Intancia = this;
        mVentana = ventana;
    }
    public void Esperar()
    {
        HE = new Hilo_Esperador();
        HE.start();
    }
    public void Empezar(String S_cadena ,String S_intervalo)
    {
        tiempo = new Date().getTime();
        HE.Apagar();
        //Iniciar hilo comunicador si hay mas de 1 conexion, sino no hacer nada
        if (Distribuidores.size() > 0)
        {

            Respuesta = new BigDecimal(0);
            Intervalos[0] = new BigDecimal(S_intervalo.split(" ")[0]);
            Intervalos[NUM_INTERVALO] = new BigDecimal(S_intervalo.split(" ")[1]);
            BigDecimal delta = Intervalos[NUM_INTERVALO].subtract(Intervalos[0])
                    .divide(new BigDecimal(NUM_INTERVALO),Configuracion.ESCALA,RoundingMode.DOWN);
            //System.out.println("delta : " + delta.toString());

            for (int i = 1; i <= NUM_INTERVALO - 1; i++) {
                Intervalos[i] = Intervalos[i - 1].add(delta);
            }
            //Envia la cadena a todos los Distribuidores
            for (CDistribuidor distribuidor : Distribuidores) {
                distribuidor.Ordenar(0, S_cadena);
            }

            IESIMO_INTERVALO = 0;
            IESIMA_RESPUESTA = 0;

            for (CDistribuidor distribuidor : Distribuidores) {
                distribuidor.Iniciar();
            }

        }else
            System.out.println("Coordinador -> No existen suficientes trabajadores para integrar");

    }


    public synchronized String getINTERVALO() {
        String res = null;

        if ( IESIMO_INTERVALO < NUM_INTERVALO)
        {
            res = Intervalos[IESIMO_INTERVALO].toString() + " " + Intervalos[IESIMO_INTERVALO + 1];
            IESIMO_INTERVALO++;
        }
        //System.out.println("Coordinador : Se a tomado un intervalo ... que es " + res);
        return res;
    }

    public synchronized void addRes(BigDecimal entrada) {
        //System.out.println("Ya va : " + Respuesta.toString() + " -> " + NUM_INTERVALO);
        Respuesta = Respuesta.add(entrada);
        IESIMA_RESPUESTA++;
        mVentana.AumentarProgreso();
        if(IESIMA_RESPUESTA == NUM_INTERVALO)
            mVentana.SetRespuesta(getRespuesta().toString() + " en " + (new Date().getTime() - tiempo) + " ms");
            //System.out.println("RESPUESTA FINAL : " + getRespuesta().toString() + );
    }

    private class Hilo_Esperador extends Thread
    {

        private void Apagar(){
            System.out.println("ESPERADOR : DEJO DE ESPERAR DISTRIBUIDORES");
            Encendido = false;  }
        private boolean Encendido = true;

        @Override
        public void run() {
            while(Encendido)
            {
                //System.out.println("Coordinador -> Estoy esperando distribuidores");
                try {
                    Distribuidores.add(new CDistribuidor(accept(),Intancia));
                    System.out.println("A ingresado un nuevo Distribuidor");
                } catch (IOException e) {
                   // e.printStackTrace();
                }
            }
            //System.out.println("Yo ya termine -> ESPERADOR");
        }
    }

}
