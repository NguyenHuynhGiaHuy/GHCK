package com.huy.springecommerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.huy.springecommerce.dto.Response;
import com.huy.springecommerce.dto.CategoryDTO;
import com.huy.springecommerce.model.Category;
import com.huy.springecommerce.service.CategoryService;

import java.util.ArrayList;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoriesRes = categories.stream()
                .map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ResponseEntity.ok(new Response(0, "Get list category successful", categoriesRes));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Category category) {
        category.setActive(true);
        categoryService.add(category);
        return ResponseEntity.ok(new Response(0, "Add category successful", ""));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @RequestBody Category category) {
        Category foundCategory = categoryService.findCategory(id);

        if(foundCategory == null) {
            return ResponseEntity.ok(new Response(1, "Category with id = " + id + " does not exist", ""));
        }

        foundCategory.setName(category.getName());

        categoryService.update(foundCategory);
        return ResponseEntity.ok(new Response(0, "Update category successful", ""));


    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Category foundCategory = categoryService.findCategory(id);

        if (foundCategory != null) {
            categoryService.delete(foundCategory);
        }

        return "redirect:/admin/category";
    }


}
