package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * extracts features for one sentence and saves features
 * in an ArrayList
 * Created by julia on 22.11.15.
 */
public class StringMapper {


    private StringMapper(){
    }

    /**
     * returns ArrayList of features of each token
     * @param sentence
     * @return
     */
    public static ArrayList<ClassedToken> extractFeaturesSentence(List<CoreLabel> sentence){
        ArrayList<ClassedToken> featuresSentence = new ArrayList<>();
        for (CoreLabel token : sentence){

            List<String> xmlTags = token.get(CoreAnnotations.XmlContextAnnotation.class);
            String xmlTagsLast = xmlTags.get(xmlTags.size()-1);
            ClassedToken.TimeMlClass classification = ClassedToken.TimeMlClass.UNKNOWN;

            if (xmlTagsLast.equals("TIMEX3")){
                classification = ClassedToken.TimeMlClass.TIME;
            } else if (xmlTagsLast.equals("EVENT")) {
                classification = ClassedToken.TimeMlClass.EVENT;
            } else if (xmlTagsLast.equals("TEXT")) {
                classification = ClassedToken.TimeMlClass.TEXT;
            } else {
                continue;
            }

            String word = BasicFeaturesExtractor.getText(token);

            String lemma = BasicFeaturesExtractor.getLemma(token);

            String POS = BasicFeaturesExtractor.getPOS(token);

            String unicodeCharClass = BasicFeaturesExtractor.getUnicodeCharClasses(token);

            String tempType = BasicFeaturesExtractor.getTempType(token);

            BasicFeatures features = new BasicFeatures(word, lemma, POS, unicodeCharClass, tempType);

            ClassedToken cToken= new ClassedToken(token);

            cToken.setToken(token);
            cToken.setFeatures(features);
            cToken.setClassification(classification);

            featuresSentence.add(cToken);
        }
        //add the 6th feature (all features above for the context of the token) to the ArrayList

        for (int i = 0; i < featuresSentence.size(); i++){
            BasicFeatures current = featuresSentence.get(i).getFeatures();
            //up to 3 tokens before the current token
            for (int j = Math.max(0,i-3); j < i; j++){
                //extracting features in the context
                current.prev[j-i+3]=featuresSentence.get(j).getFeatures();
            }
            //up to 3 tokens after the current token
            for (int j = i+1; j < Math.min(i+4,featuresSentence.size()); j++){
                //extracting features in the context
                current.next[j-i-1]=featuresSentence.get(j).getFeatures();
            }
        }
        return featuresSentence;
    }

}
