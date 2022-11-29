package com.example.demo.repo;

import com.example.demo.model.product.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Optional<Company> findCompanyByTelcoCode(String telcoCode);
}
