package com.web.imc.view;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.web.imc.model.Pessoa;
import com.web.imc.repository.Repository;
import com.web.imc.service.Service;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("view")
public class ImcMB {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private Double peso;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Double altura;

    @Getter
    @Setter
    private List<Pessoa> historico = new ArrayList<>();

    @Getter
    @Setter
    private Pessoa selectedHistory;

    @Autowired
    private Service service;

    @Autowired
    private Repository repository;

    @PostConstruct
    public void init() {
        historico = repository.findAll();
    }

    public void calcular(){
        double imc = peso / (altura*2);
        String format = df.format(imc).replace(',', '.');
        double formatted = Double.parseDouble(format);
        if(formatted>= 18.5 && formatted<=24.9){
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, nome+", O seu peso é normal, " +
                            "seu IMC é: "+formatted, ""));
            saveData(formatted);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados salvos na base de dados.", ""));
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, nome+", O seu peso é anormal, " +
                            "seu IMC é: "+formatted, ""));
            saveData(formatted);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados salvos na base de dados.", ""));

        }

    }

    public void saveData(double imc){
        try {
            Pessoa ps = new Pessoa();

            if(id!=null){
                ps.setId((id));
            }
            ps.setNome(nome);
            ps.setAltura(altura);
            ps.setPeso(peso);
            ps.setImc(imc);
            service.save(ps);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete() {

        try {
            if (selectedHistory != null) {
                service.delete(selectedHistory);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro eliminado com sucesso!", ""));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().getExternalContext().redirect("historico");
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione a linha antes de eliminar!", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao eliminar!", ""));
            e.printStackTrace();
        }
    }

    public void editar (){
        try {
            if (selectedHistory != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("calcularImc?id=" + selectedHistory.getId() + "&nome=" + selectedHistory.getNome()
                        + "&peso=" + selectedHistory.getPeso() + "&altura=" + selectedHistory.getAltura());
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione a linha antes de editar!", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao editar!", ""));
            e.printStackTrace();
        }
    }

}
