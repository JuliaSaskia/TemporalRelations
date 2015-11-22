package de.uni_stuttgart.ims.temporalrelations;

import java.util.HashMap;

/**
 * Created by julia on 22.11.15.
 */
public class StringMapper {


    private HashMap<String, Integer> featureMap;
    private HashMap<Integer, String> inverseFeatureMap;

    int currentIndex = 1;

    public StringMapper(){
        this.featureMap = new HashMap<>();
        this.inverseFeatureMap = new HashMap<>();
    }



    public int lookup(String s){
        Integer value = featureMap.get(s);
        if (value == null){
            featureMap.put(s, currentIndex);
            inverseFeatureMap.put(currentIndex, s);
            return currentIndex++;
        }else{
            return value;
        }
    }


    public String inverseLookup(int index) {
        String feature = inverseFeatureMap.get(index);
        return feature;
    }

}
