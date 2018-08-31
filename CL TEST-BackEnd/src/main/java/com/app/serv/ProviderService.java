/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.serv;

import com.app.mod.Provider;
import java.util.List;

public interface ProviderService {

    /**
     *
     * @return
     */
    List<Provider> findAll();
    
    
    /**
     *
     * @param uuid
     * @return
     */
    Provider findById(String uuid);
   
    /**
     *
     * @param user
     * @param isNew
     * @return
     */
    Provider saveOrUpdateProvider(Provider user);

    /**
     *
     * @param uuid
     */
    void deleteProvider(String uuid);
}
