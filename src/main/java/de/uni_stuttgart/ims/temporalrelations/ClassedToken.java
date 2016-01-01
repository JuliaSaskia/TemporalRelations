package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreLabel;

/**
 * Created by julia on 01.01.16.
 */
public class ClassedToken {
    public enum TimeMlClass{
        UNKNOWN,
        TIME,
        EVENT,
        TEXT
    }

    private CoreLabel token;

    private BasicFeatures features;

    private TimeMlClass classification;


    public ClassedToken(CoreLabel tok){
        this.token = tok;
    }


    public BasicFeatures getFeatures() {
        return features;
    }

    public void setFeatures(BasicFeatures features) {
        this.features = features;
    }

    public TimeMlClass getClassification() {
        return classification;
    }

    public void setClassification(TimeMlClass classification) {
        this.classification = classification;
    }

    public CoreLabel getToken() {
        return token;
    }

    public void setToken(CoreLabel token) {
        this.token = token;
    }

}
