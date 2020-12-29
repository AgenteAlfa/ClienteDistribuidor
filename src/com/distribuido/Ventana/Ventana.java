package com.distribuido.Ventana;

import com.distribuido.Cadena.Cadena;
import com.distribuido.Conexion.Coordinador.Coordinador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Ventana extends JFrame{
    int ancho=655;
    int alto=530;
    FondoPanel fondo = new FondoPanel();
    //Campos para llenar
    JLabel A = new JLabel();
    JLabel B = new JLabel();
    JLabel FX = new JLabel();
    JLabel N = new JLabel();
    JTextField JTextFieldA = new JTextField();
    JTextField JTextFieldB = new JTextField();
    JTextField JTextFieldFX = new JTextField();
    JTextField JTextFieldN = new JTextField();

    JButton BCalcular = new JButton();
    JProgressBar BProgreso = new JProgressBar();
    JLabel R = new JLabel();

    Coordinador mCoordinador;

    public Ventana(){
        try {
            mCoordinador = new Coordinador(this);
            mCoordinador.Esperar();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        setSize(ancho, alto);
        this.setContentPane(fondo);
        fondo.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        init();

        setVisible(true);

        //--ACCIÓN DEL BOTÓN

        ActionListener calcula=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //--CAMPO DE ACCIÓN DEL BOTÓN CALCULAR
                //-----------------------------

                //Obtiene los datos de la ventana
                // a:inicio de intervalo, b: final, f(x): funcion en string
                double a= Double.parseDouble(JTextFieldA.getText());
                double b= Double.parseDouble(JTextFieldB.getText());
                String fx=JTextFieldFX.getText();
                String mIntervalo = a+" "+b;
                //Inicia el calculo

                mCoordinador.Empezar(fx,mIntervalo);
                //R.setText("Resultado: "+Cad.Integrar(a+" "+b).toString());
                //------------------------------------
            }
        };

        BCalcular.addActionListener(calcula);

    }

    public void SetRespuesta(String S)
    {
        R.setText("Resultado: " + S);
    }
    public void AumentarProgreso()
    {
        BProgreso.setValue(BProgreso.getValue() +  100/Coordinador.NUM_INTERVALO);
    }


    public static void main (String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    private void init(){
        int PY1=340;
        int PY2=380;
        int tamanoLetraJL=25;
        //Label A
        A.setText("a:");
        A.setBounds(20,PY1,55,30);
        A.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(A);
        //JTextFieldA
        JTextFieldA.setText("0");
        JTextFieldA.setBounds(48,PY1,70,30);
        JTextFieldA.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldA);
        //Label B
        B.setText(", b:");
        B.setBounds(119,PY1,80,30);
        B.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(B);
        //JTextFieldB
        JTextFieldB.setText("10");
        JTextFieldB.setBounds(159,PY1,70,30);
        JTextFieldB.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldB);
        //Label FX
        FX.setText(", f(x):");
        FX.setBounds(229,PY1,140,30);
        FX.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(FX);
        //JTextFieldFX
        JTextFieldFX.setText("10");
        JTextFieldFX.setBounds(295,PY1,210,30);
        JTextFieldFX.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldFX);
        //Label N
        N.setText(", n:");
        N.setBounds(505,PY1,80,30);
        N.setFont(new Font("Arial",Font.BOLD,tamanoLetraJL));
        add(N);
        //JTextFieldN
        JTextFieldN.setText("10");
        JTextFieldN.setBounds(547,PY1,70,30);
        JTextFieldN.setFont(new Font("Arial",Font.BOLD,20));
        add(JTextFieldN);
        //Boton calcular
        BCalcular.setText("Calcular");
        BCalcular.setBounds(250,PY2,150,30);
        BCalcular.setFont(new Font("Arial",Font.BOLD,20));
        BCalcular.setMnemonic('c');//tecla para hacerlo funcionar ALT+c
        add(BCalcular);
        //ProgressBar
        BProgreso.setBounds(150,420,350,5);
        add(BProgreso);
        //Label R
        R.setText("Resultado:");
        R.setBounds(200,440,300,30);
        R.setFont(new Font("Arial",Font.BOLD,20));
        add(R);
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
