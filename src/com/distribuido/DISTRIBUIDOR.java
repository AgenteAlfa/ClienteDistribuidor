package com.distribuido;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.Configuracion;
import com.distribuido.Conexion.Coordinador.Coordinador;
import com.distribuido.Conexion.Distribuidor.Distribuidor;
import com.distribuido.Conexion.Obrero.Obrero;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class DISTRIBUIDOR {
    public static void main(String[] args) {
        try {
            Scanner sc  = new Scanner(System.in);
            Distribuidor D = new Distribuidor(
                    new Socket(Configuracion.IP_PRIMARIA,Configuracion.PUERTO_PRIMARIO));
            Obrero O =  new Obrero(
                    new Socket(Configuracion.IP_SECUNDARIA,Configuracion.PUERTO_SECUNDARIO));

            O.Iniciar();
            System.out.println("Esperando,Presiona una tecla detener la espera...");
            sc.next();
                D.Iniciar();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
