package org.garfieldnate.nlpl.tokenize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@ApiModel(description = "Response for the tokenize operation containing the tokenization results or an error message if there was a problem tokenizing the input text.")
@Value
public class TokenizeResponse {
    @ApiModelProperty("Results of tokenizing the user's input text")
    Tokenizer.Result result;

    @ApiModelProperty("If there was an error that prevented the text from being tokenized, this describes the error.")
    String errorMessage;
}
