package com.att.training.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.training.constants.ControllerConstants;
import com.att.training.exception.BadResourceException;
import com.att.training.exception.ResourceAlreadyExistsException;
import com.att.training.exception.ResourceNotFoundException;
import com.att.training.model.Property;
import com.att.training.service.PropertyService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_MAPPTING_API)
@CrossOrigin(origins = "*")
public class PropertyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyController.class);
	
	@Autowired
	private PropertyService propertyService;
	
    @GetMapping(value = "/property", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<List<Property>> findAll() {
    
    	return ResponseEntity.ok(propertyService.findAll());        
    }
    
    @GetMapping(value = "/property/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Property> findById(@PathVariable long id) {
    	
        try {
            Property property = propertyService.findById(id);            
            return ResponseEntity.ok(property);
        } 
        catch (ResourceNotFoundException rnfex) {
        	
        	LOGGER.error(rnfex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping(value = "/property/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Property> findByName(@PathVariable String name) {
    	
        try {
            Property property = propertyService.findByName(name);            
            return ResponseEntity.ok(property);
        } 
        catch (ResourceNotFoundException rnfex) {
        	
        	LOGGER.error(rnfex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PostMapping(value = "/property")
    public ResponseEntity<Property> add(@RequestBody Property property) throws URISyntaxException {
    	
        try {
            
            Property newProperty = propertyService.save(property); 
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty); 
        } 
        catch (ResourceAlreadyExistsException raex) {
        	
        	LOGGER.error(raex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } 
        catch (BadResourceException brex) {
        	
        	LOGGER.error(brex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping(value = "/property")
    public ResponseEntity<Property> update(@RequestBody Property property) {
    	
        try {
        	
            Property newProperty = propertyService.update(property);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
        } 
        catch (ResourceNotFoundException rnfex) {
        	
        	LOGGER.error(rnfex.getMessage());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } 
        catch (BadResourceException brex) {
        	
        	LOGGER.error(brex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path = "/property")
    public ResponseEntity<Void> delete(@RequestBody Property property) {
    	
        try {
        	
        	propertyService.delete(property);
            return ResponseEntity.ok().build();
        } 
        catch (ResourceNotFoundException rnfex) {
        	
        	LOGGER.error(rnfex.getMessage());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping(path = "/property/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
    	
        try {
        	
        	propertyService.deleteById(id);
            return ResponseEntity.ok().build();
        } 
        catch (ResourceNotFoundException rnfex) {
        	
        	LOGGER.error(rnfex.getMessage());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
