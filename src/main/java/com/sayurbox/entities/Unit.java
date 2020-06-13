package com.sayurbox.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String abbreviation;
}