package com.att.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.att.training.model.Property;


@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	
	@Query("SELECT p.name FROM Property p")
    public List<String> getPropertyList();
	
	@Query("SELECT p FROM Property p WHERE p.name = :name")
    public Property getPropertyByName(@Param("name") String name);
}
