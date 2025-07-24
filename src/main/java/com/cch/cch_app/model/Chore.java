package com.cch.cch_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name="chores")
@Getter
public class Chore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private LocalDate date;

    private String description;

    protected Chore() {}

    public Chore(Long id, String name, LocalDate date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }
}

