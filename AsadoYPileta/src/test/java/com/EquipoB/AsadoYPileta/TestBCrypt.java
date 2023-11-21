
package com.EquipoB.AsadoYPileta;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    
    public static void main(String[] args) {
        
        /* Ingresa tu contrasena aquí */
        String contrasena = "coco";
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("Contrasena: "+encoder.encode(contrasena));
        
    }
    
}
