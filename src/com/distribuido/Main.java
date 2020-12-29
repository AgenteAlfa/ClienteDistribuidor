package com.distribuido;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Ventana.Ventana;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        //Ventana V = new Ventana();

        Cadena C = new Cadena("2x^2 - x");
        C.Imprimir();
        BigDecimal A = C.Integrar("1 2"),B = C.Integrar("2 3");
        System.out.println(A.toString());
        System.out.println(B.toString());
        System.out.println(A.add(B).toString());
        System.out.println(C.Integrar("1 3").toString());;
        //System.out.println("Resultado integrar : " + C.EjecutarOrden('0',"6 12 0.00001"));

    }
}
