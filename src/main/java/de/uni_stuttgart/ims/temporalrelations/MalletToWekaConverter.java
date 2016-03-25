package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreLabel;

import java.io.*;
import java.util.ArrayList;

/**
 * class for reading in the output of applying our mallet model on a test file
 * makes a test file for weka out of the results of the mallet testing
 *
 * @author julia bettinger
 * @author jens beck
 */
public class MalletToWekaConverter {

    private PrintWriter outputStream;

    public MalletToWekaConverter(String fileName) throws FileNotFoundException {
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
     * reads in a annotated by our mallet model test file
     * writes a test file for weka to determine the types of the temporal relations
     * takes only the temporal expessions into account (tagged with B or I), disregards O (according to the file read in)
     * each line represents a temporal expression followed by the features, a question mark is at the last position
     * necessary for weka
     *
     * @param filename
     */
    public void readMalletPasted(String filename){
        outputStream.println(header());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String[]> tempEx = null;
            String previousLast = null;
            while ((line = reader.readLine()) != null) {
                String[] tokenLine = line.split("\\s+");
                String lastToken = null;
                if(tokenLine.length != 0) {
                    lastToken = tokenLine[tokenLine.length - 1];
                    //only taking temporal expressions into account
                    if (lastToken.equals("B") || lastToken.equals("I")) {
                        if (previousLast == null || previousLast.equals("O")) {
                            tempEx = new ArrayList<>();
                        }
                        tempEx.add(tokenLine);

                    } else {
                        if (tempEx != null) {
                            writeFeatures(tempEx);
                            tempEx = null;
                        }
                    }
                    previousLast = tokenLine[tokenLine.length - 1];
                }
            }

            if(tempEx != null) {
                writeFeatures(tempEx);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * writes all features of a temporal expression in one line including a questionmark at the last position representing the
     * unknown temporal type
     *
     * @param tempEx       all tokens of one temporal expression in an ArrayList
     */
    public void writeFeatures(ArrayList<String[]> tempEx){
        StringBuilder temporalExpression = new StringBuilder();
        StringBuilder uniCodeChars = new StringBuilder();
        StringBuilder tempTypes = new StringBuilder();

        for(String line[] : tempEx){
            temporalExpression.append(line[0] + " ");
            uniCodeChars.append(line[3]);
            tempTypes.append(line[4]);


        }
        String temporalExp = temporalExpression.substring(0, temporalExpression.length()-1);
        String oneStringTemp = temporalExp.replace(" ", "_");
        String[]lastLine = tempEx.get(tempEx.size()-1);
        String last = lastLine[0];

        outputStream.println(escape(temporalExp)+","+escape(oneStringTemp)+","+escape(last)+","+uniCodeChars+","+tempTypes+","+"?");
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
