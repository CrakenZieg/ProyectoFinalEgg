
package com.EquipoB.AsadoYPileta.enumeraciones;

import java.util.HashMap;
import java.util.Map;

public enum Meses {
    
    ENERO("Enero",0),
    FEBRERO("Febrero",1),
    MARZO("Marzo",2),
    ABRIL("Abril",3),
    MAYO("Mayo",4),
    JUNIO("Junio",5),
    JULIO("Julio",6),
    AGOSTO("Agosto",7),
    SEPTIEMBRE("Septiembre",8),
    OCTUBRE("Octubre",9),
    NOVIEMBRE("Noviembre",10),
    DICIEMBRE("Diciembre",11);
    
    private final String mes;
    private final int numMes;
    private static final Map<Integer,Meses> mapa;
    static{
        mapa = new HashMap<Integer,Meses>();
        for(Meses m: Meses.values()){
            mapa.put(m.numMes, m);
        }
    }
    
    Meses(String mes,int numMes){
        this.mes = mes;
        this.numMes = numMes;
    }
    
    public static Meses getMes(int num){
       return mapa.get(num);
    }

    public String getMes() {
        return mes;
    }
    public int getNumMes() {
        return numMes;
    }  
    
}
