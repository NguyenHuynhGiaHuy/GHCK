package com.huy.springecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huy.springecommerce.model.Category;
import com.huy.springecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    public List<Product> findAllByActive(boolean active);

    public List<Product> findAllByCategoryAndActive(Category category, boolean active);
    public List<Product> findAllByBrandAndActive(String brand, boolean active);
    public List<Product> findAllByColorAndActive(String color, boolean active);
}
