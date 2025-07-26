# TruthLens - AI-Powered Misinformation Detection Tool

## Overview

TruthLens is a Spring Boot web application that uses AI to analyze and detect potential misinformation in social media content. Built with Spring Boot, Thymeleaf, Bootstrap, and Maven, it provides real-time credibility analysis and bias detection for text content.

## Features

- **AI-Powered Analysis**: Uses DeepSeek V3 models to analyze content for misinformation patterns
- **Real-time Results**: Instant credibility scoring and detailed analysis
- **User-Friendly Interface**: Modern, responsive web interface built with Bootstrap
- **Bias Detection**: Identifies potential bias and red flags in content
- **Educational**: Promotes digital literacy and critical thinking skills
- **REST API**: Provides API endpoints for programmatic access

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: Thymeleaf, Bootstrap 5.3.2, HTML/CSS/JavaScript
- **Build Tool**: Maven 3.6.3
- **AI Integration**: DeepSeek V3 API
- **Dependencies**: Spring Web, Spring WebFlux, Jackson, WebJars

## Project Structure

```
truthlens-app/
├── src/
│   ├── main/
│   │   ├── java/com/truthlens/app/
│   │   │   ├── TruthLensApplication.java      # Main application class
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java        # Web controller
│   │   │   │   └── ApiController.java         # REST API controller
│   │   │   ├── service/
│   │   │   │   ├── AnalysisService.java       # Main analysis service
│   │   │   │   └── DeepSeekService.java       # DeepSeek integration
│   │   │   └── model/
│   │   │       ├── AnalysisRequest.java       # Request model
│   │   │       └── AnalysisResult.java        # Result model
│   │   └── resources/
│   │       ├── templates/                     # Thymeleaf templates
│   │       │   ├── index.html                 # Main page
│   │       │   ├── about.html                 # About page
│   │       │   └── layout.html                # Base layout
│   │       ├── static/                        # Static assets
│   │       │   ├── css/style.css              # Custom styles
│   │       │   └── js/app.js                  # JavaScript functionality
│   │       └── application.properties         # Configuration
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- DeepSeek API key (for AI analysis functionality)

## Installation & Setup

### 1. Clone or Download the Project

```bash
# If using git
git clone <repository-url>
cd truthlens-app

# Or extract from provided archive
unzip truthlens-app.zip
cd truthlens-app
```

### 2. Install Dependencies

Make sure Java 17 and Maven are installed:

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Install on Ubuntu/Debian if needed
sudo apt update
sudo apt install openjdk-17-jdk maven
```

### 3. Configure DeepSeek API

Set your DeepSeek API key as an environment variable:

```bash
# Linux/Mac
export DEEPSEEK_API_KEY="your-deepseek-api-key-here"

# Windows
set DEEPSEEK_API_KEY=your-deepseek-api-key-here
```

Or modify `src/main/resources/application.properties`:

```properties
deepseek.api.key=your-deepseek-api-key-here
```

### 4. Build the Application

```bash
# Clean and compile
mvn clean compile

# Build JAR file (optional)
mvn clean package -DskipTests
```

## Running the Application

### Method 1: Using Maven (Recommended for Development)

```bash
mvn spring-boot:run
```

### Method 2: Using JAR File

```bash
# First build the JAR
mvn clean package -DskipTests

# Then run it
java -jar target/truthlens-app-1.0.0.jar
```

### Method 3: Using IDE

1. Import the project as a Maven project
2. Run the `TruthLensApplication.java` main class

## Accessing the Application

Once running, the application will be available at:

- **Web Interface**: http://localhost:8080
- **About Page**: http://localhost:8080/about
- **Health Check**: http://localhost:8080/api/health
- **API Status**: http://localhost:8080/api/status

## API Usage

### Analyze Content (POST)

```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Breaking news: Scientists discover shocking truth!",
    "source": "Social Media"
  }'
```

### Response Format

```json
{
  "originalContent": "Breaking news: Scientists discover shocking truth!",
  "source": "Social Media",
  "credibilityScore": 0.3,
  "overallAssessment": "Content shows signs of sensationalism",
  "redFlags": ["Sensational language detected", "Lack of specific details"],
  "positiveIndicators": ["No immediate threats detected"],
  "biasAnalysis": "Content uses emotional language typical of clickbait",
  "factCheckSummary": "Requires verification from credible sources",
  "analysisTimestamp": "2025-07-23T16:30:00",
  "aiModel": "GPT-3.5-turbo"
}
```

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080
server.address=0.0.0.0

# DeepSeek Configuration
deepseek.api.key=${DEEPSEEK_API_KEY:}
deepseek.api.base=${DEEPSEEK_API_BASE:https://api.deepseek.com}

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

## Features in Detail

### 1. Content Analysis
- Accepts text content up to 1000 characters
- Analyzes for misinformation patterns, bias, and credibility
- Provides credibility score from 0.0 to 1.0
- Identifies red flags and positive indicators

### 2. User Interface
- Modern, responsive design using Bootstrap 5
- Real-time character counter
- Form validation and error handling
- Smooth animations and transitions
- Mobile-friendly interface

### 3. AI Integration
- Uses DeepSeek V3 for analysis
- Structured prompts for consistent results
- Fallback analysis for API failures
- Error handling and timeout management

### 4. Educational Value
- Promotes critical thinking and digital literacy
- Explains analysis methodology
- Highlights cognitive biases and fallacies
- Provides transparent AI limitations

## Troubleshooting

### Common Issues

1. **Application won't start**
   - Check Java version (must be 17+)
   - Verify Maven installation
   - Check port 8080 availability

2. **AI analysis not working**
   - Verify DeepSeek API key is set correctly
   - Check internet connectivity
   - Ensure API key has sufficient credits

3. **Build failures**
   - Run `mvn clean` to clear cache
   - Check internet connection for dependency downloads
   - Verify Java and Maven versions

### Logs and Debugging

- Application logs are printed to console
- Enable debug logging by adding to `application.properties`:
  ```properties
  logging.level.com.truthlens=DEBUG
  ```

## Development

### Adding New Features

1. **New Analysis Types**: Extend `AnalysisService` and `OpenAIService`
2. **UI Improvements**: Modify Thymeleaf templates and CSS
3. **API Endpoints**: Add new controllers in the `controller` package
4. **Data Models**: Create new models in the `model` package

### Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AnalysisServiceTest

# Skip tests during build
mvn package -DskipTests
```

## Deployment

### Local Deployment

The application is configured to run locally on port 8080 with the embedded Tomcat server.

### Production Deployment

For production deployment:

1. Build the JAR file: `mvn clean package`
2. Set environment variables for production
3. Run with production profile: `java -jar -Dspring.profiles.active=prod target/truthlens-app-1.0.0.jar`

## Educational Context

This project was developed as part of a computer science course focusing on:

- **21st-century skills**: Critical thinking and digital literacy
- **AI ethics**: Responsible use of AI technology
- **Problem-solving**: Addressing misinformation in digital media
- **Technical skills**: Full-stack web development with modern frameworks

## Limitations and Disclaimers

- **AI Limitations**: The AI analysis is not perfect and should not be the sole source of truth
- **Educational Purpose**: This tool is designed for learning and should complement, not replace, human judgment
- **Bias Awareness**: The AI model may have its own biases that affect analysis results
- **Continuous Learning**: Users should verify information from multiple credible sources

## Contributing

To contribute to this project:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is developed for educational purposes. Please respect OpenAI's usage policies when using their API.

## Support

For issues or questions:

1. Check the troubleshooting section
2. Review application logs
3. Verify configuration settings
4. Consult Spring Boot documentation

## Acknowledgments

- DeepSeek for providing the AI API
- Spring Boot team for the excellent framework
- Bootstrap team for the UI components
- The open-source community for various dependencies

---

**Note**: This application requires an active internet connection and a valid DeepSeek API key to function properly. The AI analysis feature will not work without proper API configuration.

