package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;

public class TemporalRelations {

    /**
     * test the basic annotations and feature extractions
     * @param args
     */

    public static void main(String[] args) {


        BasicAnnotations test = new BasicAnnotations();
        List<String> dirlist = new ArrayList<>();
        dirlist.add("subSet");
        //dirlist.add("TimeBank");
        List<Annotation> annotations = test.annotate(dirlist);



        ArrayList<ArrayList<BasicFeatures>> annotatedSentences = new ArrayList<>();
        for (Annotation annotation : annotations) {
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
                ArrayList<BasicFeatures> annotatedSentence = StringMapper.extractFeaturesSentence(tokens);
                annotatedSentences.add(annotatedSentence);
                for(BasicFeatures features : annotatedSentence){
                    System.out.println(features);
                }

              /*  for (CoreLabel token : tokens) {
                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                    System.out.println("word: " + word);
                    System.out.println("POS-tag: " + pos);
                    System.out.println("NER: " + ne);
                }
                System.out.println("-------------------SENTENCE-------------------");
            }
            System.out.println("-------------------FILE-----------------------");
            */
            }
        }
        CsvWriter.writeToFile(annotatedSentences, "Data/testFile.csv");
    }

}
