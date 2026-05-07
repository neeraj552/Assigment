package com.panscience.assingment.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String question;
    private Long fileId;
}