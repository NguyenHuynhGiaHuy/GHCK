package com.huy.springecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.huy.springecommerce.dto.ProductDTO;
import com.huy.springecommerce.dto.Response;
import com.huy.springecommerce.model.Category;
import com.huy.springecommerce.model.Product;
import com.huy.springecommerce.service.CategoryService;
import com.huy.springecommerce.service.ProductService;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Product> products = productService.findAll();
        List<ProductDTO> data = new ArrayList<>();

        for (Product p : products) {
            Long categoryId = null;
            String categoryName = null;

            Category category = p.getCategory();
            if (category != null) {
                categoryId = category.getId();
                categoryName = category.getName();
            }

            ProductDTO productDTO = new ProductDTO(p.getId(), p.getName(), p.getImage(), p.getColor(), p.getBrand(), p.getPrice(), categoryId, categoryName);
            data.add(productDTO);
        }

        return ResponseEntity.ok(new Response(0, "Get list product successful", data));
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<?> getAllByCategory(@PathVariable("id") Long id) {
        Category category = categoryService.findCategory(id);
        List<Product> products = productService.findAllByCategoryAndActive(category);
        List<ProductDTO> data = new ArrayList<>();

        for (Product p : products) {
            Long categoryId = null;
            String categoryName = null;

            Category productCategory = p.getCategory();
            if (productCategory != null) {
                categoryId = productCategory.getId();
                categoryName = productCategory.getName();
            }

            ProductDTO productDTO = new ProductDTO(p.getId(), p.getName(), p.getImage(), p.getColor(), p.getBrand(), p.getPrice(), categoryId, categoryName);
            data.add(productDTO);
        }

        return ResponseEntity.ok(new Response(0, "Get list product successful", data));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> getAllByBrand(@PathVariable("brand") String brand) {
        List<Product> products = productService.findAllByBrandAndActive(brand);
        List<ProductDTO> data = new ArrayList<>();

        for (Product p : products) {
            Long categoryId = null;
            String categoryName = null;

            Category productCategory = p.getCategory();
            if (productCategory != null) {
                categoryId = productCategory.getId();
                categoryName = productCategory.getName();
            }

            ProductDTO productDTO = new ProductDTO(p.getId(), p.getName(), p.getImage(), p.getColor(), p.getBrand(), p.getPrice(), categoryId, categoryName);
            data.add(productDTO);
        }

        return ResponseEntity.ok(new Response(0, "Get list product successful", data));
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> getAllByColor(@PathVariable("color") String color) {
        List<Product> products = productService.findAllByColorAndActive(color);
        List<ProductDTO> data = new ArrayList<>();

        for (Product p : products) {
            Long categoryId = null;
            String categoryName = null;

            Category productCategory = p.getCategory();
            if (productCategory != null) {
                categoryId = productCategory.getId();
                categoryName = productCategory.getName();
            }

            ProductDTO productDTO = new ProductDTO(p.getId(), p.getName(), p.getImage(), p.getColor(), p.getBrand(), p.getPrice(), categoryId, categoryName);
            data.add(productDTO);
        }

        return ResponseEntity.ok(new Response(0, "Get list product successful", data));
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        Product product = productService.findProductById(id);
        ModelAndView modelAndView = new ModelAndView("user/product-detail");
        modelAndView.addObject("product", product);
        return modelAndView;
  
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("brand") String brand,
            @RequestParam("color") String color,
            @RequestParam("idCategory") Long idCategory,
            @RequestParam("image") MultipartFile image
    ) {
        Product p = new Product();
        Category category = categoryService.findCategory(idCategory);
        p.setName(name);
        p.setPrice(price);
        p.setBrand(brand);
        p.setColor(color);
        p.setActive(true);
        p.setCategory(category);

        if(productService.uploadImage(image)) {
            p.setImage(image.getOriginalFilename());
            productService.add(p);

            return ResponseEntity.ok(new Response(0, "Successfully to add product", ""));
        }

        return ResponseEntity.ok(new Response(1, "Failed to add product", ""));

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @RequestBody Product product) {
        Product foundProduct = productService.findProductById(id);

        if(foundProduct == null) {
            return ResponseEntity.ok(new Response(0, "Product with id = " + id + " does not exist", ""));
        }

        foundProduct.setBrand(product.getBrand());
        foundProduct.setName(product.getName());
        foundProduct.setColor(product.getColor());
        foundProduct.setPrice(product.getPrice());

        productService.update(foundProduct);
        return ResponseEntity.ok(new Response(0, "Successfully to update product", ""));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        Product foundProduct = productService.findProductById(id);

        productService.delete(foundProduct);

        return new ModelAndView("redirect:/admin/product");
    }

    
}
