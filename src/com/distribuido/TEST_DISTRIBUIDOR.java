package com.distribuido;

import com.distribuido.Conexion.CentralDistribuidor;

import java.io.IOException;

public class TEST_DISTRIBUIDOR {

    public static void main(String[] args) {

        try {
            CentralDistribuidor CD = new CentralDistribuidor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
