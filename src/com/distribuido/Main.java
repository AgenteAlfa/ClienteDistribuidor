package com.distribuido;

import com.distribuido.Cadena.Cadena;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        Cadena C = new Cadena("2x^2 - x");
        C.Imprimir();
        System.out.println(C.Integrar("1 10").toString());;
        //System.out.println("Resultado integrar : " + C.EjecutarOrden('0',"6 12 0.00001"));

    }
}
