package com.truthlens.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnalysisRequest {
    
    @NotBlank(message = "Content cannot be empty")
    @Size(max = 1000, message = "Content must be less than 1000 characters")
    private String content;
    
    private String source;
    
    public AnalysisRequest() {}
    
    public AnalysisRequest(String content, String source) {
        this.content = content;
        this.source = source;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
}

