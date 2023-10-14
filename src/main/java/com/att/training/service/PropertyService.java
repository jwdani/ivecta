package com.att.training.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.training.exception.BadResourceException;
import com.att.training.exception.ResourceAlreadyExistsException;
import com.att.training.exception.ResourceNotFoundException;
import com.att.training.model.Property;
import com.att.training.repository.PropertyRepository;

@Service
public class PropertyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyService.class);
	
	@Autowired
	private PropertyRepository propertyRepository;	
	
	private boolean existsById(Long id) {
		
        return propertyRepository.existsById(id);
    }
	
	private boolean existsByName(String name) {
		
		if(propertyRepository.getPropertyByName(name) != null)
			return true;
		
        return false;
    }
    
    public Property findById(Long id) throws ResourceNotFoundException {
    	
        Property property = propertyRepository.findById(id).orElse(null);
        
        if (property==null) {
            throw new ResourceNotFoundException("Cannot find Property with id: " + id);
        }
        else return property;
    }
    
    public Property findByName(String name) throws ResourceNotFoundException {
    	
        Property property = propertyRepository.getPropertyByName(name);
        
        if (property==null) {
            throw new ResourceNotFoundException("Cannot find Property with name: " + name);
        }
        else return property;
    }
    
    public List<Property> findAll() {
        
        return propertyRepository.findAll();
    }
      
    public Property save(Property property) throws BadResourceException, ResourceAlreadyExistsException {
    	
        if (!StringUtils.isEmpty(property.getName())) {
        	
        	if (property.getName() != null && existsByName(property.getName())) { 
            	
                throw new ResourceAlreadyExistsException("Property with name: " + property.getName() + " already exists");
            }
        	else if (property.getId() != null && existsById(property.getId())) { 
            	
                throw new ResourceAlreadyExistsException("Property with id: " + property.getId() + " already exists");
            }
            
            return propertyRepository.save(property);
        }
        else {
        	
            BadResourceException badResourceException = new BadResourceException("Failed to save property");
            badResourceException.addErrorMessage("Property is null or empty");            
            throw badResourceException;
        }
    }
    
    public Property update(Property property) throws BadResourceException, ResourceNotFoundException {
    	
        if (!StringUtils.isEmpty(property.getName())) {
        	
            if (!existsById(property.getId())) {
                throw new ResourceNotFoundException("Cannot find Property with id: " + property.getId());
            }
            
            return propertyRepository.save(property);
        }
        else {
        	
            BadResourceException badResourceException = new BadResourceException("Failed to save property");
            badResourceException.addErrorMessage("Property is null or empty");            
            throw badResourceException;
        }
    }
    
    public void delete(Property property) throws ResourceNotFoundException {
    	
        if (!existsById(property.getId())) 
            throw new ResourceNotFoundException("Cannot find property with id: " + property.getId());        
        else 
            propertyRepository.delete(property);        
    } 
	
	public void deleteById(Long id) throws ResourceNotFoundException {
    	
        if (!existsById(id)) 
            throw new ResourceNotFoundException("Cannot find property with id: " + id);        
        else 
            propertyRepository.deleteById(id);        
    }    

	public Long count() {
    	
        return propertyRepository.count();
    }
}

