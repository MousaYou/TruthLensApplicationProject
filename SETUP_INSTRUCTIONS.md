# Quick Setup Instructions for TruthLens

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- DeepSeek API key

## Quick Start (5 minutes)

### 1. Install Java and Maven (if not already installed)

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven
```

**Windows:**
- Download and install Java 17 from Oracle or OpenJDK
- Download and install Maven from Apache Maven website

**macOS:**
```bash
brew install openjdk@17 maven
```

### 2. Set DeepSeek API Key

**Linux/macOS:**
```bash
export DEEPSEEK_API_KEY="your-api-key-here"
```

**Windows:**
```cmd
set DEEPSEEK_API_KEY=your-api-key-here
```

### 3. Navigate to Project Directory
```bash
cd truthlens-app
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

### 5. Access the Application
Open your browser and go to: http://localhost:8080

## That's it! 🎉

The application should now be running and ready to analyze content for misinformation.

## Troubleshooting

**If the application doesn't start:**
1. Check Java version: `java -version` (should be 17+)
2. Check Maven version: `mvn -version`
3. Verify DeepSeek API key is set: `echo $DEEPSEEK_API_KEY`
4. Make sure port 8080 is not in use

**If AI analysis doesn't work:**
1. Verify your DeepSeek API key is valid
2. Check your internet connection
3. Ensure your DeepSeek account has sufficient credits

## Alternative Running Methods

**Using JAR file:**
```bash
mvn clean package -DskipTests
java -jar target/truthlens-app-1.0.0.jar
```

**Using IDE:**
1. Import as Maven project
2. Run `TruthLensApplication.java`

For detailed documentation, see README.md

