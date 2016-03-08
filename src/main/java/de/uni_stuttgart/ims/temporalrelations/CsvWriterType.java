package de.uni_stuttgart.ims.temporalrelations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by julia on 05.03.16.
 */
public class CsvWriterType {
    private PrintWriter outputStream;

    public CsvWriterType(String fileName) throws FileNotFoundException {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }



}
