package com.distribuido.Ventana;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
    int ancho=974;
    int alto=760;
    FondoPanel fondo = new FondoPanel();

    public Ventana(){
        setSize(ancho, alto);

        this.setContentPane(fondo);
        fondo.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Label A
        JLabel A = new JLabel();
        A.setText("a:");
        A.setBounds(50,540,55,55);
        A.setFont(new Font("Arial",Font.BOLD,50));
        add(A);
        //JTextFieldA
        JTextField JTextFieldA = new JTextField();
        JTextFieldA.setText("0");
        JTextFieldA.setBounds(100,540,70,55);
        JTextFieldA.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldA);

        //Label B
        JLabel B = new JLabel();
        B.setText(", b:");
        B.setBounds(180,540,80,55);
        B.setFont(new Font("Arial",Font.BOLD,50));
        add(B);
        //JTextFieldB
        JTextField JTextFieldB = new JTextField();
        JTextFieldB.setText("10");
        JTextFieldB.setBounds(260,540,70,55);
        JTextFieldB.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldB);

        //Label FX
        JLabel FX = new JLabel();
        FX.setText(", f(x):");
        FX.setBounds(330,540,140,55);
        FX.setFont(new Font("Arial",Font.BOLD,50));
        add(FX);
        //JTextFieldB
        JTextField JTextFieldFX = new JTextField();
        JTextFieldFX.setText("10");
        JTextFieldFX.setBounds(460,540,250,55);
        JTextFieldFX.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldFX);

        //Boton calcular
        JButton BCalcular = new JButton();
        BCalcular.setText("Calcular");
        BCalcular.setBounds(360,600,150,55);
        BCalcular.setMnemonic('c');//tecla para hacerlo funcionar ALT+c
        add(BCalcular);

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
