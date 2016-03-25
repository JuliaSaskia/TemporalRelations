package de.uni_stuttgart.ims.temporalrelations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * class for writing training and test file for the mallet sequence tagger
 *
 * @author julia bettinger
 * @author jens beck
 *
 */
public class SsvWriterBIO extends Writer{

    private PrintWriter outputStream;


    public SsvWriterBIO(String fileName) throws FileNotFoundException {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }

    /**
     * writes training file, each line represents a token with all features,
     * last position of a line is the label (B,I or O)
     *
     * @param annotatedSentence
     */
    @Override
    public void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            ClassedToken previous = null;
            for (ClassedToken token : tokens) {
                BasicFeatures features = token.getFeatures();
                outputStream.print(escape(features.word) + " " + escape(features.lemma) + " " + escape(features.POS) + " " + features.unicodeCharClass + " " + features.tempType);
                //writing also the features of the context
                printPrevContext(features);
                printNextContext(features);
                //checks in XML-tags if the current token is a temporal expression
                if (token.getClassification() == ClassedToken.TimeMlClass.TIME) {
                    //checks if the current token is within a a temporal expression or at the beginning
                    if (previous != null && previous.getClassification() == ClassedToken.TimeMlClass.TIME) {
                        outputStream.println(" I");
                    } else {
                        outputStream.println(" B");
                    }
                } else {
                    outputStream.println(" O");
                }
                previous = token;


            } //outputStream.println();
        }

    }


    /**
     * closes the outputstream
     */
    @Override
    public void close(){
        outputStream.close();
    }


    /**
     * writes test file for the mallet sequence tagger, same as the trainings file but without
     * the label at the last position of each line
     *
     * @param annotatedSentence
     */
    public void writeTest(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            for (ClassedToken token : tokens) {
                BasicFeatures features = token.getFeatures();
                outputStream.print(escape(features.word) + " " +escape(features.lemma) + " " + escape(features.POS)+" "+features.unicodeCharClass+" "+features.tempType);
                //writing also the features of the context
                printPrevContext(features);
                printNextContext(features);
                outputStream.println();
            }
           // outputStream.println();
        }

    }


    /**
     * writes the extracted features also for the context tokens (3 tokens behind the current)
     * @param features
     */
    private void printNextContext(BasicFeatures features) {
        for(BasicFeatures nextFeatures : features.next){
            if(nextFeatures != null) {
                outputStream.print(" " + escape(nextFeatures.word) + " " + escape(nextFeatures.lemma) + " " + escape(nextFeatures.POS) + " " + nextFeatures.unicodeCharClass + " " + nextFeatures.tempType);
            }
        }
    }

    /**
     * writes the extracted features also for the context tokens (3 tokens before the current)
     * @param features
     */
    private void printPrevContext(BasicFeatures features) {
        for(BasicFeatures prevFeatures : features.prev){
            if(prevFeatures != null) {
                outputStream.print(" " + escape(prevFeatures.word) + " " + escape(prevFeatures.lemma) + " " + escape(prevFeatures.POS) + " " + prevFeatures.unicodeCharClass + " " + prevFeatures.tempType);
            }
        }
    }

    /**
     * class to modify the String of a token, currently doing no modification
     * @param token
     * @return  token
     */
    private static String escape(String token){
        return token;
    }

}
