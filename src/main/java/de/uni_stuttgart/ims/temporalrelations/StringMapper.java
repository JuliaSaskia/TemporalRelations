package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * extracts features for one sentence and saves features
 * in an ArrayList where the features of every token
 * are represented in a hashmap
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
    public static ArrayList<BasicFeatures> extractFeaturesSentence(List<CoreLabel> sentence){
        ArrayList<BasicFeatures> featuresSentence = new ArrayList<>();
        for (CoreLabel token : sentence){

            String word = BasicFeaturesExtractor.getText(token);

            String lemma = BasicFeaturesExtractor.getLemma(token);

            String POS = BasicFeaturesExtractor.getPOS(token);

            String unicodeCharClass = BasicFeaturesExtractor.getUnicodeCharClasses(token);

            String tempType = BasicFeaturesExtractor.getTempType(token);

            featuresSentence.add(new BasicFeatures(word, lemma, POS, unicodeCharClass, tempType));
        }
        //add the 6th feature (all features above for the context of the token) to the ArrayList
        for (int i = 0; i < featuresSentence.size(); i++){
            BasicFeatures current = featuresSentence.get(i);
            //up to 3 tokens before the current token
            for (int j = Math.max(0,i-3); j < i; j++){
                //extracting features in the context
                current.prev[j-i+3]=featuresSentence.get(j);
            }
            //up to 3 tokens after the current token
            for (int j = Math.max(i+1,i+4); j < i+4; j++){
                //extracting features in the context
                current.next[j-i-1]=featuresSentence.get(j);
            }

        }
        return featuresSentence;
    }

}
