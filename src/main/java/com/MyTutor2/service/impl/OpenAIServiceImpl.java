package com.MyTutor2.service.impl;

import com.MyTutor2.service.OpenAIService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${openai.model:o1-mini}")
    private String model;

    private final RestClient.Builder restClientBuilder;
    private RestClient restClient;

    public OpenAIServiceImpl(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @PostConstruct
    public void init() {
        this.restClient = restClientBuilder
                .baseUrl(apiUrl)
                .build();
    }


    public String askQuestion(String question) {
        var prompt = "You are a helpful chatbot that assists people with their questions about programming, mathematics and data science. " +
                "Make sure to answer the question as best as you can, providing some context. Keep in mind that the person asking" +
                "the question is probably a junior developer or a student, so make sure to keep the answer simple and explain any complex concepts" +
                "The question is" + question;

        // Create a map representing the message: {"role": "user", "content": PROMPT}
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("role", "user");
        messageContent.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(messageContent));

        try {
            var response = restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + apiKey)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            if (response != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null) {
                        return (String) message.get("content");
                    }
                }
            }

            return "We are really sorry, There seems to be a problem with TutorBot! Please come back later";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while processing your request: " + e.getMessage();
        }
    }
}
