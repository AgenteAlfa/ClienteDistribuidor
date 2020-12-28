package com.distribuido.Ventana;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
    int ancho=974;
    int alto=760;
    FondoPanel fondo = new FondoPanel();

    public Ventana(){
        setSize(ancho, alto);
        setLayout(null);
        this.setContentPane(fondo);


        JLabel A = new JLabel();
        A.setText("a:");
        A.setBounds(50,560,55,55);
        A.setFont(new Font("Arial",Font.BOLD,50));
        add(A);
        setVisible(true);

    }

    public static void main (String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }


    class FondoPanel extends JPanel{
        private Image imagen;

        @Override
        public void paint(Graphics g){
            //imagen = new ImageIcon(getClass().getResource("src\\com\\distribuido\\Ventana\\imagenes\\fondoIntegral.jpg")).getImage();
            imagen = new ImageIcon(getClass().getResource("imagenes/fondoIntegral.jpg")).getImage();
            g.drawImage(imagen,0,0,this);

            setOpaque(false);
            super.paint(g);

        }
    }
}
