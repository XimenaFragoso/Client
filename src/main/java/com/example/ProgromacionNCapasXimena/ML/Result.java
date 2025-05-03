package com.example.ProgromacionNCapasXimena.ML;

import java.util.List; 

public class Result <T>{
    
    public boolean correct; //se ejecta o noic String errorMessage;      
    public String errorMessage; //error de mensaje con su descripcion
    public Exception ex; //excepciones
    public T object;
    public List <T> objects; 
}
