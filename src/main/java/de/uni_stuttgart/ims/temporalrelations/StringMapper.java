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
     * returns ArrayList of features of each token represented in hashmaps
     * @param sentence
     * @return
     */
    public static ArrayList<HashMap<String, String>> extractFeaturesSentence(List<CoreLabel> sentence){
        ArrayList<HashMap<String, String>> featuresSentence = new ArrayList<>();
        for (CoreLabel token : sentence){
            HashMap<String, String> featuresToken = new HashMap<>();

            String text = BasicFeaturesExtractor.getText(token);
            featuresToken.put("word=", text);

            String lemma = BasicFeaturesExtractor.getLemma(token);
            featuresToken.put("lemma=", lemma);

            String pos = BasicFeaturesExtractor.getPOS(token);
            featuresToken.put("POS=", pos);

            String unicodeClass = BasicFeaturesExtractor.getUnicodeCharClasses(token);
            featuresToken.put("unicodeCharClas=", unicodeClass);

            String tempType = BasicFeaturesExtractor.getTempType(token);
            featuresToken.put("tempType=", tempType);

            featuresSentence.add(featuresToken);
        }
        //add the 6th feature (all features above for the context of the token) to the ArrayList
        ArrayList<HashMap<String, String>> context = new ArrayList<>();
        //iterating over each feature hashmap
        for (int i = 0; i < featuresSentence.size(); i++){
            HashMap<String, String> contextFeatures = new HashMap<>();
            //up to 3 tokens before the current token
            for (int j = Math.max(0,i-3); j < i; j++){
                //extracting features in the context
                for (Map.Entry<String, String> entry : featuresSentence.get(i).entrySet()){
                    contextFeatures.put("prev"+Integer.toString(i-j)+entry.getKey(), entry.getValue());
                }
            }
            //up to 3 tokens after the current token
            for (int j = Math.max(i+1,i+4); j < i+4; j++){
                //extracting features in the context
                for (Map.Entry<String, String> entry : featuresSentence.get(i).entrySet()){
                    contextFeatures.put("next"+Integer.toString(j-i)+entry.getKey(), entry.getValue());
                }

            }
            context.add(contextFeatures);

        }
        //merge context and featureSentences
        for (int i = 0; i < context.size(); i++) {
            featuresSentence.get(i).putAll(context.get(i));
        }

        return featuresSentence;
    }



}
