package com.panscience.assingment.service;

import com.panscience.assingment.model.Chunk;
import com.panscience.assingment.repository.ChunkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChunkRepository chunkRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private String apiKey;

    public ChatService(ChunkRepository chunkRepository) {
        this.chunkRepository = chunkRepository;
    }

    public Map<String, Object> askQuestion(Long fileId, String question) {

        List<Chunk> chunks = chunkRepository.findByFileId(fileId);

        String context = chunks.stream()
                .limit(5)
                .map(Chunk::getContent)
                .collect(Collectors.joining(" "));
        String cleanedContext = context
                .replace("\r\n", " ")
                .replace("\n", " ")
                .trim();

        String answer;

        try {
            if (apiKey != null && !apiKey.isEmpty()) {

                String requestBody = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {
                      "role": "system",
                      "content": "Answer only from the provided context."
                    },
                    {
                      "role": "user",
                      "content": "Context:\\n%s\\n\\nQuestion:\\n%s"
                    }
                  ],
                  "max_tokens": 150
                }
                """.formatted(cleanedContext, question);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                HttpEntity<String> entity =
                        new HttpEntity<>(requestBody, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        "https://api.openai.com/v1/chat/completions",
                        HttpMethod.POST,
                        entity,
                        String.class
                );

                answer = response.getBody();

            } else {

                answer = chunks.stream()
                        .map(Chunk::getContent)
                        .filter(content ->
                                content.toLowerCase()
                                        .contains(question.toLowerCase()))
                        .findFirst()
                        .orElse(cleanedContext);

                answer = answer
                        .replace("\r\n", " ")
                        .replace("\n", " ")
                        .trim();

                answer = answer.substring(
                        0,
                        Math.min(400, answer.length())
                );
            }

        } catch (Exception e) {

            answer = cleanedContext.substring(
                    0,
                    Math.min(400, cleanedContext.length())
            );
        }

        Map<String, Object> result = new HashMap<>();

        result.put("answer", answer);
        result.put("start", 0);
        result.put("end", 30);

        return result;
    }
}