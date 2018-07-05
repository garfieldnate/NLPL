package org.garfieldnate.nlpl.tokenize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Request tokenizing some text.")
@NoArgsConstructor
@EqualsAndHashCode
public class TokenizeRequest {
    @ApiModelProperty(value="The language of the input text", example="vie")
    @NotBlank
    @Pattern(regexp="^[a-z]{3}(-[A-Z]{2})?$", message="Should be an ISO 639-2 3-letter language code")
    @Getter
    @Setter
    String language;
    @ApiModelProperty(value="The text to tokenize", example="Tôi học tiếng Việt.")
    @NotBlank
    @Getter
    @Setter
    String text;
}
