package com.example.ProgromacionNCapasXimena.ML;

import jakarta.validation.Valid;
import java.util.List;

public class UsuarioDireccion {
    
    @Valid
    public Usuario Usuario;
    public List<Direccion> Direcciones;
    public Direccion Direccion;

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public Direccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.Direccion = Direccion;
    }

}
