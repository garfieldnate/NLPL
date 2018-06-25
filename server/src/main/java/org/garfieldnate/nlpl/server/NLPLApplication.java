package org.garfieldnate.nlpl.server;

import org.garfieldnate.nlpl.Tokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.Value;

@RestController
@SpringBootApplication
@ComponentScan("org.garfieldnate.nlpl")
public class NLPLApplication {
    @Value
    private static class Response {
        Tokenizer.Result result;
        String errorMessage;
    }

    private final Map<String, Tokenizer> tokenizers;

    public NLPLApplication(Map<String, Tokenizer> tokenizers) {
        System.out.println(tokenizers);
        this.tokenizers = tokenizers;
    }

    @RequestMapping(value = "/tokenize/{language}", method = RequestMethod.POST)
    public Response tokenize(@PathVariable String language, @RequestBody String inputText) {
        Tokenizer tokenizer = tokenizers.get(language);
        if (tokenizer == null) {
            return new Response(null, "No tokenizer for " + language + " available");
        }
        Tokenizer.Result result = tokenizer.tokenize(inputText);
        return new Response(result, null);
    }

    public static void main(String[] args) {
        SpringApplication.run(NLPLApplication.class, args);
    }
}
