package com.truthlens.app.controller;

import com.truthlens.app.model.AnalysisRequest;
import com.truthlens.app.model.AnalysisResult;
import com.truthlens.app.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.CompletableFuture;

@Controller
public class HomeController {
    
    @Autowired
    private AnalysisService analysisService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("analysisRequest", new AnalysisRequest());
        return "index";
    }
    
    @PostMapping("/analyze")
    public String analyzeContent(@Valid @ModelAttribute AnalysisRequest analysisRequest,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("analysisRequest", analysisRequest);
            return "index";
        }
        
        try {
            CompletableFuture<AnalysisResult> futureResult = analysisService.analyzeContent(analysisRequest);
            AnalysisResult result = futureResult.get(); // Wait for completion
            
            model.addAttribute("analysisResult", result);
            model.addAttribute("analysisRequest", new AnalysisRequest()); // Reset form
            return "index";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during analysis: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/api/health")
    @ResponseBody
    public String health() {
        return "TruthLens API is running";
    }
}

