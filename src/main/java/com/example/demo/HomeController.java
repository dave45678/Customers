package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository repository;

    @RequestMapping("/")
    public String findByName(Model model, String lastName){
        model.addAttribute("customers" , repository.findByName(lastName));
        return "list";
    }

    @GetMapping("/add")
    public String customerForm(Model model)




}
