package com.example.ProgromacionNCapasXimena.ML;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Estado {
    private int IdEstado; 
    private String Nombre; 
    public Pais Pais; 

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais Pais) {
        this.Pais = Pais;
    }
    
    public int getIdEstado(){ 
        return IdEstado; 
    }
    
    public void setIdEstado(int IdEstado){
        this.IdEstado = IdEstado; 
    }
    
    public String getNombre(){
        return Nombre; 
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
}
