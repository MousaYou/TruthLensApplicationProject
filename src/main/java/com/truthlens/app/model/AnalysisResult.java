package com.truthlens.app.model;

import java.time.LocalDateTime;
import java.util.List;

public class AnalysisResult {
    
    private String originalContent;
    private String source;
    private double credibilityScore; // 0.0 to 1.0
    private String overallAssessment;
    private List<String> redFlags;
    private List<String> positiveIndicators;
    private String biasAnalysis;
    private String factCheckSummary;
    private LocalDateTime analysisTimestamp;
    private String aiModel;
    
    public AnalysisResult() {
        this.analysisTimestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getOriginalContent() {
        return originalContent;
    }
    
    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public double getCredibilityScore() {
        return credibilityScore;
    }
    
    public void setCredibilityScore(double credibilityScore) {
        this.credibilityScore = credibilityScore;
    }
    
    public String getOverallAssessment() {
        return overallAssessment;
    }
    
    public void setOverallAssessment(String overallAssessment) {
        this.overallAssessment = overallAssessment;
    }
    
    public List<String> getRedFlags() {
        return redFlags;
    }
    
    public void setRedFlags(List<String> redFlags) {
        this.redFlags = redFlags;
    }
    
    public List<String> getPositiveIndicators() {
        return positiveIndicators;
    }
    
    public void setPositiveIndicators(List<String> positiveIndicators) {
        this.positiveIndicators = positiveIndicators;
    }
    
    public String getBiasAnalysis() {
        return biasAnalysis;
    }
    
    public void setBiasAnalysis(String biasAnalysis) {
        this.biasAnalysis = biasAnalysis;
    }
    
    public String getFactCheckSummary() {
        return factCheckSummary;
    }
    
    public void setFactCheckSummary(String factCheckSummary) {
        this.factCheckSummary = factCheckSummary;
    }
    
    public LocalDateTime getAnalysisTimestamp() {
        return analysisTimestamp;
    }
    
    public void setAnalysisTimestamp(LocalDateTime analysisTimestamp) {
        this.analysisTimestamp = analysisTimestamp;
    }
    
    public String getAiModel() {
        return aiModel;
    }
    
    public void setAiModel(String aiModel) {
        this.aiModel = aiModel;
    }
    
    public String getCredibilityLevel() {
        if (credibilityScore >= 0.8) return "HIGH";
        if (credibilityScore >= 0.6) return "MEDIUM";
        if (credibilityScore >= 0.4) return "LOW";
        return "VERY LOW";
    }
    
    public String getCredibilityColor() {
        if (credibilityScore >= 0.8) return "success";
        if (credibilityScore >= 0.6) return "warning";
        if (credibilityScore >= 0.4) return "danger";
        return "dark";
    }
}

