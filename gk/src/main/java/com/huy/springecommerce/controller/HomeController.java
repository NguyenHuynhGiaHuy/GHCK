package com.huy.springecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.huy.springecommerce.model.Category;
import com.huy.springecommerce.service.CategoryService;

@Controller
@RequestMapping({"", "/"})
public class HomeController {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("user/index");
        List<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

}
