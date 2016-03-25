package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * class basic annotations provides methods for doing basic annotations
 * on sentence data sets
 *
 * @author julia bettinger
 * @author jens beck
 *
 */
public class BasicAnnotations {

    //StanfordNLP pipeline used for preprocessing
    private StanfordCoreNLP pipeline;

    /**
    constructor initialized with the annotations to be done on the data for preprocessing
     */
    public BasicAnnotations(){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, cleanxml, ssplit, pos, lemma");
        props.setProperty("clean.tokenAnnotations","de.uni_stuttgart.ims.temporalrelations.TimexTypeAnnotation=timex3[type]");
        this.pipeline = new StanfordCoreNLP(props);
    }


    /**
     * readDir reads in all files which are in dirname
     * @param dirname   name of the directory which is read
     * @return          String list of the content of each file in the directory
     */
    private List<String> readDir(String dirname){
        File directory = new File(dirname);
        if (!directory.isDirectory()){
            System.err.println(dirname+" is not a directory!!!");
            System.exit(5);
            return null;
        }
        //list of file contents with one file per String
        List<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(dirname), "*.tml")) {
            for (Path path : dirStream) {
                BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                //used to combine lines of a file into a single String
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                fileList.add(stringBuilder.toString());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: "+dirname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Directory listing failed: "+e.getMessage());
            System.exit(1);
        }

        return fileList;
    }

    /**
     * annotate creates basic annotations on the given documents
     * @param dirnames  list of the directory names to be read in
     * @return          list of the text with all chosen annotations
     */
    public List<Annotation> annotate(List<String> dirnames) {
        List<Annotation> annotatedDocuments = new ArrayList<>();
        for (String dirname : dirnames) {
            List<String> inputs = readDir(dirname);
            for (String input : inputs) {
                Annotation document = new Annotation(input);
                pipeline.annotate(document);
                annotatedDocuments.add(document);
            }
        }
        return annotatedDocuments;
    }

}
