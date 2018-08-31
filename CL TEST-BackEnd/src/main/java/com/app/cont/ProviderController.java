/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.cont;

import com.app.dto.ProviderDTO;
import com.app.dto.SpecialtyDTO;
import com.app.exp.ProviderNotFoundException;
import com.app.mod.Provider;
import com.app.mod.Specialty;
import com.app.serv.ProviderService;
import com.app.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
@Api(value = "Provider RestController", description = "CRUD PROVIDER")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    public ObjectMapper objectMapper = ObjectMapperUtil.getInstanceObjectMapper();

    public final static org.slf4j.Logger LOG = LoggerFactory.getLogger(ProviderController.class);

//START METHODS    ************************************************************

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save ProviderDTO", notes = "Return ResponseUtil")
    
    public Response SaveProv(@RequestBody @Valid ProviderDTO providerDTO) {
        try {
            Specialty specialty = objectMapper.convertValue(providerDTO.getSpecialtyDTO(), Specialty.class);
            Provider provider = objectMapper.convertValue(providerDTO, Provider.class);
            provider.setSpecialty(specialty);
            provider = providerService.saveOrUpdateProvider(provider);
            specialty = (Specialty) provider.getSpecialty();

            SpecialtyDTO specialtyDTO = objectMapper.convertValue(specialty, SpecialtyDTO.class);
            providerDTO = objectMapper.convertValue(provider, ProviderDTO.class);
            providerDTO.setSpecialtyDTO(specialtyDTO);
            return Response.ok().entity(new GenericEntity<ProviderDTO>(providerDTO) {
            }).build();
        } catch (Exception ex) {
            return Response.serverError().entity(providerDTO).build();
        }
    }

  //*************************************************************************** 
    
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update ProviderDTO")
    
    public Response UpdateProv(@RequestBody @Valid ProviderDTO providerDTO) {
        try {
            if (ValidProv(providerDTO.getId()) != null) {
                Specialty specialty = objectMapper.convertValue(providerDTO.getSpecialtyDTO(), Specialty.class);
                Provider provider = objectMapper.convertValue(providerDTO, Provider.class);
                provider.setSpecialty(specialty);
                provider = providerService.saveOrUpdateProvider(provider);
                specialty = (Specialty) provider.getSpecialty();

                SpecialtyDTO specialtyDTO = objectMapper.convertValue(specialty, SpecialtyDTO.class);
                providerDTO = objectMapper.convertValue(provider, ProviderDTO.class);
                providerDTO.setSpecialtyDTO(specialtyDTO);
                return Response.ok().entity(new GenericEntity<ProviderDTO>(providerDTO) {
                }).build();
            } else {
                throw new ProviderNotFoundException(providerDTO.getId());
            }
        } catch (Exception ex) {
            return Response.serverError().tag(ex.getMessage()).build();
        }
    }

    /**
     *
     * @param id
     * @return
     */
    
    //*************************************************************************
    
    @RequestMapping(method = RequestMethod.GET)
    public List<ProviderDTO> ListProv() {
        try {
            List<ProviderDTO> listProviderDTO = new ArrayList<>();
            for (Provider iterator : providerService.findAll()) {
                if ((Object) iterator.getSpecialty() instanceof String == false) {
                    SpecialtyDTO specialtyDTO = objectMapper.convertValue((Object) iterator.getSpecialty(), SpecialtyDTO.class);
                    ProviderDTO providerDTO = objectMapper.convertValue(iterator, ProviderDTO.class);
                    providerDTO.setSpecialtyDTO(specialtyDTO);
                    listProviderDTO.add(providerDTO);
                }
            }
            return listProviderDTO;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
    
    //*************************************************************************
    
    @RequestMapping(value= "/{id}" , method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete ProviderDTO")
    public Response DeleteProv(@PathVariable("id") String id) {
        try {
            if (ValidProv(id) != null) {
                providerService.deleteProvider(id);
                return Response.ok().build();
            } else {
                throw new ProviderNotFoundException(id);
            }
        } catch (Exception ex) {
            return Response.serverError().tag(ex.getMessage()).build();
        }
    }

    private Provider ValidProv(String idprov) {
        try {
                return providerService.findById(idprov);
        } catch (Exception ex) {
            return null;
        }
    }

}
