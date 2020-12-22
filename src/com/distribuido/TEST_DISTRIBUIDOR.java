package com.distribuido;

import com.distribuido.Conexion.CentralDistribuidor;
import com.distribuido.Conexion.Conexion;
import com.distribuido.Conexion.Coordinador;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TEST_DISTRIBUIDOR {

    public static void main(String[] args) {

        System.out.println("1  s");
        Conexion C = null;
        try {
            ;
            //System.out.println("aSdasdasd");
            C = new Conexion(new Socket(Conexion.IP,Conexion.PUERTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("2  ss");
        C.Trabajar();

    }

}
