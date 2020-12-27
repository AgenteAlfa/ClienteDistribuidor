package com.distribuido.Conexion.Coordinador;

import com.distribuido.Conexion.AbsComunicacion;

import java.math.BigDecimal;
import java.net.Socket;

public class CDistribuidor extends AbsComunicacion implements Runnable {

    private Thread Hilo;

    public CDistribuidor(Socket socket) {
        super(socket);
    }

    public void Iniciar() {
        Hilo = new Thread(this);
        Hilo.start();
    }

    @Override
    public void run() {
        String string;
        while (( string = Coordinador.Intancia.getINTERVALO() )!= null)
        {
            //System.out.println(Hilo.getName() + " : hace " + string);
            Ordenar(1,string);
            Coordinador.Intancia.addRes(new BigDecimal(Escuchar()));
        }
        Ordenar(2,"");

    }
}
