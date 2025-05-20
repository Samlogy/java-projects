package com.example.product.repositories;

import com.example.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
     @Query("SELECT p FROM Product p " +
             "WHERE (p.name LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) " +
             "AND (p.price >= :priceMin OR :priceMin IS NULL) " +
             "AND (p.price <= :priceMax or :priceMax IS NULL) " +
             "AND (p.category = :category) OR :category IS NULL")
     Page<Product> findAllByFilters(
             @Param("name") String name,
             @Param("priceMin") Float priceMin,
             @Param("priceMax") Float priceMax,
             @Param("category") String category,
             Pageable pageable
     );
}