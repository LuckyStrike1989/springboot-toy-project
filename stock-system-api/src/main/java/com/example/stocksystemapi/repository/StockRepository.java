package com.example.stocksystemapi.repository;

import com.example.stocksystemapi.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
