package com.example.UnderstandingLLM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatApp")
public class SummarizeController {
    @Autowired
    private SummarizeService summarizeService;


    @PostMapping("/summarize")
    public String summarize(@RequestBody  String ticket){
        return summarizeService.summarize(ticket);
    }


    @PostMapping("/chat")
    public String chat(@RequestBody  String ticket){
        return summarizeService.chat(ticket);
    }
}
