package com.huy.springecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huy.springecommerce.model.Order;
import com.huy.springecommerce.model.OrderProduct;
import com.huy.springecommerce.model.Product;
import com.huy.springecommerce.repository.OrderProductRepository;

@Service
public class OrderProductService {
    
    @Autowired
    private OrderProductRepository repo;

    public List<OrderProduct> findAll() {
        return repo.findAll();
    }

    public List<OrderProduct> findAllByProduct(Product product) {
        return repo.findAllByProduct(product);
    }

    public OrderProduct findOrderProduct(Product product, Order order) {
        return repo.findByProductAndOrder(product, order);
    }

    public void update(OrderProduct orderProduct) {
        repo.save(orderProduct);
    }

    public void add(OrderProduct orderProduct) {
        repo.save(orderProduct);
    }

   
}
