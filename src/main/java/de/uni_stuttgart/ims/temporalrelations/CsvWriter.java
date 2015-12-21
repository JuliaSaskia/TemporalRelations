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

    public static void writeToFile(ArrayList<ArrayList<BasicFeatures>> annotatedSentence, String fileName) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
            outputStream.println(BasicFeatures.header());
            for (ArrayList<BasicFeatures> basicFeatures : annotatedSentence) {
                for (BasicFeatures features : basicFeatures) {
                    outputStream.println(features);
                }
            }

        } catch (IOException e) {
            System.out.println("Could not open or create file" + fileName);
        } finally {
            outputStream.close();
        }



    }

}
