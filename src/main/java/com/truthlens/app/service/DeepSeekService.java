package com.truthlens.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;

@Service
public class DeepSeekService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.base}")
    private String apiBase;

    public DeepSeekService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("HTTP-Referer", "http://localhost:8080")
                .defaultHeader("X-Title", "TruthLens")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Mono<String> analyzeContent(String content) {
        String prompt = createAnalysisPrompt(content);

        Map<String, Object> requestBody = Map.of(
                "model", "deepseek/deepseek-r1:free",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 1000,//
                "temperature", 0.3//
        );

        return webClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("API Error: " + response.statusCode() + " - " + body);
                                return Mono.error(new RuntimeException("API returned " + response.statusCode() + ": " + body));
                            });
                })
                .bodyToMono(String.class)
                .map(this::extractContentFromResponse)
                .onErrorResume(e -> {
                    System.err.println("Error in DeepSeekService: " + e.getMessage());
                    return Mono.just("Error: " + e.getMessage());
                });
    }

    private String createAnalysisPrompt(String content) {
        return String.format("""
            Please analyze the following content for misinformation, bias, and credibility:
            
            Content: "%s"
            
            Provide your analysis in the following JSON format:
            {
                "credibilityScore": 0.0-1.0,
                "overallAssessment": "brief assessment",
                "redFlags": ["flag1", "flag2"],
                "positiveIndicators": ["indicator1", "indicator2"],
                "biasAnalysis": "analysis of potential bias",
                "factCheckSummary": "summary of fact-checking findings"
            }
            
            Consider:
            - Emotional language and sensationalism
            - Lack of credible sources
            - Logical fallacies
            - Confirmation bias indicators
            - Factual accuracy
            - Context and nuance
            """, content);
    }

    private String extractContentFromResponse(String response) {
        try {
            System.out.println("Raw API Response: " + response);

            JsonNode jsonNode = objectMapper.readTree(response);

            if (!jsonNode.has("choices") || jsonNode.get("choices").isEmpty()) {
                return "Error: No choices in API response";
            }

            JsonNode messageNode = jsonNode.path("choices").get(0).path("message");
            if (messageNode.isMissingNode() || !messageNode.has("content")) {
                return "Error: No message content in API response";
            }

            String content = messageNode.path("content").asText();

            // Clean up markdown code blocks if present
            if (content.startsWith("```json")) {
                content = content.substring(7, content.length() - 3).trim();
            } else if (content.startsWith("```")) {
                content = content.substring(3, content.length() - 3).trim();
            }

            return content;
        } catch (Exception e) {
            System.err.println("Error parsing AI response: " + e.getMessage());
            return "Error parsing AI response. Raw response: " + response;
        }
    }
}