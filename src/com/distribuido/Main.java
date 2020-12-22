package com.distribuido;

import com.distribuido.Cadena.Cadena;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        Cadena C = new Cadena("3x -4x^2");
        C.Imprimir();
        System.out.println("Resultado integrar : " + C.EjecutarOrden('0',"6 12 0.00001"));

    }
}
