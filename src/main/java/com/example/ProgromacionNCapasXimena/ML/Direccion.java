package com.example.ProgromacionNCapasXimena.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Direccion {
    private int IdDireccion; 
    
//    @NotNull(message = "El campo Calle no puede ser NULL")
//    @Size(min = 4, max = 50, message = "El rango de caracteres es de 04 a 50")
    private String Calle; 

//    @Size(min = 4, max = 20, message = "El rango de caracteres es de 04 a 20")
    private String NumeroInterior; 
    
//    @NotNull(message = "El campo NumeroExterior no puede ser NULL")
//    @Size(min = 4, max = 50, message = "El rango de caracteres es de 04 a 20")
    private String NumeroExterior; 
    
    @Valid
    public Colonia Colonia; 
//    public Usuario Usuario; 
    
    public int getIdDireccion (){
        return IdDireccion;
    }
    
    public void setIdDireccion (int IdDireccion) { 
        this.IdDireccion = IdDireccion; 
    }
    
    public String getCalle (){ 
        return Calle; 
    }
    
    public void setCalle (String Calle) { 
        this.Calle = Calle; 
    }
    
    public String getNumeroInterior (){
        return NumeroInterior;
    }
    
    public void setNumeroInterior (String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }
    
     public String getNumeroExterior (){
        return NumeroExterior;
    }
    
    public void setNumeroExterior (String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }  

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
    
}
