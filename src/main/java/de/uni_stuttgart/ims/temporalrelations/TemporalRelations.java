package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TemporalRelations {


  public static List<String> readDir(String dirname){
    File directory = new File(dirname);
    if (!directory.isDirectory()){
      System.err.println(dirname+" is not a directory!!!");
      System.exit(5);
      return null;
    }
    List<String> fileList = new ArrayList<>();
    for (File file:directory.listFiles(name->name.getName().endsWith(".tml"))){
      try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder b = new StringBuilder();
        String line;
        while((line = reader.readLine())!=null){
          b.append(line);
        }
        fileList.add(b.toString());
      } catch (FileNotFoundException e) {
        //ignore files which vanished
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return fileList;
  }


  public static void annotateTest(StanfordCoreNLP pipeline, List<String> inputs){
    for(String input: inputs) {
      // create an empty Annotation just with the given text
      Annotation document = new Annotation(input);

      // run all Annotators on this text
      pipeline.annotate(document);

      // these are all the sentences in this document
      // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
      List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

      for (CoreMap sentence : sentences) {
        // traversing the words in the current sentence
        // a CoreLabel is a CoreMap with additional token-specific methods
        for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
          // this is the text of the token
          String word = token.get(CoreAnnotations.TextAnnotation.class);
          // this is the POS tag of the token
          String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
          // this is the NER label of the token
          String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

          System.out.println("word: " + word);
          System.out.println("POS-tag: " + pos);
          System.out.println("NER: " + ne);
        }

        System.out.println("-------------------SENTENCE-------------------");
      }
      System.out.println("-------------------FILE-----------------------");
    }

  }





  public static void main(String[] args) {




    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, cleanxml, ssplit, pos, lemma, ner");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    List<String> inputs = readDir("TimeBank");
    annotateTest(pipeline, inputs);



  }

}
