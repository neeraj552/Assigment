package com.panscience.assingment.model;

import jakarta.persistence.*;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double startTime;
    private double endTime;

    @Column(length = 2000)
    private String text;
}