package com.EquipoB.AsadoYPileta.controladores;


import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.xml.ws.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

@RestController
public class RestControlador {

    @RequestMapping("/hello")
    public String hello() {

        return "Hello World";
    }
    
    @GetMapping(value = "/GetHelloClient")
    public String getHelloClient(){
    
        String uri = "http://localhost:8070/hello";
        
        RestTemplate restTemplate = new RestTemplate();
        
        String result = restTemplate.getForObject(uri, String.class);
        
        return result;
    }
    
    @GetMapping(value = "/paises")
    public List<Object> verPaises(){
    
        String uri = "https://restcountries.eu/rest/v2/all";
        
        RestTemplate restTemplate = new RestTemplate();
        
        Object[] paises = restTemplate.getForObject(uri, Object[].class);
        
        return Arrays.asList(paises);
    }
    
    @GetMapping(value = "/paises2")
    public List<Object> verPaises2(){
    
        HttpHeaders headers = new HttpHeaders();
        
        headers.set("X-RapidAPI-Key", "cf086a7032mshb0b00a743026eafp1f49d4jsn6bde0abfbf55");
        headers.set("X-RapidAPI-Host", "referential.p.rapidapi.com");
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<List<Object>> response = restTemplate.exchange("https://"
                 + "referential.p.rapidapi.com/v1/country?fields=currency%"
                 + "2Ccurrency_num_code%2Ccurrency_code%2Ccontinent_code%"
                 + "2Ccurrency%2Ciso_a3%2Cdial_code&continent_code=sa&limit=250",
        HttpMethod.GET,entity, new ParameterizedTypeReference<List<Object>>() {});
        
        List<Object> paises = response.getBody();
         
        return paises;
    }
}
