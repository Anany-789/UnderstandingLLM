package com.example.UnderstandingLLM;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SummarizeService {

    private ChatClient chatClient;

    public SummarizeService(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }

//    1. Why use ChatClient.Builder instead of injecting ChatClient directly?
//    Think of ChatClient.Builder as a factory or customization kit, and ChatClient as the final product.
//
//    Why a builder? Spring AI gives you a builder so you can optionally customize your AI client before creating it (for example, adding default system instructions like "You are a helpful assistant", setting default safety filters, or adding logging).
//
//    The standard pattern: Spring Boot automatically creates a pre-configured ChatClient.Builder bean for you. By injecting it into your constructor and calling .build(), you get a fully initialized ChatClient ready to talk to your configured AI provider.

//    Look at it like this:
//
//    The Machine (chatClient): Built once in the constructor. It knows where to send the request (OpenRouter) and which model to use (openrouter/free). That never changes.
//
//            The Prompt (chatClient.prompt().user(prompt)): This happens fresh every time someone visits the URL. One user might type "Tell me a joke", and another user might type "What is Java?".

    public String summarize(String ticket){
        String output = chatClient.prompt()//
                .user("Summarize this support ticket in 2 lines : "+ ticket)// we are acting as user and giving prompts
                .call()//this will trigger the call in background
                .content();

        return output;
    }

}
