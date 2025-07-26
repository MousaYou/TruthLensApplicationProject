# DeepSeek Migration Guide

## Overview

The TruthLens application has been successfully migrated from OpenAI's ChatGPT to DeepSeek's AI models. This migration provides several benefits including potentially lower costs, different model capabilities, and reduced dependency on OpenAI's infrastructure.

## Changes Made

### 1. Service Layer Changes

**File: `DeepSeekService.java` (formerly `OpenAIService.java`)**
- Changed API endpoint from `https://api.openai.com/v1` to `https://api.deepseek.com`
- Updated model from `gpt-3.5-turbo` to `deepseek-chat` (which points to DeepSeek-V3-0324)
- Modified authentication to use `DEEPSEEK_API_KEY` instead of `OPENAI_API_KEY`
- Added `stream: false` parameter for non-streaming responses

**File: `AnalysisService.java`**
- Updated dependency injection from `OpenAIService` to `DeepSeekService`
- Changed AI model display name from "GPT-3.5-turbo" to "DeepSeek-V3"

### 2. Configuration Changes

**File: `application.properties`**
- Replaced OpenAI configuration with DeepSeek configuration:
  ```properties
  # Old OpenAI Configuration
  openai.api.key=${OPENAI_API_KEY:}
  openai.api.base=${OPENAI_API_BASE:https://api.openai.com/v1}
  
  # New DeepSeek Configuration
  deepseek.api.key=${DEEPSEEK_API_KEY:}
  deepseek.api.base=${DEEPSEEK_API_BASE:https://api.deepseek.com}
  ```

## API Compatibility

DeepSeek API is fully compatible with OpenAI's API format, which means:
- Same request/response structure
- Same message format with `role` and `content`
- Same parameters like `temperature`, `max_tokens`, etc.
- Same JSON response format

## Available Models

- **`deepseek-chat`**: Points to DeepSeek-V3-0324 (general chat model)
- **`deepseek-reasoner`**: Points to DeepSeek-R1-0528 (reasoning model)

The application currently uses `deepseek-chat` for misinformation detection tasks.

## Setup Instructions

### 1. Get DeepSeek API Key

1. Visit [DeepSeek Platform](https://platform.deepseek.com/)
2. Sign up or log in to your account
3. Navigate to "API Keys" section
4. Create a new API key
5. Copy the generated key

### 2. Set Environment Variable

**Linux/macOS:**
```bash
export DEEPSEEK_API_KEY="your-deepseek-api-key-here"
```

**Windows (Command Prompt):**
```cmd
set DEEPSEEK_API_KEY=your-deepseek-api-key-here
```

**Windows (PowerShell):**
```powershell
$env:DEEPSEEK_API_KEY="your-deepseek-api-key-here"
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

## Testing the Migration

1. Start the application with the DeepSeek API key set
2. Navigate to `http://localhost:8080`
3. Submit content for analysis
4. Verify that:
   - Analysis completes successfully
   - Results show "Analyzed by DeepSeek-V3"
   - Credibility scores and analysis are generated

## Troubleshooting

### Common Issues

1. **Authentication Error (401)**
   - Verify your DeepSeek API key is correct
   - Ensure the environment variable is set properly
   - Check that your DeepSeek account has sufficient credits

2. **Rate Limiting (429)**
   - DeepSeek has rate limits similar to OpenAI
   - Wait a few minutes before retrying
   - Consider upgrading your DeepSeek plan for higher limits

3. **Connection Issues**
   - Verify internet connectivity
   - Check if `api.deepseek.com` is accessible from your network
   - Ensure no firewall is blocking the connection

### Logs and Debugging

Enable debug logging by adding to `application.properties`:
```properties
logging.level.com.truthlens.app.service.DeepSeekService=DEBUG
```

## Benefits of DeepSeek Migration

1. **Cost Efficiency**: DeepSeek often provides competitive pricing
2. **Model Diversity**: Access to different AI architectures and capabilities
3. **Reduced Vendor Lock-in**: Less dependency on a single AI provider
4. **Performance**: DeepSeek-V3 offers strong performance in reasoning tasks

## Rollback Instructions

If you need to rollback to OpenAI:

1. Rename `DeepSeekService.java` back to `OpenAIService.java`
2. Update the class name from `DeepSeekService` to `OpenAIService`
3. Update `AnalysisService.java` to inject `OpenAIService`
4. Revert `application.properties` to use OpenAI configuration
5. Set `OPENAI_API_KEY` environment variable
6. Rebuild and restart the application

## Future Enhancements

Consider these potential improvements:

1. **Multi-Model Support**: Allow users to choose between different AI providers
2. **Model Comparison**: Run analysis through multiple models and compare results
3. **Reasoning Model**: Integrate `deepseek-reasoner` for complex analysis tasks
4. **Fallback Mechanism**: Automatically switch to backup provider if primary fails

## Support

For DeepSeek-specific issues:
- [DeepSeek API Documentation](https://api-docs.deepseek.com/)
- [DeepSeek Platform](https://platform.deepseek.com/)
- [DeepSeek Community Discord](https://discord.gg/deepseek)

For application-specific issues, refer to the main README.md file.

