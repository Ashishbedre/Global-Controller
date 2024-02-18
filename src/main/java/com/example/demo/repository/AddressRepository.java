package com.example.demo.repository;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.SiteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.site.id = :siteId")
    Address findBySiteId(@Param("siteId") Long siteId);
}
