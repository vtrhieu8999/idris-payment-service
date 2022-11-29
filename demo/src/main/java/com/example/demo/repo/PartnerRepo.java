package com.example.demo.repo;

import com.example.demo.model.Partner;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepo extends JpaRepository<Partner, Integer> {
    Option<Partner> findByPartnerCode(String partnerCode);
}
