package com.distribuido;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.CentralDistribuidor;

import java.io.IOException;

public class TEST_CENTRAL {
    public static void main(String[] args) {
        try {
            CentralDistribuidor CD = new CentralDistribuidor(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
