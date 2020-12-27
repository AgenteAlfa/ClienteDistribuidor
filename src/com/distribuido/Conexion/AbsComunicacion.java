package com.distribuido.Conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class AbsComunicacion {
    private final Socket mSocket;
    private ObjectInputStream  OIS;
    private ObjectOutputStream OOS;
    public AbsComunicacion(Socket socket)
    {
        mSocket = socket;
        try {
            OOS = new ObjectOutputStream(mSocket.getOutputStream());
            OIS = new ObjectInputStream(mSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Ordenar(int orden,String cadena)
    {
        try {
            OOS.writeObject(orden);
            OOS.writeObject(cadena);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error desconocido");
        }

    }
    public void Responder(String res)
    {
        try {
            OOS.writeObject(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int LeerOrden()
    {
        try {
            if (mSocket.isConnected())
                return (int) OIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String Escuchar()
    {
        try {
            if (mSocket.isConnected())
                return (String) OIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
