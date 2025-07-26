package com.truthlens.app.controller;

import com.truthlens.app.model.AnalysisRequest;
import com.truthlens.app.model.AnalysisResult;
import com.truthlens.app.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow CORS for API endpoints
public class ApiController {
    
    @Autowired
    private AnalysisService analysisService;
    
    @PostMapping("/analyze")
    public CompletableFuture<ResponseEntity<AnalysisResult>> analyzeContent(@Valid @RequestBody AnalysisRequest request) {
        return analysisService.analyzeContent(request)
            .thenApply(result -> ResponseEntity.ok(result))
            .exceptionally(throwable -> ResponseEntity.internalServerError().build());
    }
    
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("TruthLens API is operational");
    }
}

