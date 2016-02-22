package de.uni_stuttgart.ims.temporalrelations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 21.12.15.
 */
public class SsvWriterBIO extends Writer{
    private PrintWriter outputStream;

    public SsvWriterBIO(String fileName) throws FileNotFoundException {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }





    @Override
    public void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            ClassedToken previous = null;
            for (ClassedToken token : tokens) {
                BasicFeatures features = token.getFeatures();
                outputStream.print(escape(features.word) + " " +escape(features.lemma) + " " + escape(features.POS)+" "+features.unicodeCharClass+" "+features.tempType);
                printPrevContext(features);
                printNextContext(features);
                if (token.getClassification() == ClassedToken.TimeMlClass.TIME){
                    if (previous != null && previous.getClassification() == ClassedToken.TimeMlClass.TIME){
                        outputStream.println(" I");
                    }else{
                        outputStream.println(" B");
                    }
                }else{
                    outputStream.println(" O");
                }
                previous = token;


            }
        }
    }
    


    @Override
    public void close(){
        outputStream.close();
    }


    public void writeTest(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            for (ClassedToken token : tokens) {
                BasicFeatures features = token.getFeatures();
                outputStream.print(escape(features.word) + " " +escape(features.lemma) + " " + escape(features.POS)+" "+features.unicodeCharClass+" "+features.tempType);
                printPrevContext(features);
                printNextContext(features);
            }
        }
    }



    private void printNextContext(BasicFeatures features) {
        for(BasicFeatures nextFeatures : features.next){
            if(nextFeatures != null) {
                outputStream.print(" " + escape(nextFeatures.word) + " " + escape(nextFeatures.lemma) + " " + escape(nextFeatures.POS) + " " + nextFeatures.unicodeCharClass + " " + nextFeatures.tempType);
            }
        }
    }

    private void printPrevContext(BasicFeatures features) {
        for(BasicFeatures prevFeatures : features.prev){
            if(prevFeatures != null) {
                outputStream.print(" " + escape(prevFeatures.word) + " " + escape(prevFeatures.lemma) + " " + escape(prevFeatures.POS) + " " + prevFeatures.unicodeCharClass + " " + prevFeatures.tempType);
            }
        }
    }

    private static String escape(String token){
        return token;
    }

}
