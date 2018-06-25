package org.garfieldnate.nlpl.server;

import org.garfieldnate.nlpl.Tokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
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

    private final Map<String, Tokenizer> tokenizers = new HashMap<>();

    public NLPLApplication(Collection<Tokenizer> tokenizers) {
        System.out.println(tokenizers);
        for (Tokenizer tok : tokenizers) {
            this.tokenizers.put(tok.getLanguage(), tok);
        }
    }

    @RequestMapping(value = "/tokenize", method = RequestMethod.POST)
    public Response tokenize(@RequestParam String lang, @RequestBody String inputText) {
        Tokenizer tokenizer = tokenizers.get(lang);
        if (tokenizer == null) {
            return new Response(null, "No tokenizer for " + lang + " available");
        }
        Tokenizer.Result result = tokenizer.tokenize(inputText);
        return new Response(result, null);
    }

    public static void main(String[] args) {
        SpringApplication.run(NLPLApplication.class, args);
    }
}
