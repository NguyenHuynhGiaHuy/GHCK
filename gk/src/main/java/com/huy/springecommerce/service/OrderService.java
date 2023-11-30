package com.huy.springecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.huy.springecommerce.model.Order;
import com.huy.springecommerce.model.User;
import com.huy.springecommerce.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository repo;

    public List<Order> findAll() {
        return repo.findAllByActive(true);
    }

    public List<Order> findAllByUser(User user) {
        return repo.findAllByUserAndActive(user, true);
    }



    public void add(Order order) {
        try {
            // Kiểm tra điều kiện trước khi lưu
            if (order != null) {
                // Thêm Order vào cơ sở dữ liệu
                repo.save(order);
            } else {
                // Xử lý khi order là null
                // throw new YourCustomException("Order is null");
            }
        } catch (DataIntegrityViolationException e) {
            // Xử lý ngoại lệ khi vi phạm ràng buộc dữ liệu
        } catch (JpaSystemException e) {
            // Xử lý ngoại lệ khác của JPA
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
        }
    }

    public void update(Order order) {
        try {
            // Kiểm tra điều kiện trước khi cập nhật
            if (order != null) {
                // Cập nhật Order trong cơ sở dữ liệu
                repo.save(order);
            } else {
                // Xử lý khi order là null
                // throw new YourCustomException("Order is null");
            }
        } catch (DataIntegrityViolationException e) {
            // Xử lý ngoại lệ khi vi phạm ràng buộc dữ liệu
        } catch (JpaSystemException e) {
            // Xử lý ngoại lệ khác của JPA
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác
        }
    }


    public void delete(Order order) {
        if(order.getLstOrderProducts() == null || order.getLstOrderProducts().size() == 0
           
        ) {
            repo.delete(order);
        } else {
            order.setActive(false);
            update(order);
        }
    }

    public Order findFirstByOrderByIdDesc(User user) {
        return repo.findTopByUserOrderByIdDesc(user);
    }
}
