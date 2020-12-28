package com.distribuido.Ventana;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
    int ancho=655;
    int alto=530;

    FondoPanel fondo = new FondoPanel();

    public Ventana(){
        setSize(ancho, alto);
        int PY1=340;
        int PY2=380;
        int tamanoLetraJL=25;

        this.setContentPane(fondo);
        fondo.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Label A
        JLabel A = new JLabel();
        A.setText("a:");
        A.setBounds(20,PY1,55,30);
        A.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(A);
        //JTextFieldA
        JTextField JTextFieldA = new JTextField();
        JTextFieldA.setText("0");
        JTextFieldA.setBounds(48,PY1,70,30);
        JTextFieldA.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldA);

        //Label B
        JLabel B = new JLabel();
        B.setText(", b:");
        B.setBounds(119,PY1,80,30);
        B.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(B);
        //JTextFieldB
        JTextField JTextFieldB = new JTextField();
        JTextFieldB.setText("10");
        JTextFieldB.setBounds(159,PY1,70,30);
        JTextFieldB.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldB);

        //Label FX
        JLabel FX = new JLabel();
        FX.setText(", f(x):");
        FX.setBounds(229,PY1,140,30);
        FX.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(FX);
        //JTextFieldFX
        JTextField JTextFieldFX = new JTextField();
        JTextFieldFX.setText("10");
        JTextFieldFX.setBounds(295,PY1,210,30);
        JTextFieldFX.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldFX);

        //Label N
        JLabel N = new JLabel();
        N.setText(", n:");
        N.setBounds(505,PY1,80,30);
        N.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(N);
        //JTextFieldN
        JTextField JTextFieldN = new JTextField();
        JTextFieldN.setText("10");
        JTextFieldN.setBounds(547,PY1,70,30);
        JTextFieldN.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldN);

        //Boton calcular
        JButton BCalcular = new JButton();
        BCalcular.setText("Calcular");
        BCalcular.setBounds(250,PY2,150,30);
        BCalcular.setFont(new Font("Arial",Font.BOLD,20));
        BCalcular.setMnemonic('c');//tecla para hacerlo funcionar ALT+c
        add(BCalcular);
        //BCalcular.addActionListener(this);

        //ProgressBar
        JProgressBar BProgreso = new JProgressBar();
        BProgreso.setBounds(150,420,350,5);
        add(BProgreso);

        //Label R
        JLabel R = new JLabel();
        R.setText("Resultado:");
        R.setBounds(200,440,300,30);
        R.setFont(new Font("Arial",Font.BOLD,20));
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
