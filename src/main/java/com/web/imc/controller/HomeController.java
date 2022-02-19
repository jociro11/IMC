package com.web.imc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    static final String INDEX = "index.xhtml";
    static final String CALCIMC = "calcImc.xhtml";
    static final String HISTORICO = "historico.xhtml";

    @GetMapping
    public String init(){
        return INDEX;
    }

    @RequestMapping("/calcularImc")
    public String calcImc(){
        return CALCIMC;
    }

    @RequestMapping("/historico")
    public String historico(){
        return HISTORICO;
    }
}
