/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 Valentina Codutti Theran*/


package com.app.serv.impl;

import com.app.mod.Provider;
import com.app.mod.Specialty;
import com.app.repo.ProviderRepository;
import com.app.repo.SpecialtyRepository;
import com.app.serv.ProviderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;
    
    @Autowired
    SpecialtyRepository specialtyRepository;

  
    @Override
    public List<Provider> findAll() {
        Iterable<Provider> provider = providerRepository.findAll();
        return (List<Provider>) provider;
    }

    @Override
    public Provider saveOrUpdateProvider(Provider provider) {
        
            Specialty specialty = (Specialty)provider.getSpecialty();
            if (specialty != null) {
                if (specialty.getId() != null) {
                    Specialty specialtyAux = specialtyRepository.findOne(specialty.getId());
                    if (specialtyAux == null) {
                        specialty = specialtyRepository.save(specialty);
                    } else {
                        specialty = specialtyAux;
                    }
                } else {
                    specialty = specialtyRepository.save(specialty);
                }
                provider.setSpecialty(specialty);
            }        
        return providerRepository.save(provider);
    }

    @Override
    public void deleteProvider(String providerId) {
        providerRepository.delete(providerId);
    }

    @Override
    public Provider findById(String uuid) {
        return providerRepository.findOne(uuid);
    }
}
