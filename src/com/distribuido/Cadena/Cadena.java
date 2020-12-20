package com.distribuido.Cadena;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Cadena {
    public ArrayList<Simbolo> Simbolos;
    public enum STATUS {NS,NUMERO,X,CE,ES,EXPONENTE,SIG}
    private STATUS mStatus = STATUS.NS;

    public Cadena(String S)
    {
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
        Simbolo Ultimo;
        boolean leer = true;
        while (R.ready())
        {
            letra = leer? R.read():letra;
            if (letra == -1) break;
            if (letra == ' ') continue;
            leer = true;
            System.out.println("STATUS : " + mStatus.toString());
            System.out.println((char)letra);
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
                    switch (letra)
                    {
                        case '+': case '-':
                        case '0': case '1': case '2': case '3':case '4':
                        case '5': case '6': case '7': case'8': case '9':
                            leer = false;
                            Ultimo.ValExp = new BigInteger("0");
                        case 'x':
                        case 'X':
                            break;
                    }
                    mStatus = STATUS.values()[mStatus.ordinal() + 1];
                    break;
                case CE:
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

    private class Simbolo
    {

        public BigInteger ValCoc,ValExp;
        private boolean asig;
        private boolean positivo = true;
        private Simbolo()
        {
            //ValCoc = new BigInteger("0");
            //ValExp = new BigInteger("0");
        };

        private void Corregir()
        {
            if (ValCoc == null) ValCoc = new BigInteger("1");
            if (ValExp == null) ValExp = new BigInteger("1");
        }
        public void setPositivo(boolean positivo) {
            this.positivo = positivo;
            //System.out.println("Seteado : " + positivo);
        }
        public void addVal(int c)
        {
            System.out.println("Agregando..." + (char) c);
            if (mStatus == STATUS.NUMERO)
            {
                String anterior = ValCoc != null?ValCoc.toString() : "";
                ValCoc = new BigInteger
                            ( anterior + (char)c);
                if (!positivo && ValCoc.signum() == 1) {
                    //System.out.println("Antiguo : " + ValCoc.toString());
                    ValCoc = ValCoc.negate();
                    //System.out.println("Nuevo : " + ValCoc.toString());
                }

            }
            else
            {
                String anterior = ValExp != null?ValExp.toString() : "";
                ValExp = new BigInteger
                        ( anterior + (char)c);
                if (!positivo && ValExp.signum() == 1) ValExp = ValExp.negate();
            }

        }

        public String Codificar()
        {
            return (ValCoc.toString() + "X^" + ValExp.toString());
        }

    }
}
