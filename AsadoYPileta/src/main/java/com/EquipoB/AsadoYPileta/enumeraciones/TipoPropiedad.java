
package com.EquipoB.AsadoYPileta.enumeraciones;

public enum TipoPropiedad {
    
    QUINCHO("Quincho","127867"),
    JARDIN("Jardin","127804"),
    CASAFINSEMANA("Casa de fin de semana","127966"),
    QUINTA("Quinta","127969"),
    CASAPILETA("Casa con pileta","127946"),
    CHACRA("Chacra","127807"),
    ESTANCIA("Estancia","128004"),
    FOGON("Fogón","128293"),
    CASAPLAYA("Casa en la playa","127958");
    
    private String tipo;
    private String emoji;

    private TipoPropiedad(String tipo, String emoji) {
        this.tipo = tipo;
        this.emoji = emoji;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getEmoji(){
        return "&#"+emoji+";";
    }
    
}
