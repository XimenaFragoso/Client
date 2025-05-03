/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProgromacionNCapasXimena.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam; 


@Controller
@RequestMapping("/demo")
public class Controller1 {
    
    @GetMapping("HolaMundo")
    public String HolaMundo () { 
        return "HolaMundo";
    }  
    
    @GetMapping("Formulario")
    public String Formulario () { 
        return "Formulario";
    }  
}
