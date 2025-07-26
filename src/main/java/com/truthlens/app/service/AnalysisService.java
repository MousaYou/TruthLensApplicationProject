package com.truthlens.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truthlens.app.model.AnalysisRequest;
import com.truthlens.app.model.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AnalysisService {
    
    @Autowired
    private DeepSeekService deepSeekService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CompletableFuture<AnalysisResult> analyzeContent(AnalysisRequest request) {
        return deepSeekService.analyzeContent(request.getContent())
            .toFuture()
            .thenApply(aiResponse -> parseAIResponse(aiResponse, request));
    }

    private AnalysisResult parseAIResponse(String aiResponse, AnalysisRequest request) {
        AnalysisResult result = new AnalysisResult();
        result.setOriginalContent(request.getContent());
        result.setSource(request.getSource());
        result.setAiModel("deepseek/deepseek-r1:free");

        try {
            // Try to parse JSON response from AI
            JsonNode jsonNode = objectMapper.readTree(aiResponse);

            result.setCredibilityScore(jsonNode.path("credibilityScore").asDouble(0.5));
            result.setOverallAssessment(jsonNode.path("overallAssessment").asText("Analysis completed"));
            result.setBiasAnalysis(jsonNode.path("biasAnalysis").asText("No specific bias detected"));
            result.setFactCheckSummary(jsonNode.path("factCheckSummary").asText("Fact-checking completed"));

            // Parse arrays
            JsonNode redFlagsNode = jsonNode.path("redFlags");
            if (redFlagsNode.isArray()) {
                List<String> redFlags = Arrays.asList(objectMapper.convertValue(redFlagsNode, String[].class));
                result.setRedFlags(redFlags);
            } else {
                result.setRedFlags(Arrays.asList("Unable to parse red flags"));
            }

            JsonNode positiveNode = jsonNode.path("positiveIndicators");
            if (positiveNode.isArray()) {
                List<String> positiveIndicators = Arrays.asList(objectMapper.convertValue(positiveNode, String[].class));
                result.setPositiveIndicators(positiveIndicators);
            } else {
                result.setPositiveIndicators(Arrays.asList("Unable to parse positive indicators"));
            }

        } catch (Exception e) {
            // Fallback parsing if JSON parsing fails
            result = createFallbackResult(aiResponse, request);
        }

        return result;
    }
    
    private AnalysisResult createFallbackResult(String aiResponse, AnalysisRequest request) {
        AnalysisResult result = new AnalysisResult();
        result.setOriginalContent(request.getContent());
        result.setSource(request.getSource());
        result.setAiModel("DeepSeek-V3");
        
        // Basic analysis based on content characteristics
        String content = request.getContent().toLowerCase();
        double score = calculateBasicCredibilityScore(content);
        
        result.setCredibilityScore(score);
        result.setOverallAssessment(aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse);
        result.setBiasAnalysis("AI response could not be parsed properly. Manual review recommended.");
        result.setFactCheckSummary("Automated fact-checking completed with limitations.");
        
        // Basic red flags detection
        List<String> redFlags = Arrays.asList(
            content.contains("breaking") ? "Sensational language detected" : null,
            content.contains("shocking") ? "Emotional manipulation detected" : null,
            content.contains("they don't want you to know") ? "Conspiracy language detected" : null
        ).stream().filter(flag -> flag != null).toList();
        
        result.setRedFlags(redFlags.isEmpty() ? Arrays.asList("No obvious red flags detected") : redFlags);
        result.setPositiveIndicators(Arrays.asList("Content analyzed", "No immediate threats detected"));
        
        return result;
    }
    
    private double calculateBasicCredibilityScore(String content) {
        double score = 0.7; // Base score
        
        // Reduce score for suspicious patterns
        if (content.contains("breaking") || content.contains("shocking")) score -= 0.2;
        if (content.contains("they don't want you to know")) score -= 0.3;
        if (content.contains("!!!") || content.contains("???")) score -= 0.1;
        if (content.length() < 20) score -= 0.2; // Very short content
        
        // Increase score for positive indicators
        if (content.contains("according to") || content.contains("study shows")) score += 0.1;
        if (content.contains("research") || content.contains("data")) score += 0.1;
        
        return Math.max(0.0, Math.min(1.0, score));
    }
}

