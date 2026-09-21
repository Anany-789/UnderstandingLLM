package com.example.UnderstandingLLM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatApp")
public class ChatController {
    @Autowired
    private ChatService chatService;


    @PostMapping("/summarize")
    public String summarize(@RequestBody  String ticket){
        return chatService.summarize(ticket);
    }


    @PostMapping("/chat")
    public String chat(@RequestBody  String ticket){
        System.out.println("cont");
        return chatService.chat(ticket);
    }
}
