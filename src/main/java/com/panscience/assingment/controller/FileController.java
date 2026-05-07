package com.panscience.assingment.controller;

import com.panscience.assingment.dto.ChatRequest;
import com.panscience.assingment.model.Chunk;
import com.panscience.assingment.model.FileEntity;
import com.panscience.assingment.repository.FileRepository;
import com.panscience.assingment.repository.ChunkRepository;
import com.panscience.assingment.service.ChatService;
import com.panscience.assingment.service.PdfService;
import com.panscience.assingment.util.TextChunker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {

    private final ChatService chatService;
    private final FileRepository fileRepository;
    private final ChunkRepository chunkRepository;
    private final PdfService pdfService;

    public FileController(ChatService chatService,
                          FileRepository fileRepository,
                          ChunkRepository chunkRepository,
                          PdfService pdfService) {
        this.chatService = chatService;
        this.fileRepository = fileRepository;
        this.chunkRepository = chunkRepository;
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String uploadDir = "uploads/";
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(file.getBytes());
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setFilePath(filePath);
        fileEntity.setFileType(file.getContentType());

        fileRepository.save(fileEntity);

        if (file.getContentType() != null && file.getContentType().equals("application/pdf")) {

            String text = pdfService.extractText(filePath);

            List<String> chunks = TextChunker.chunkText(text);

            for (String chunkText : chunks) {
                Chunk chunk = new Chunk();
                chunk.setContent(chunkText);
                chunk.setFileId(fileEntity.getId());

                chunkRepository.save(chunk);
            }
        }

        return ResponseEntity.ok(fileEntity.getId());
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatRequest request) {

        return ResponseEntity.ok(
                chatService.askQuestion(
                        request.getFileId(),
                        request.getQuestion()
                )
        );
    }
}