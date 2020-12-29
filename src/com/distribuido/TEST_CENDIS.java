package com.distribuido;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.Configuracion;
import com.distribuido.Conexion.Distribuidor.Distribuidor;
import com.distribuido.Conexion.Coordinador.Coordinador;
import com.distribuido.Conexion.Distribuidor.DObrero;
import com.distribuido.Conexion.Obrero.Obrero;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class TEST_CENDIS {
    public static void main(String[] args) {
        try {
            Coordinador A = new Coordinador();
            Scanner sc  = new Scanner(System.in);
            A.Esperar();

            Distribuidor[] Distribuidores = new Distribuidor[]
                    {
                            new Distribuidor(new Socket(Configuracion.IP_PRIMARIA,Configuracion.PUERTO_PRIMARIO))
                    };

            Obrero[] Obreros = new Obrero[]
                    {
                            new Obrero(new Socket(Configuracion.IP_SECUNDARIA,Configuracion.PUERTO_SECUNDARIO)) };
            for (Obrero O:Obreros) {
                O.Iniciar();
            }
            System.out.println("Esperando tecla para iniciar...");
            //sc.next();
            for (Distribuidor D:Distribuidores) {
                D.Iniciar();
            }


            String funcion = "-2 + 9x - 6x^-6";
            String intervalo = "1 2";

            A.Empezar(funcion,intervalo);

            Cadena C = new Cadena(funcion);
            //C.Imprimir();
            long t = new Date().getTime();
            System.out.println("Secuencial :  " + C.Integrar(intervalo).toString() + " en " + ( new Date().getTime() - t ) + " ms");;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
