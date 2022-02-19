package com.web.imc.service;

import com.web.imc.model.Pessoa;
import com.web.imc.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repository;

    @Transactional
    public void save(Pessoa ps){
        repository.save(ps);
    }

    @Transactional
    public void delete(Pessoa ps){
        repository.delete(ps);
    }
}
