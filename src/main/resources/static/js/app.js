// TruthLens JavaScript Application

document.addEventListener('DOMContentLoaded', function() {
    // Initialize application
    initializeApp();
});

function initializeApp() {
    // Add loading states to forms
    setupFormSubmission();
    
    // Add character counter to textarea
    setupCharacterCounter();
    
    // Add smooth scrolling
    setupSmoothScrolling();
    
    // Add fade-in animations
    setupAnimations();
    
    // Setup tooltips
    setupTooltips();
}

function setupFormSubmission() {
    const analyzeForm = document.querySelector('form[action*="/analyze"]');
    const analyzeBtn = document.getElementById('analyzeBtn');
    
    if (analyzeForm && analyzeBtn) {
        analyzeForm.addEventListener('submit', function(e) {
            // Show loading state
            const originalText = analyzeBtn.innerHTML;
            analyzeBtn.innerHTML = '<span class="loading me-2"></span>Analyzing...';
            analyzeBtn.disabled = true;
            
            // Add a timeout to re-enable button in case of errors
            setTimeout(() => {
                if (analyzeBtn.disabled) {
                    analyzeBtn.innerHTML = originalText;
                    analyzeBtn.disabled = false;
                }
            }, 30000); // 30 seconds timeout
        });
    }
}

function setupCharacterCounter() {
    const contentTextarea = document.getElementById('content');
    
    if (contentTextarea) {
        // Create character counter element
        const counterElement = document.createElement('div');
        counterElement.className = 'character-counter text-muted mt-1';
        counterElement.innerHTML = '<small><i class="fas fa-keyboard me-1"></i><span id="char-count">0</span>/1000 characters</small>';
        
        // Insert after the textarea
        contentTextarea.parentNode.insertBefore(counterElement, contentTextarea.nextSibling);
        
        const charCountSpan = document.getElementById('char-count');
        
        // Update character count
        function updateCharCount() {
            const currentLength = contentTextarea.value.length;
            charCountSpan.textContent = currentLength;
            
            // Change color based on character count
            if (currentLength > 900) {
                counterElement.className = 'character-counter text-danger mt-1';
            } else if (currentLength > 800) {
                counterElement.className = 'character-counter text-warning mt-1';
            } else {
                counterElement.className = 'character-counter text-muted mt-1';
            }
        }
        
        // Add event listeners
        contentTextarea.addEventListener('input', updateCharCount);
        contentTextarea.addEventListener('paste', () => setTimeout(updateCharCount, 10));
        
        // Initial count
        updateCharCount();
    }
}

function setupSmoothScrolling() {
    // Add smooth scrolling to anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
}


analyzeForm.addEventListener('submit', function(e) {
    e.preventDefault();

    // Show loading state
    const originalText = analyzeBtn.innerHTML;
    analyzeBtn.innerHTML = '<span class="loading me-2"></span>Analyzing...';
    analyzeBtn.disabled = true;

    // Submit form via fetch API for better error handling
    fetch(analyzeForm.action, {
        method: 'POST',
        body: new FormData(analyzeForm)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Analysis failed');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('results-container').innerHTML = html;
        })
        .catch(error => {
            showNotification('Analysis failed: ' + error.message, 'danger');
        })
        .finally(() => {
            analyzeBtn.innerHTML = originalText;
            analyzeBtn.disabled = false;
        });
});



function setupAnimations() {
    // Add fade-in animation to cards
    const cards = document.querySelectorAll('.card');
    
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    cards.forEach(card => {
        observer.observe(card);
    });
}

function setupTooltips() {
    // Initialize Bootstrap tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

// Utility functions
function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    notification.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    notification.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    // Add to page
    document.body.appendChild(notification);
    
    // Auto-remove after 5 seconds
    setTimeout(() => {
        if (notification.parentNode) {
            notification.remove();
        }
    }, 5000);
}

function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        showNotification('Copied to clipboard!', 'success');
    }).catch(() => {
        showNotification('Failed to copy to clipboard', 'danger');
    });
}

// Add copy functionality to analysis results
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('copy-result')) {
        const resultText = e.target.getAttribute('data-text');
        copyToClipboard(resultText);
    }
});

// Form validation enhancements
function validateForm(form) {
    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');
    
    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            field.classList.add('is-invalid');
            isValid = false;
        } else {
            field.classList.remove('is-invalid');
            field.classList.add('is-valid');
        }
    });
    
    return isValid;
}



// Real-time form validation
document.querySelectorAll('input, textarea').forEach(field => {
    field.addEventListener('blur', function() {
        if (this.hasAttribute('required')) {
            if (!this.value.trim()) {
                this.classList.add('is-invalid');
                this.classList.remove('is-valid');
            } else {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            }
        }
    });
});

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Ctrl/Cmd + Enter to submit form
    if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
        const activeForm = document.querySelector('form');
        if (activeForm) {
            activeForm.submit();
        }
    }
    
    // Escape to clear form
    if (e.key === 'Escape') {
        const contentTextarea = document.getElementById('content');
        if (contentTextarea && document.activeElement === contentTextarea) {
            if (confirm('Clear the content?')) {
                contentTextarea.value = '';
                contentTextarea.focus();
            }
        }
    }
});

// Progressive Web App features
if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('/sw.js')
            .then(registration => {
                console.log('SW registered: ', registration);
            })
            .catch(registrationError => {
                console.log('SW registration failed: ', registrationError);
            });
    });
}

// Dark mode toggle (if implemented)
function toggleDarkMode() {
    document.body.classList.toggle('dark-mode');
    localStorage.setItem('darkMode', document.body.classList.contains('dark-mode'));
}

// Load dark mode preference
if (localStorage.getItem('darkMode') === 'true') {
    document.body.classList.add('dark-mode');
}

// Export functions for global use
window.TruthLens = {
    showNotification,
    copyToClipboard,
    validateForm,
    toggleDarkMode
};

