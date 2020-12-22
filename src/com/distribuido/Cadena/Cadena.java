package com.distribuido.Cadena;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Cadena {
    public static Cadena Datos;

    public ArrayList<Simbolo> Simbolos;
    public enum STATUS {NS,NUMERO,X,CE,ES,EXPONENTE,SIG}
    private STATUS mStatus = STATUS.NS;

    public Cadena(String S)
    {
        Datos = this;
        Simbolos = new ArrayList<>();
        Simbolos.add(new Simbolo());
        try {
            Leer(S);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Leer(String S) throws IOException {
        StringReader R = new StringReader(S);

        int letra = -1;
        Simbolo Ultimo = null;
        boolean leer = true;
        boolean equis = false;
        while (true)
        {
            equis = letra == 'x' || letra == 'X';
            //System.out.println("LETRA : " + + ((char) letra)  + " : " + leer);
            letra = leer? R.read():letra;
            if (letra == -1)
            {
                if (Ultimo != null )Ultimo.Corregir();
                break;
            }
            if (letra == ' ') continue;
            leer = true;
            //System.out.println("STATUS : " + mStatus.toString());
            //System.out.println((char)letra);
            Ultimo = Simbolos.get(Simbolos.size() - 1);

            switch (mStatus)
            {

                case NS:
                case ES:
                    //System.out.println("Letra simbolo " + (char)letra);
                    switch (letra)
                    {

                        case '+':
                        case '-':
                            Ultimo.setPositivo( letra == '+' );
                            break;
                        default:
                            Ultimo.setPositivo(true);
                            leer = false;
                            break;
                    }
                    mStatus = STATUS.values()[mStatus.ordinal() + 1];
                    break;

                case EXPONENTE:
                case NUMERO:
                    switch (letra)
                    {
                        case '0': case '1': case '2': case '3':case '4':
                        case '5': case '6': case '7': case'8': case '9':
                            Ultimo.addVal(letra);
                            break;
                        default:
                            leer = false;
                            mStatus = STATUS.values()[mStatus.ordinal() + 1];
                            break;
                    }
                    break;

                case X:
                   //System.out.println("Evaluando X");
                    switch (letra)
                    {

                        case '+': case '-':
                        case '0': case '1': case '2': case '3':case '4':
                        case '5': case '6': case '7': case'8': case '9':
                            leer = false;
                            Ultimo.ValExp = new BigDecimal("0");
                        case 'x':
                        case 'X':
                            Ultimo.equis = true;
                            break;
                    }
                    mStatus = STATUS.values()[mStatus.ordinal() + 1];

                    //System.out.println("FIN X");
                    break;
                case CE:
                    //System.out.println("Evaluando CE");
                    switch (letra)
                    {
                        case '+': case '-':
                        case '0': case '1': case '2': case '3':case '4':
                        case '5': case '6': case '7': case'8': case '9':
                            leer = false;
                            mStatus = STATUS.SIG;
                            break;
                        case '^':
                            mStatus = STATUS.values()[mStatus.ordinal() + 1];
                            break;
                    }

                    break;
                case SIG:
                    Ultimo.Corregir();
                    leer = false;
                    Simbolos.add(new Simbolo());
                    mStatus = STATUS.NS;
                    break;
            }
        }

    }

    public String Codificar()
    {
        StringBuilder builder = new StringBuilder();
        for (Simbolo S : Simbolos) {
            builder.append(S);
        }
        return builder.toString();
    }
    public void Imprimir()
    {

        for (Simbolo S : Simbolos) {
            System.out.print(S.Codificar() + "\t");
        }
    }

    public String EjecutarOrden(int ord,String cad)
    {
        ///Parte en evaluacion ... implementada por ahora
        BigDecimal R = new BigDecimal("0");
        switch (ord)
        {
            case '0'://Ejecutar cadena cad forma : ini fin inter
                String[] cadena = cad.split(" ");
                BigDecimal A = new BigDecimal(cadena[0]) ,
                        B = new BigDecimal(cadena[1]),
                        I = new BigDecimal(cadena[2]);
                if (A.compareTo(B) > 0)
                {
                    I = I.negate();
                }
                while (A.compareTo(B) <= 0)
                {
                    BigDecimal temp = EvaluarTodo(A.add(I)) .add(EvaluarTodo(A)); // A+I + A
                    //System.out.println("A : " + A.toString() + " Temp : " + temp.toString());
                    temp = temp.multiply(I);
                    //System.out.println("temp : " + temp.toString());
                    //Datos arbitrarios , no se como configurar esto
                    temp = temp.divide(new BigDecimal("2"),RoundingMode.DOWN);
                    //System.out.println("temp : " + temp.toString());
                    R = R.add(temp);
                    A = A.add(I);
                }
                break;
            case '1':
                //apagarse
                break;

        }


        return R.toString();
    }

    private BigDecimal EvaluarTodo(BigDecimal X)
    {
        BigDecimal R = new BigDecimal("0");
        for (Simbolo mS: Simbolos) {
            R = R.add(mS.Evaluar(X));
        }
        return R;
    }

    private class Simbolo
    {

        public BigDecimal ValCoc,ValExp;
        private boolean asig;
        private boolean equis = false;
        private boolean positivo = true;
        private Simbolo()
        {
            //ValCoc = new BigDecimal("0");
            //ValExp = new BigDecimal("0");
        };

        private void Corregir()
        {
            if (ValCoc == null) ValCoc = new BigDecimal("1");

            if (ValExp == null) ValExp = equis?new BigDecimal("1"):new BigDecimal("0");
        }
        public void setPositivo(boolean positivo) {
            this.positivo = positivo;
            //System.out.println("Seteado : " + positivo);
        }
        public void addVal(int c)
        {
            //System.out.println("Agregando..." + (char) c);
            if (mStatus == STATUS.NUMERO)
            {
                String anterior = ValCoc != null?ValCoc.toString() : "";
                ValCoc = new BigDecimal(
                            ( anterior + (char)c) );
                if (!positivo && ValCoc.signum() == 1) {
                    //System.out.println("Antiguo : " + ValCoc.toString());
                    ValCoc = ValCoc.negate();
                    //System.out.println("Nuevo : " + ValCoc.toString());
                }

            }
            else
            {
                String anterior = ValExp != null?ValExp.toString() : "";
                ValExp = new BigDecimal(
                        ( anterior + (char)c) );
                if (!positivo && ValExp.signum() == 1) ValExp = ValExp.negate();
            }

        }
        public BigDecimal Evaluar(BigDecimal X)
        {
            return X.pow(ValExp.toBigInteger().intValue()).multiply(ValCoc);
        }


        public String Codificar()
        {
            return (ValCoc.toString() + "X^" + ValExp.toString());
        }

    }
}
