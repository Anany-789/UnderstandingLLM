package com.example.UnderstandingLLM;

import com.example.UnderstandingLLM.aiTools.CalculatorTool;
import com.example.UnderstandingLLM.aiTools.CurrencyExchangeTool;
//import com.example.UnderstandingLLM.aiTools.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private ChatClient chatClient;
    private CalculatorTool calculatorTool;
//    private WeatherTool weatherTool;
    private CurrencyExchangeTool currencyExchangeTool;

    private static final String SYSTEM_PROMPT = """
            You are a helpful AI assistant with access to external tools.

            Follow these rules:
            1. For arithmetic calculations, ALWAYS use the calculator tool.
            2. Always use calculator tool for even trivial calculation
            3. For current weather, ALWAYS use the currentWeather tool.
            4. For currency conversion or exchange rates, ALWAYS use the convertCurrency tool.
            5. You may call multiple tools when solving a multi-step request.
            6. After receiving tool results, explain the answer naturally.
            7. Never invent current weather or exchange-rate information.
            """;

    //To store the context
    private List<Message> history = new ArrayList<>();

    public ChatService(ChatClient.Builder chatClient, CalculatorTool calculatorTool,
//                       WeatherTool weatherTool,
                       CurrencyExchangeTool currencyExchangeTool){
        this.chatClient = chatClient.build();
        this.calculatorTool = calculatorTool;
//        this.weatherTool = weatherTool;
        this.currencyExchangeTool = currencyExchangeTool;
    }

    public String summarize(String ticket){
        String output = chatClient.prompt()//
                .user("Summarize this support ticket in 2 lines : "+ ticket)// we are acting as user and giving prompts
                .call()//this will trigger the call in background
                .content();

        return output;
    }

    public String chat(String message) {

        // USER role
        history.add(new UserMessage(message));

        // SYSTEM + Conversation History
        String response = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .messages(history)
                .tools(calculatorTool, currencyExchangeTool)
                .call()
                .content();

        // ASSISTANT role
        history.add(new AssistantMessage(response));

        return response;
    }


}
