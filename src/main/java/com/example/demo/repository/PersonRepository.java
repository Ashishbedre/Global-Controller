package com.example.demo.repository;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT a FROM Person a WHERE a.site.id = :siteId")
    Person findBySiteId(@Param("siteId") Long siteId);
}
