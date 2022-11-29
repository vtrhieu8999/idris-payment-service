package com.example.demo.repo;

import com.example.demo.model.product.TopUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopUpRepo extends JpaRepository<TopUp, Integer> {
}
