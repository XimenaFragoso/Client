package com.example.ProgromacionNCapasXimena.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class Usuario {
    private int IdUsuario;
    
    //Notaciones de Validacion
//    @NotNull(message = "El campo nombre no puede ser NULL")
//    @Size(min = 4, max = 30, message = "El rango de caracteres es de 04 a 30")
    private String Nombre;
    
//    @NotNull(message = "El campo ApellidoPaterno no puede ser NULL")
//    @Size(min = 4, max = 30, message = "El rango de caracteres es de 04 a 30")
    private String ApellidoPaterno;
        
//    @Size(min = 4, max = 30, message = "El rango de caracteres es de 04 a 30")
    private String ApellidoMaterno;
    
//    @NotNull(message = "El campo FechaNacimiento no puede ser NULL")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;

//    @NotNull(message = "El campo UserName no puede ser NULL")
//    @Size(min = 4, max = 50, message = "El rango de caracteres es de 04 a 50")
    private String UserName; 
    
//    @Email(message = "user.email.mask")
//    @NotNull(message = "El campo Email no puede ser NULL")
    private String Email; 
    
//    @Size(min = 8, max = 10, message = "El rango min de caracteres es de 8 y el max de 10")
    private String Password;
    
//    @NotNull(message = "El campo sexo no puede ser NULL")
//    @Size(min = 1,max =1, message = "Solo puedes ingresar una 'M' O 'F'")
    private String Sexo;
    
//    @NotNull(message = "El campo telefono no puede ser NULL")
//    @Size(min = 10, max = 12, message = "El rango min de numeros es de 10 y el max de 12")
    private String Telefono; 
    
//    @NotNull(message = "El campo celular no puede ser NULL")        
//    @Size(min = 10, max = 12, message = "El rango min de numeros es de 10 y el max de 12")
    private String Celular; 

    private String CURP;
    
    private String Imagen;
    
    private int Status; 
    
    @Valid
    public Roll Roll; 
    
    public Usuario(){       
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public Roll getRoll() {
        return Roll;
    }

    public void setRoll(Roll Roll) {
        this.Roll = Roll;
    }
    
    public String getImagen(){
        return Imagen; 
    }
    
    public void setImagen(String Imagen){
        this.Imagen = Imagen; 
    }
    
    public int getStatus(){
        return Status; 
    }
    
    public void setStatus(int Status){
        this.Status = Status;
    }
    
}



