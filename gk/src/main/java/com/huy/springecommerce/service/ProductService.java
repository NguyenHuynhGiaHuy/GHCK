package com.huy.springecommerce.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.huy.springecommerce.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huy.springecommerce.model.Category;
import com.huy.springecommerce.model.Product;
import com.huy.springecommerce.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repo;

    public List<Product> findAll() {
        return repo.findAllByActive(true);
    }

    public Product findProductById(Long id) {
        Optional<Product> product = repo.findById(id);

        if(product.isPresent()) {
            return product.get();
        }

        throw new ProductNotFoundException("Không tìm thấy sản phẩm với ID: " + id);
    }


    public void add(Product product) {
        try {
            repo.save(product);
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void update(Product product) {
        try {
            repo.save(product);
        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    public void delete(Product product) {
        try {
            if (product.getLstOrderProducts() == null || product.getLstOrderProducts().size() == 0) {
                repo.delete(product);
            } else {
                product.setActive(false);
                update(product);
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    public boolean uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            return false;
        }

        try {

            String destinationDir = "src/main/resources/static/images/"; // Đường dẫn thư mục cần di chuyển
            Path destinationPath = Paths.get(destinationDir + image.getOriginalFilename());
            Files.createDirectories(destinationPath.getParent()); // Tạo thư mục nếu chưa tồn tại
            Files.write(destinationPath, image.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Product> findAllByCategoryAndActive(Category category) {
        try {
            return repo.findAllByCategoryAndActive(category, true);
        } catch (Exception e) {
            // Xử lý hoặc ghi log cho lỗi trong quá trình truy vấn dữ liệu
            e.printStackTrace(); // Hoặc log lỗi vào hệ thống
            throw new RuntimeException("Đã xảy ra lỗi khi truy vấn dữ liệu theo danh mục và trạng thái kích hoạt");
        }
    }

    public List<Product> findAllByBrandAndActive(String brand) {
        try {
            return repo.findAllByBrandAndActive(brand, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi truy vấn dữ liệu theo thương hiệu và trạng thái kích hoạt");
        }
    }

    public List<Product> findAllByColorAndActive(String color) {
        try {
            return repo.findAllByColorAndActive(color, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi khi truy vấn dữ liệu theo màu sắc và trạng thái kích hoạt");
        }
    }

}
