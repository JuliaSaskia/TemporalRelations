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
public class CsvWriter extends Writer{
    private PrintWriter outputStream;

    public CsvWriter(String fileName) throws FileNotFoundException {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }


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


    @Override
    public void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        outputStream.println(header());
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            for (ClassedToken token : tokens) {
                BasicFeatures features = token.getFeatures();
                outputStream.print(escape(features.word) + "," +escape(features.lemma) + "," + escape(features.POS)+","+features.unicodeCharClass+","+features.tempType);
                printPrevContext(features);
                printNextContext(features);
                if (token.getClassification() == ClassedToken.TimeMlClass.TIME){
                    outputStream.println(","+"yes");
                }else{
                    outputStream.println(","+"no");
                }

            }
        }
    }

    @Override
    public void close(){
        outputStream.close();
    }



    private void printNextContext(BasicFeatures features) {
        for(BasicFeatures nextFeatures : features.next){
            if(nextFeatures != null) {
                outputStream.print("," + escape(nextFeatures.word) + "," + escape(nextFeatures.lemma) + "," + escape(nextFeatures.POS) + "," + nextFeatures.unicodeCharClass + "," + nextFeatures.tempType);
            }else{
                outputStream.print(",,,,,");
            }
        }
    }

    private void printPrevContext(BasicFeatures features) {
        for(BasicFeatures prevFeatures : features.prev){
            if(prevFeatures != null) {
                outputStream.print("," + escape(prevFeatures.word) + "," + escape(prevFeatures.lemma) + "," + escape(prevFeatures.POS) + "," + prevFeatures.unicodeCharClass + "," + prevFeatures.tempType);
            }else{
                outputStream.print(",,,,,");
            }
        }
    }

    private static String escape(String token){
        token = token.replace("\"","\\\"");
        token = token.replace("'","\u2019");
        return '"'+token+'"';
    }

}
