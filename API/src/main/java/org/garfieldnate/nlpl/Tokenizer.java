package org.garfieldnate.nlpl;

import java.util.List;
import java.util.Map;

import lombok.Value;

public interface Tokenizer {
    @Value
	class Result {
		public final String text;
		public final List<Token> tokens;

		@Value
		public static class Token {
            public final int start;
			public final int end;
			public final String lemma;
			public final Map<String, String> details;
		}
	}

	Result tokenize(String inputText);
}
