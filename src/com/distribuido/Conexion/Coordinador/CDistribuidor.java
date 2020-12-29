package com.distribuido.Conexion.Coordinador;

import com.distribuido.Conexion.AbsComunicacion;

import java.math.BigDecimal;
import java.net.Socket;

public class CDistribuidor extends AbsComunicacion implements Runnable {

    private Thread Hilo;
    private Coordinador Padre;

    public CDistribuidor(Socket socket,Coordinador P) {
        super(socket);
        Padre = P;
    }

    public void Iniciar() {
        Hilo = new Thread(this);
        Hilo.start();
    }

    @Override
    public void run() {
        String string;
        while (( string = Padre.getINTERVALO() )!= null)
        {
            //System.out.println(Hilo.getName() + " : hace " + string);
            Ordenar(1,string);
            Padre.Intancia.addRes(new BigDecimal(Escuchar()));
        }
        //System.out.println("Estoy tomando un ORDEN 2");
        Ordenar(2," ");

    }
}
