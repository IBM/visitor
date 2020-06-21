package com.example.visitor.controller;

import com.example.visitor.dto.MuseumDTO;
import com.example.visitor.service.MuseumVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/visit")
public class VisitorController {

    @Autowired
    private MuseumVisitorService museumVisitorService;

    @GetMapping("/museum/{address}")
    public MuseumDTO getMuseumByAddress(@PathVariable String address) throws InterruptedException {
        return this.museumVisitorService.getMuseum(address);
    }
}
