package com.distribuido.Conexion.Distribuidor;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.AbsComunicacion;
import com.distribuido.Conexion.Coordinador.Coordinador;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DObrero extends AbsComunicacion implements Runnable{

    private Thread hilo;
    private Distribuidor mDistribuidor;
    public DObrero(Socket socket,Distribuidor distribuidor) {
        super(socket);
        mDistribuidor = distribuidor;
    }
    public void Iniciar()
    {
        //if (hilo == null)
            hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        String string;
        while (( string = mDistribuidor.getINTERVALO() )!= null)
        {
            //System.out.println(hilo.getName() + " obrero : hace " + string);
            Ordenar(1,string);
            mDistribuidor.addRes(new BigDecimal(Escuchar()));
            //mDistribuidor.addRes(new BigDecimal("0.01"));
        }
        //Ordenar(2,"");

    }
}
