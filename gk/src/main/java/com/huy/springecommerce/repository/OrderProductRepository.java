package com.huy.springecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huy.springecommerce.model.Order;
import com.huy.springecommerce.model.OrderProduct;
import com.huy.springecommerce.model.Product;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    
    public List<OrderProduct> findAllByProduct(Product product);

    public OrderProduct findByProductAndOrder(Product product, Order order);
    
}
