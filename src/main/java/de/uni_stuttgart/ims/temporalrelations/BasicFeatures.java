package de.uni_stuttgart.ims.temporalrelations;

/**
 * stores all features for one token
 *
 * @author julia bettinger
 * @author jens beck
 */
public class BasicFeatures {

    public String word;

    public String lemma;

    public String POS;

    public String unicodeCharClass;

    public String tempType;

    public BasicFeatures[] prev;

    public BasicFeatures[] next;

    public BasicFeatures(String word, String lemma, String POS, String unicodeCharClass, String tempType){
        this.word = word;
        this.lemma = lemma;
        this.POS = POS;
        this.unicodeCharClass = unicodeCharClass;
        this.tempType = tempType;
        this.prev = new BasicFeatures[3];
        this.next = new BasicFeatures[3];
    }

}
