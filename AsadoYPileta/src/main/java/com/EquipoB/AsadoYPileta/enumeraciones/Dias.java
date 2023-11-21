
package com.EquipoB.AsadoYPileta.enumeraciones;

import java.util.HashMap;
import java.util.Map;

public enum Dias {
    
    LUNES("Lunes",1),
    MARTES("Martes",2),
    MIERCOLES("Miércoles",3),
    JUEVES("Jueves",4),
    VIERNES("Viernes",5),
    SABADO("Sábado",6),
    DOMINGO("Domingo",7);
    
    private final String dia;
    private final int numDia;
    private static final Map<Integer,Dias> mapa;
    static{
        mapa = new HashMap<Integer,Dias>();
        for(Dias d: Dias.values()){
            mapa.put(d.numDia, d);
        }
    }
    
    Dias(String dia, int numDia){
        this.dia = dia;
        this.numDia = numDia;
    }
    
    public static Dias getDia(int num){
       return mapa.get(num);
    }

    public String getDia() {
        return dia;
    }
    public int getNumDia() {
        return numDia;
    }  
    
}
