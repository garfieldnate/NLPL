package org.garfieldnate.nlpl.server;

import org.garfieldnate.nlpl.tokenize.TokenizeRequest;
import org.garfieldnate.nlpl.tokenize.TokenizeResponse;
import org.garfieldnate.nlpl.tokenize.Tokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@SpringBootApplication
@ComponentScan("org.garfieldnate.nlpl")
@RequestMapping("/api/v1")
@Api(description="Set of methods for analyzing texts with NLP")
public class NLPLApplication {
    private final Map<String, Tokenizer> tokenizers = new HashMap<>();

    public NLPLApplication(Collection<Tokenizer> tokenizers) {
        System.out.println(tokenizers);
        for (Tokenizer tok : tokenizers) {
            this.tokenizers.put(tok.getLanguage(), tok);
        }
    }

    @RequestMapping(value = "/tokenize", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("Tokenizes input text and provides a lemma and possibly additional information for each token")
    public TokenizeResponse tokenize(@RequestBody @ApiParam("JSON string containing request parameters") TokenizeRequest request) {
        Tokenizer tokenizer = tokenizers.get(request.getLanguage());
        if (tokenizer == null) {
            return new TokenizeResponse(null, "No tokenizer for " + request.getLanguage() + " available");
        }
        Tokenizer.Result result = tokenizer.tokenize(request.getText());
        return new TokenizeResponse(result, null);
    }

    public static void main(String[] args) {
        SpringApplication.run(NLPLApplication.class, args);
    }
}
