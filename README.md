# NLPL
Web backend app for NLP-aided language learning

Currently all that this does is tokenize text sent to it.

Run with `gradle server:bootRun` and post Vietnamese text to http://localhost:8080/api/v1/tokenize?lang=VN. 
You will get a JSON response with the tokens, lemmas (dictionary forms), span in original text, and a map containing other available details (part-of-speech, etc.).
