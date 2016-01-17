package de.uni_stuttgart.ims.temporalrelations;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by julia on 17.01.16.
 */
public class WekaLearn {

    public WekaLearn(){
    }

    private Instances instances;

    private Classifier model;

    public void load() throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("Data/aquaint.csv"));
        instances = loader.getDataSet();
        if (instances.classIndex() == -1){
            instances.setClassIndex(instances.numAttributes() - 1);
        }

    }

    public void train(){
        model = (Classifier) new NaiveBayes();
        try {
            model.buildClassifier(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cTest(){
        Evaluation eTest;
        try {
            eTest = new Evaluation(instances);
            eTest.crossValidateModel(model, instances, 10, new Random(1));
            String strSummary = eTest.toSummaryString();
            System.out.println(strSummary);
            double[][] cmMatrix = eTest.confusionMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
