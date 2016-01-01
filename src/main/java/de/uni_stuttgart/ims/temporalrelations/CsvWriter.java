package de.uni_stuttgart.ims.temporalrelations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 21.12.15.
 */
public class CsvWriter {


    public static String header(){
        return "word,lemma,POS,unicodeCharClass,tempType," +
                "prevprevprevword,prevprevprevlemma,prevprevprevPOS,prevprevprevunicodeCharClass,prevprevprevtempType," +
                "prevprevword,prevprevlemma,prevprevPOS,prevprevunicodeCharClass,prevprevtempType," +
                "prevword,prevlemma,prevPOS,prevunicodeCharClass,prevtempType," +
                "nextword,nextlemma,nextPOS,nextunicodeCharClass,nexttempType," +
                "nextnextword,nextnextlemma,nextnextPOS,nextnextunicodeCharClass,nextnexttempType," +
                "nextnextnextword,nextnextnextlemma,nextnextnextPOS,nextnextnextunicodeCharClass,nextnextnexttempType," +
                "class";
    }



    public static void writeToFile(ArrayList<ArrayList<ClassedToken>> annotatedSentence, String fileName) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
            outputStream.println(header());
            for (ArrayList<ClassedToken> tokens : annotatedSentence) {
                for (ClassedToken token : tokens) {
                    BasicFeatures features = token.getFeatures();
                    outputStream.print(escape(features.word) + "," +escape(features.lemma) + "," + escape(features.POS)+","+features.unicodeCharClass+","+features.tempType);
                    printPrevContext(outputStream, features);
                    printNextContext(outputStream, features);
                    outputStream.println(","+token.getClassification());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not open or create file" + fileName);
        } finally {
            outputStream.close();
        }



    }

    private static void printNextContext(PrintWriter outputStream, BasicFeatures features) {
        for(BasicFeatures nextFeatures : features.next){
            if(nextFeatures != null) {
                outputStream.print("," + escape(nextFeatures.word) + "," + escape(nextFeatures.lemma) + "," + escape(nextFeatures.POS) + "," + nextFeatures.unicodeCharClass + "," + nextFeatures.tempType);
            }else{
                outputStream.print(",,,,,");
            }
        }
    }

    private static void printPrevContext(PrintWriter outputStream, BasicFeatures features) {
        for(BasicFeatures prevFeatures : features.prev){
            if(prevFeatures != null) {
                outputStream.print("," + escape(prevFeatures.word) + "," + escape(prevFeatures.lemma) + "," + escape(prevFeatures.POS) + "," + prevFeatures.unicodeCharClass + "," + prevFeatures.tempType);
            }else{
                outputStream.print(",,,,,");
            }
        }
    }

    private static String escape(String token){
        return '"'+token+'"';
    }

}
