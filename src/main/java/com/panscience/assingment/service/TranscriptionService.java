package com.panscience.assingment.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TranscriptionService {

    public String transcribe(File audioFile) {
        return "transcribed text (mock for now)";
    }
}