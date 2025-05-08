package com.andriokar.controllers;

import com.andriokar.entities.SoftwareEngineer;
import com.andriokar.services.SoftwareEngineerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getSoftwareEngineers();
    }

    @GetMapping(path = "/{id}")
    public SoftwareEngineer getEngineerById(
            @PathVariable Integer id
    ) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    @PostMapping
    public SoftwareEngineer addNewSoftwareEngineer(
            @RequestBody SoftwareEngineer softwareEngineer
    ) {
        return softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }
}
