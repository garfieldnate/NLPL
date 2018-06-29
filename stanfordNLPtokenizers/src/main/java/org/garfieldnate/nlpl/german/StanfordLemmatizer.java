package org.garfieldnate.nlpl.german;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer {

    protected StanfordCoreNLP pipeline;

    public StanfordLemmatizer() throws IOException {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props = new Properties();
        props.load(StanfordLemmatizer.class.getResourceAsStream("StanfordCoreNLP-german.properties"));

        /*
         * This is a pipeline that takes in a string and returns various analyzed linguistic forms.
         * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator),
         * and then other sequence model style annotation can be used to add things like lemmas,
         * POS tags, and named entities. These are returned as a list of CoreLabels.
         * Other analysis components build and store parse trees, dependency graphs, etc.
         *
         * This class is designed to apply multiple Annotators to an Annotation.
         * The idea is that you first build up the pipeline by adding Annotators,
         * and then you take the objects you wish to annotate and pass them in and
         * get in return a fully annotated object.
         *
         *  StanfordCoreNLP loads a lot of models, so you probably
         *  only want to do this once per execution
         */
        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> lemmatize(String documentText) {
        List<String> lemmas = new LinkedList<String>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the
                // list of lemmas
                lemmas.add(token.get(LemmaAnnotation.class));
            }
        }
        return lemmas;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Starting Stanford Lemmatizer");
        String text = "An Deutschland grenzen neun Staaten, die Nord- und Ostsee im Norden und die Alpen im "
                      + "Süden. Es liegt in der gemäßigten Klimazone und verfügt über sechzehn National- und "
                      + "über hundert Naturparks. Bundeshauptstadt sowie bevölkerungsreichste deutsche Stadt "
                      + "ist Berlin. Weitere Metropolen mit mehr als einer Million Einwohnern sind Hamburg, "
                      + "München und Köln, der größte Ballungsraum ist das Ruhrgebiet, Frankfurt als deutsches "
                      + "Finanzzentrum ist international von Bedeutung. Deutschland gilt international als das "
                      + "Land mit der zweithöchsten Zahl von Einwanderern nach den Vereinigten Staaten. Seine "
                      + "Bevölkerung ist die zweitälteste und hat mit 1,59 Kindern pro Frau (2016) eine der "
                      + "niedrigeren Geburtenraten im weltweiten Vergleich, liegt aber unter den EU-Staaten aufgrund "
                      + "eines kontinuierlichen Anstiegs in den 2010er-Jahren mittlerweile im Mittelfeld.";
        StanfordLemmatizer slem = new StanfordLemmatizer();
        System.out.println(slem.lemmatize(text));
    }

}
