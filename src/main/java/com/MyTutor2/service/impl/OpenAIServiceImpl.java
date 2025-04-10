package com.MyTutor2.service.impl;

import com.MyTutor2.config.OpenAIAPIConfig;
import com.MyTutor2.service.OpenAIService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ChatBotAPI_5
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private OpenAIAPIConfig openAIAPIConfig;

    private final RestClient.Builder restClientBuilder;
    private RestClient restClient;

    public OpenAIServiceImpl(RestClient.Builder restClientBuilder, OpenAIAPIConfig openAIAPIConfig) {
        this.restClientBuilder = restClientBuilder;
        this.openAIAPIConfig = openAIAPIConfig;
    }


    public String askQuestion(String question) {
        String prompt = "You are a helpful chatbot that assists people with their questions about programming, mathematics and data science. " +
                "Make sure to answer the question as best as you can, providing some context. Keep in mind that the person asking" +
                "the question is probably a junior developer or a student, so make sure to keep the answer simple and explain any complex concepts" +
                "The question is" + question;

        // Create a map representing the message: {"role": "user", "content": PROMPT}
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("role", "user");
        messageContent.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAIAPIConfig.getModel()); // give him the model that we want to use. They have multiple models
        requestBody.put("messages", List.of(messageContent));  //give him the whole chat history. In our case List of one message.

        try {
            this.restClient = restClientBuilder
                    .baseUrl(openAIAPIConfig.getApiUrl()) // we build this restClient with this URL. Each request will go exactly to this URL
                    .build();

            var response = restClient
                    .post() //create a post request to the URL
                    .contentType(MediaType.APPLICATION_JSON) // that what I send you is a JSON
                    // Authorization mean this user can use this endpoint
                    .header("Authorization", "Bearer " + openAIAPIConfig.getApiKey())
                    //the body that we are sending
                    .body(requestBody) //the body is the data that I'm sending and teh header is meta-data
                    .retrieve()
                    .body(Map.class); //convert the body(of teh response) in to a map

            if (response != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null) {
                        String responseToTheQuestion = (String) message.get("content");
                        return responseToTheQuestion;
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
