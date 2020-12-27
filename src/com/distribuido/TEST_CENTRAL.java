package com.distribuido;

import com.distribuido.Conexion.Coordinador.Coordinador;

import java.io.IOException;
import java.util.Scanner;

public class TEST_CENTRAL {
    public static void main(String[] args) {
        try {
            Scanner SC = new Scanner(System.in);
            Coordinador C = new Coordinador();
            C.Esperar();
            System.out.println("Ingresa una un polinomio");
            String Pol = SC.nextLine();
            System.out.println("Ingresa el intervalo");
            String Itv = SC.nextLine();

            C.Empezar(Pol,Itv);
            System.out.println(Pol + " : " + Itv);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
