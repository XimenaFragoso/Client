package com.example.ProgromacionNCapasXimena.ML;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Colonia {
    
    private int IdColonia; 
    private String Nombre; 
    private String CodigoPostal; 
    public Municipio Municipio; 

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }
    
    public String getNombre () { 
        return Nombre;
    }
    
    public void setNombre (String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getCodigoPostal () {
        return CodigoPostal; 
    }
    
    public void setCodigoPostal (String CodigoPostal) { 
        this.CodigoPostal = CodigoPostal;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
}
