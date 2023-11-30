package com.huy.springecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huy.springecommerce.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByActive(boolean active);
}
