package com.distribuido.Cadena;

import com.distribuido.Conexion.Configuracion;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Cadena {
    public static Cadena Datos;
    public static final String DEFAULT_INTERVALO = "0.00001";
    public ArrayList<Simbolo> Simbolos;
    public enum STATUS {NS,NUMERO,X,CE,ES,EXPONENTE,SIG}
    private STATUS mStatus = STATUS.NS;

    private int PunAct = 0;
    private int PunAnt = 0;
    private static final long Vueltas = 10;
    private int Contador = 0;
    private static int HILOS = 1;



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

        while ( (letra = leer? R.read():letra) != -1 || mStatus != STATUS.SIG)
        {
            if (letra == ' ') continue; else if (letra == -1) mStatus = STATUS.SIG;
            leer = true;

            Ultimo = Simbolos.get(Simbolos.size() - 1);
            //Imprimir();
            //System.out.println("Cadena -> STATUS : " + mStatus.toString());
            //System.out.println((char)letra);


            switch (mStatus)
            {

                case NS:
                case ES:
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
                            Ultimo.equis = false;
                            mStatus = STATUS.SIG;
                            break;
                        case 'x':
                        case 'X':
                            Ultimo.equis = true;
                            mStatus = STATUS.values()[mStatus.ordinal() + 1];
                            break;
                    }
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
                    leer = false;
                    if(letra != -1)
                    {
                        Simbolos.add(new Simbolo());
                        mStatus = STATUS.NS;
                    }

                    break;
            }
            Ultimo.Corregir();
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
        }System.out.println();
    }

    public String IntegrarParalelo(String intervalos)
    {

        BigDecimal R = new BigDecimal("0");
        //Ejecutar cadena cad forma : ini fin inter
        String[] cadena = intervalos.split(" ");
        BigDecimal A = new BigDecimal(cadena[0]) , B = new BigDecimal(cadena[1]), I;

        I = new BigDecimal(DEFAULT_INTERVALO);
        ////System.out.println("Usare intervalo : " + I.toString());

        //Dado que queremos usar N hilos , aplicaremos la integral de A a un Pm con intervalo I

        BigDecimal delta = B.subtract(A).divide(new BigDecimal(HILOS),Configuracion.ESCALA,RoundingMode.DOWN);
        if (A.subtract(B).abs().compareTo(delta) < 0)
            I = A.subtract(B).abs();


        HiloIntegrar[] HI = new HiloIntegrar[HILOS];
        BigDecimal[] Pmed = new BigDecimal[HILOS + 1];//Puntos medios A ... Pm1 ... Pm2 ...... B
        Pmed[0] = A; Pmed[HILOS] = B;

        long tiempo = new Date().getTime();
        ////System.out.println("Cadena -> dIntervalo : " + delta.toString());
        for (int i = 1; i < Pmed.length; i++) {
            Pmed[i] = Pmed[i - 1].add(delta);
            ////System.out.println("Cadena -> INTEGRO DE : " + Pmed[i - 1].toString() + " a " + Pmed[i].toString()) ;
            HI[i - 1] = new HiloIntegrar(Pmed[i - 1],Pmed[i],I);
            HI[i - 1].start();
        }
        ////System.out.println("Cadena -> EO EMPECE A TRABAJAR");

        for (HiloIntegrar hi: HI) {
            while (!hi.resuelto)
            {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 1; i < HILOS; i++) {
                R = R.add(HI[i].getmRes());
        }
        ////System.out.println("Cadena -> EO ESPERE");
        ////System.out.println("Cadena -> EO TENGO : " + R.toString());
        tiempo = new Date().getTime() - tiempo ;

        //Tiempos[Contador++] = tiempo;
        PunAct += tiempo/Vueltas;
        if(Contador == Vueltas)
        {
            if (PunAnt != 0)
                PunAnt = PunAct;

            if (PunAct < PunAnt)   //Antes era mas lento : Vamos mejorando, mejoremos más
                HILOS ++;
            if (PunAct > PunAnt && HILOS > 1) // Ahora es mas lento : Mejoramos suficiente
                HILOS --;
            Contador = 0;
        }


        //System.out.println("Cadena -> Esta ronda se demoro " + tiempo + "ms para responder " + R.toString());
        //System.out.println("Cadena -> Para la siguiente usaremos " + HILOS + " hilos");

        return R.toString();
    }

    public BigDecimal Integrar(String msj)
    {

        String[] spliteado = msj.split(" ");
        BigDecimal A = new BigDecimal(spliteado[0]),B = new BigDecimal(spliteado[1]);
        return Integrar(A,B,new BigDecimal(DEFAULT_INTERVALO));
    }

    public BigDecimal Integrar(BigDecimal A,BigDecimal B,BigDecimal I)
    {

        BigDecimal R = new BigDecimal("0");
        if (I.compareTo(A.subtract(B).abs()) > 0)
            I = A.subtract(B).abs();
        if (A.compareTo(B) > 0)
        {
            I = I.negate();
        }
        //System.out.println(" Integro en " + A.toString() + " a " + B.toString() + " de " + I.toString());
        while (A.compareTo(B) < 0)
        {
            //System.out.println("Agregando...");
            BigDecimal delta = EvaluarTodo(A);
            delta = delta.multiply(I);
            R = R.add(delta);
            A = A.add(I);

        }


        return R;
    }

    private BigDecimal EvaluarTodo(BigDecimal X)
    {
        BigDecimal R = new BigDecimal("0");
        for (Simbolo mS: Simbolos) {

            R = R.add(mS.Evaluar(X));
        }//System.out.println("un evaluado...");
        return R;
    }

    private class HiloIntegrar extends Thread
    {
        public boolean resuelto = false;
        private BigDecimal mRes;
        private final BigDecimal mA, mB , mI;
        public HiloIntegrar (BigDecimal A , BigDecimal B , BigDecimal I)
        {
            mA = A; mB = B; mI = I;
        }

        public BigDecimal getmRes() {
            return mRes;
        }

        @Override
        public void run() {
            resuelto = false;
            ////System.out.println("Cadena -> HAGO RUN RUN con " + mA.toString() + " : " + mB.toString()  + " : " + mI.toString() );
            ////System.out.println("Cadena -> SALIDA :  " +
                    //Integrar(mA,mB,mI).toString());;
            mRes = Integrar(mA,mB,mI);
            resuelto = true;
        }
    }


    private class Simbolo
    {

        public BigDecimal ValCoc,ValExp;
        private boolean equis = false;
        private boolean positivo = true;
        private Simbolo(){}

        private void Corregir()
        {
            if(mStatus == STATUS.X)
            {
                if (ValCoc == null)
                    ValCoc = positivo?new BigDecimal("1"):new BigDecimal("-1");
            }
            else if(mStatus == STATUS.SIG)
            {
                if (ValExp == null)
                    ValExp = equis?new BigDecimal("1"):new BigDecimal("0");
            }
        }
        public void setPositivo(boolean positivo) {
            this.positivo = positivo;
            ////System.out.println("Cadena -> Seteado : " + positivo);
        }
        public void addVal(int c)
        {
            ////System.out.println("Cadena -> Agregando..." + (char) c);
            if (mStatus == STATUS.NUMERO)
            {
                String anterior = ValCoc != null?ValCoc.toString() : "";
                ValCoc = new BigDecimal(
                            ( anterior + (char)c) );
                if (!positivo && ValCoc.signum() == 1) {
                    ////System.out.println("Cadena -> Antiguo : " + ValCoc.toString());
                    ValCoc = ValCoc.negate();
                    ////System.out.println("Cadena -> Nuevo : " + ValCoc.toString());
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
            ////System.out.println("intalue " + X.toString() + " pow  " + ValExp.toBigInteger().intValue());
            BigDecimal D = X.pow(ValExp.toBigInteger().intValue(), new MathContext(Configuracion.ESCALA)).multiply(ValCoc);
            ////System.out.println("sale : " + D.toString());
            return D;
        }


        public String Codificar()
        {
            return ( (ValCoc==null?'?':ValCoc.toString()) + "X^" + (ValExp==null?'?':ValExp.toString()) );
        }

    }
}
