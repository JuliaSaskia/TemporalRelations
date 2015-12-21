package de.uni_stuttgart.ims.temporalrelations;

/**
 * Created by julia on 21.12.15.
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

    public String toString(){
        StringBuilder builder = new StringBuilder();
        String complete = this.word+","+this.lemma+","+this.POS+","+this.unicodeCharClass+","+this.tempType;
        builder.append(complete);
        for(BasicFeatures features : prev){
            if(features != null) {
                builder.append(","+features.word + "," + features.lemma + "," + features.POS + "," + features.unicodeCharClass + "," + features.tempType);
            }else{
                builder.append(","+null+","+null+","+null+","+null+","+null);
            }
        }
        for(BasicFeatures features : next){
            if(features != null) {
                builder.append(","+features.word + "," + features.lemma + "," + features.POS + "," + features.unicodeCharClass + "," + features.tempType);
            }else{
                builder.append(","+null+","+null+","+null+","+null+","+null);
            }
        }

        return builder.toString();
    }

    public static String header(){
        return "word,lemma,POS,unicodeCharClass,tempType," +
                "prevprevprevword,prevprevprevlemma,prevprevprevPOS,prevprevprevunicodeCharClass,prevprevprevtempType," +
                "prevprevword,prevprevlemma,prevprevPOS,prevprevunicodeCharClass,prevprevtempType," +
                "prevword,prevlemma,prevPOS,prevunicodeCharClass,prevtempType," +
                "nextword,nextlemma,nextPOS,nextunicodeCharClass,nexttempType," +
                "nextnextword,nextnextlemma,nextnextPOS,nextnextunicodeCharClass,nextnexttempType," +
                "nextnextnextword,nextnextnextlemma,nextnextnextPOS,nextnextnextunicodeCharClass,nextnextnexttempType,";
    }
}
