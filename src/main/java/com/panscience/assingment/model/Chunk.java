package com.panscience.assingment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    private Long fileId;
}