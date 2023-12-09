package com.EquipoB.AsadoYPileta.controladores;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestControlador {
    
    @GetMapping(value = "/provincias2")
    public List<Object> verProvincias2() throws NoSuchFieldException{

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Accept", "*/*");
        headers.set("User-Agent", "PostmanRuntime/7.28.4");
        headers.set("Connection", "keep-alive");
        headers.set("Cache-Control", "no-cache");
        headers.set("Content-Type","application/json");
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate.exchange("https://apis.datos.gob.ar/georef/api/provincias",
        HttpMethod.GET,entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {});
         
         Set<String> keySet = response.getBody().keySet();
         
         String[] arrayKey = keySet.toArray(new String[keySet.size()]);
         
         Object provincias = response.getBody().get(arrayKey[3]);
         
        return (List<Object>) provincias;
    }
    
    @GetMapping(value = "/ciudades")
    public List<Object> verCiudades(@RequestParam String provincia, @RequestParam String orden,
            @RequestParam String max, @RequestParam String exacto) throws NoSuchFieldException{
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Accept", "*/*");
        headers.set("User-Agent", "PostmanRuntime/7.28.6");
        headers.set("Connection", "keep-alive");
        headers.set("Cache-Control", "no-cache");
        headers.set("Content-Type","application/json");
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<LinkedHashMap<String, Object>> response = restTemplate.exchange(
        "https://apis.datos.gob.ar/georef/api/departamentos?provincia="+provincia+"&orden="+orden+"&max="+max+"&exacto="+exacto+"",
        HttpMethod.GET,entity, new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {});
         
        Set<String> keySet = response.getBody().keySet();
         
        String[] arrayKey = keySet.toArray(new String[keySet.size()]);
         
        Object ciudades = response.getBody().get(arrayKey[1]);
         
        return (List<Object>)ciudades;
    }
    
}
