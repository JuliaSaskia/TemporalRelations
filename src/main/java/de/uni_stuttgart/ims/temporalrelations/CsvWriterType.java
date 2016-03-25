package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreLabel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * class for writing a training file for weka to learn identifying the type of a temporal expression
 *
 * @author julia bettinger
 * @author jens beck
 */
public class CsvWriterType {
    private PrintWriter outputStream;

    public CsvWriterType(String fileName) throws FileNotFoundException {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }

    /**
     * first line of a training or test file for weka
     *
     * @return      header as a String
     */
    public static String header(){
        return "tempExp,oneString,lastToken,unicodeCharClass,tempType,label";
    }

    /**
     * writes training file for weka
     * each line is a temporal expression followed by the extracted features
     * last position of each line is the label, the temporal type (SET, DURATION, TIME or DATE)
     * @param annotatedSentence
     */
    public void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence) {
        outputStream.println(header());
        for (ArrayList<ClassedToken> tokens : annotatedSentence) {
            ClassedToken previous = null;
            ArrayList<ClassedToken> tempEx = null;
            for (ClassedToken token : tokens) {
                //checks if the current token is a temporal expression
                if (token.getClassification() == ClassedToken.TimeMlClass.TIME) {
                    System.out.println(token.getToken());
                    if (previous == null || previous.getClassification() != ClassedToken.TimeMlClass.TIME) {
                        tempEx = new ArrayList<>();
                    }
                    tempEx.add(token);

                } else {
                    if(tempEx != null){
                        writeFeatures(tempEx);
                        tempEx = null;
                    }
                }
                previous = token;
            }

            if(tempEx != null) {
                writeFeatures(tempEx);
            }
        }

    }

    /**
     * writes all features of one temporal expression in one line including the label at the last position
     *
     * @param tempEx       all tokens of one temporal expression in an ArrayList
     */
    public void writeFeatures(ArrayList<ClassedToken> tempEx){
        StringBuilder temporalExpression= new StringBuilder();
        StringBuilder uniCodeChars = new StringBuilder();
        StringBuilder tempTypes = new StringBuilder();
        ClassedToken lastToken = tempEx.get(tempEx.size()-1);
        String last = lastToken.getToken().originalText();
        String timexType = null;
        for (ClassedToken token : tempEx){
            temporalExpression.append(token.getToken().originalText() + " ");
            BasicFeatures tokenFeatures = token.getFeatures();
            uniCodeChars.append(tokenFeatures.unicodeCharClass);
            tempTypes.append(tokenFeatures.tempType);
            //extracting the temporal type of the temporal expression from an xml-attribute
            CoreLabel tokenXml = token.getToken();
            timexType= tokenXml.get(TimexTypeAnnotation.class);
        }
        String temporalExp = temporalExpression.substring(0, temporalExpression.length()-1);
        //creating on single String out of all String one temporal expression includes
        String oneStringTemp = temporalExp.replace(" ", "_");


        outputStream.println(escape(temporalExp)+","+escape(oneStringTemp)+","+escape(last)+","+uniCodeChars+","+tempTypes+","+timexType);

    }


    /**
     * closes an output stream
     */
    public void close(){
        outputStream.close();
    }

    /**
     * modifies a token to avoid problems with weka
     *
     * @param token
     * @return      modified token
     */
    private static String escape(String token){
        token = token.replace("\"","\\\"");
        token = token.replace("'","\u2019");
        return '"'+token+'"';
    }


}
