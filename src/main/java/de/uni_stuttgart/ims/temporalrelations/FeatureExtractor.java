package de.uni_stuttgart.ims.temporalrelations;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UCharacterCategory;
import com.ibm.icu.lang.UCharacterEnums;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by julia on 22.11.15.
 */
public class FeatureExtractor {



    public static String getText(CoreLabel token){
        String word = token.word();
        return "word="+word;
    }

    public static String getStem(CoreLabel token){
        //this release version of coreNLP has no stemmer yet
        //there is a stemmer on github: https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/process/Stemmer.java
        //maybe we could use the lemma?!
        return null;
    }

    public static String getPOS(CoreLabel token){
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
        return "POS="+pos;
    }

    private static String toUnicodeClassAbbrv(int type){
        switch (type) {
            case UCharacterEnums.ECharacterCategory.COMBINING_SPACING_MARK:
                return "Mc";
            case UCharacterEnums.ECharacterCategory.CONNECTOR_PUNCTUATION:
                return "Pc";
            case UCharacterEnums.ECharacterCategory.CONTROL:
                return "Cc";
            case UCharacterEnums.ECharacterCategory.CURRENCY_SYMBOL:
                return "Sc";
            case UCharacterEnums.ECharacterCategory.DASH_PUNCTUATION:
                return "Pd";
            case UCharacterEnums.ECharacterCategory.DECIMAL_DIGIT_NUMBER:
                return "Nd";
            case UCharacterEnums.ECharacterCategory.ENCLOSING_MARK:
                return "Me";
            case UCharacterEnums.ECharacterCategory.END_PUNCTUATION:
                return "Pe";
            case UCharacterEnums.ECharacterCategory.FINAL_PUNCTUATION:
                return "Pf";
            case UCharacterEnums.ECharacterCategory.FORMAT:
                return "Cf";
            case UCharacterEnums.ECharacterCategory.INITIAL_PUNCTUATION:
                return "Pi";
            case UCharacterEnums.ECharacterCategory.LETTER_NUMBER:
                return "Nl";
            case UCharacterEnums.ECharacterCategory.LINE_SEPARATOR:
                return "Zl";
            case UCharacterEnums.ECharacterCategory.LOWERCASE_LETTER:
                return "Ll";
            case UCharacterEnums.ECharacterCategory.MATH_SYMBOL:
                return "Sm";
            case UCharacterEnums.ECharacterCategory.MODIFIER_LETTER:
                return "Lm";
            case UCharacterEnums.ECharacterCategory.MODIFIER_SYMBOL:
                return "Sk";
            case UCharacterEnums.ECharacterCategory.NON_SPACING_MARK:
                return "Mn";
            case UCharacterEnums.ECharacterCategory.OTHER_LETTER:
                return "Lo";
            case UCharacterEnums.ECharacterCategory.OTHER_NUMBER:
                return "No";
            case UCharacterEnums.ECharacterCategory.OTHER_PUNCTUATION:
                return "Po";
            case UCharacterEnums.ECharacterCategory.OTHER_SYMBOL:
                return "So";
            case UCharacterEnums.ECharacterCategory.PARAGRAPH_SEPARATOR:
                return "Zp";
            case UCharacterEnums.ECharacterCategory.PRIVATE_USE:
                return "Co";
            case UCharacterEnums.ECharacterCategory.SPACE_SEPARATOR:
                return "Zs";
            case UCharacterEnums.ECharacterCategory.START_PUNCTUATION:
                return "Ps";
            case UCharacterEnums.ECharacterCategory.SURROGATE:
                return "Cs";
            case UCharacterEnums.ECharacterCategory.TITLECASE_LETTER:
                return "Lt";
            case UCharacterEnums.ECharacterCategory.UPPERCASE_LETTER:
                return "Lu";
            default:
                return "XX";
        }
    }

    public static String getUnicodeCharClasses(CoreLabel token){
        String word = token.originalText();
        IntStream codePoints = word.codePoints();
        StringBuilder b = new StringBuilder();
        codePoints.forEach(new IntConsumer() {
            private int lastType = -1;
            @Override
            public void accept(int codePoint) {
                int type = UCharacter.getType(codePoint);
                if(type != lastType) {
                    String cClass = toUnicodeClassAbbrv(type);
                    b.append(cClass);
                    lastType = type;
                }
            }
        });
        return b.toString();
    }


    public static String getTempType(CoreLabel token){
        //what is this 58-word gazetteer of time words???
        return null;
    }


    public static String getPrevNextFeatures(CoreLabel token){

        return null;
    }
}
