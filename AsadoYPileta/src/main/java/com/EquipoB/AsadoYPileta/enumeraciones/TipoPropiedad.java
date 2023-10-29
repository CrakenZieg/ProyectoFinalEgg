
package com.EquipoB.AsadoYPileta.enumeraciones;

public enum TipoPropiedad {
    
    QUINCHO("Quincho"),
    JARDIN("Jardin"),
    CASAFINSEMANA("Casa de fin de semana"),
    QUINTA("Quinta"),
    CASAPILETA("Casa con pileta"),
    CHACRA("Chacra"),
    ESTANCIA("Estancia"),
    FOGON("Fogón"),
    CASAPLAYA("Casa en la playa");
    
    private String tipo;

    private TipoPropiedad(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return tipo;
    }
    
}
