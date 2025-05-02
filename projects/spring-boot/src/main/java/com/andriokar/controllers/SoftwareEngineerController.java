package com.andriokar.controllers;

import com.andriokar.entities.SoftwareEngineer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return List.of(
                new SoftwareEngineer(
                        1,
                        "Frodo Baggins",
                        "JS, Node, React, Tailwind"
                ),
                new SoftwareEngineer(
                        2,
                        "Samwise Gamgee",
                        "Java, Spring, Springboot"
                ),
                new SoftwareEngineer(
                        3,
                        "Peregrin Took",
                        "Java, Hibernate"
                ),
                new SoftwareEngineer(
                        4,
                        "Meriadoc Brandybuck",
                        "Java, JUnit"
                )
        );
    }
}
