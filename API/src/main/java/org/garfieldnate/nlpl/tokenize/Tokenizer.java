package org.garfieldnate.nlpl.tokenize;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

public interface Tokenizer {
    @ApiModel(description = "The results of tokenizing the user's input text")
    @Value
    class Result {
        @ApiModelProperty("Returns the text that was tokenized, and which is guaranteed to "
                          + "match exactly with the start and stop locations given by individual "
                          + "tokens in the tokens field. This field is necessary because some "
                          + "tokenizer technology may require pre-normalization of text before "
                          + "tokenization. Therefore, this text may be contain differences from "
                          + "the original input text.")
        public final String text;
        @ApiModelProperty("The tokens representing the input text")
        public final List<Token> tokens;

        @ApiModel(description = "One token extracted from the text")
        @Value
        public static class Token {
            @ApiModelProperty("The starting character index of the text corresponding to the token in the text field")
            public final int start;
            @ApiModelProperty("The ending character index of the text corresponding to the token in the text field")
            public final int end;
            @ApiModelProperty(value = "The dictionary form of the token.")
            public final String lemma;
            @ApiModelProperty("Additional details about the token which were made available by the specific tokenizer technology, e.g. part of speech, named entity, semantic information, morphological form for the input text, etc.")
            public final Map<String, String> details;
        }
    }

    Result tokenize(String inputText);

    String getLanguage();
}
