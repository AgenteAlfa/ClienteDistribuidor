package com.distribuido.Ventana;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
    int ancho=974;
    int alto=760;
    FondoPanel fondo = new FondoPanel();

    public Ventana(){
        setSize(ancho, alto);
        int PY1=515;
        int PY2=580;

        this.setContentPane(fondo);
        fondo.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Label A
        JLabel A = new JLabel();
        A.setText("a:");
        A.setBounds(50,PY1,55,55);
        A.setFont(new Font("Arial",Font.BOLD,50));
        add(A);
        //JTextFieldA
        JTextField JTextFieldA = new JTextField();
        JTextFieldA.setText("0");
        JTextFieldA.setBounds(100,PY1,70,55);
        JTextFieldA.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldA);

        //Label B
        JLabel B = new JLabel();
        B.setText(", b:");
        B.setBounds(180,PY1,80,55);
        B.setFont(new Font("Arial",Font.BOLD,50));
        add(B);
        //JTextFieldB
        JTextField JTextFieldB = new JTextField();
        JTextFieldB.setText("10");
        JTextFieldB.setBounds(260,PY1,70,55);
        JTextFieldB.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldB);

        //Label FX
        JLabel FX = new JLabel();
        FX.setText(", f(x):");
        FX.setBounds(330,PY1,140,55);
        FX.setFont(new Font("Arial",Font.BOLD,50));
        add(FX);
        //JTextFieldFX
        JTextField JTextFieldFX = new JTextField();
        JTextFieldFX.setText("10");
        JTextFieldFX.setBounds(460,PY1,250,55);
        JTextFieldFX.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldFX);

        //Label N
        JLabel N = new JLabel();
        N.setText(", n:");
        N.setBounds(710,PY1,80,55);
        N.setFont(new Font("Arial",Font.BOLD,50));
        add(N);
        //JTextFieldN
        JTextField JTextFieldN = new JTextField();
        JTextFieldN.setText("10");
        JTextFieldN.setBounds(790,PY1,120,55);
        JTextFieldN.setFont(new Font("Arial",Font.BOLD,35));
        add(JTextFieldN);

        //Boton calcular
        JButton BCalcular = new JButton();
        BCalcular.setText("Calcular");
        BCalcular.setBounds(400,PY2,150,45);
        BCalcular.setFont(new Font("Arial",Font.BOLD,25));
        BCalcular.setMnemonic('c');//tecla para hacerlo funcionar ALT+c
        add(BCalcular);

        //ProgressBar
        JProgressBar BProgreso = new JProgressBar();
        BProgreso.setBounds(300,640,350,5);
        add(BProgreso);

        //Label R
        JLabel R = new JLabel();
        R.setText("Resultado:");
        R.setBounds(310,650,300,55);
        R.setFont(new Font("Arial",Font.BOLD,25));
        add(R);

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
