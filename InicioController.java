package com.alba_coffi.alba_coffi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/index.html";
    }

    
    @GetMapping("/saludo")
    @ResponseBody
    public String saludo() {
        return "¡Bienvenido al back-end de AlbaCoffi!";
    }

    
    @GetMapping("/api/estado")
    @ResponseBody
    public String estadoServicio() {
        return "El servidor de AlbaCoffi está activo y respondiendo correctamente.";
    }
}