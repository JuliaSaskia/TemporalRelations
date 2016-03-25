package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * main class of the temporal relations project
 * handles setting up the pipeline and reading and writing files
 *
 * @author julia bettinger
 * @author jens beck
 */

public class TemporalRelations {


    public static void main(String[] args) {


        BasicAnnotations test = new BasicAnnotations();
        //list of directories with files to annotate
        List<String> dirlist = new ArrayList<>();
        dirlist.add("TimeBank");
        //dirlist.add("TE3-platinum-test"); //test data

        //annotate all files in the given directories
        List<Annotation> annotations = test.annotate(dirlist);


        ArrayList<ArrayList<ClassedToken>> annotatedSentences = new ArrayList<>();
        //goes through all the sentences found by coreNLP and annotate them
        for (Annotation annotation : annotations) {
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

            for (CoreMap sentence : sentences) {
                List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
                ArrayList<ClassedToken> annotatedSentence = StringMapper.extractFeaturesSentence(tokens);
                annotatedSentences.add(annotatedSentence);

                //print out for checking if it does the correct thing
                for (CoreLabel token : tokens) {
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


        //writing the training file for mallet
        try {
            Writer ssvWriter = new SsvWriterBIO("Data/TimeBank_malletTrain.ssv");
            ssvWriter.write(annotatedSentences);
            ssvWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }


        //writing the test file for mallet
//        try {
//            SsvWriterBIO ssvWriter = new SsvWriterBIO("Data/TimeBank_malletTest.ssv");
//            ssvWriter.writeTest(annotatedSentences);
//            ssvWriter.close();
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//        }

        //writing the training file for weka
//        try {
//            CsvWriterType csvWriterType = new CsvWriterType("Data/TimeBank_wekaTrain.csv");
//            csvWriterType.write(annotatedSentences);
//            csvWriterType.close();
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: "+e.getMessage());
//        }

        //creating the test file for weka, reading in the annotated test file for mallet first
//        try {
//            MalletToWekaConverter malletToWekaConverter = new MalletToWekaConverter("Data/TimeBank_wekaTest.csv");
//            malletToWekaConverter.readMalletPasted("Data/TimeBank_malletTest_tagged.txt");
//            malletToWekaConverter.close();
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: "+e.getMessage());
//        }


        }

    }
}


