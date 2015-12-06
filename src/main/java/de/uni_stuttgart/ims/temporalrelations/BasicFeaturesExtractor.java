package de.uni_stuttgart.ims.temporalrelations;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UCharacterCategory;
import com.ibm.icu.lang.UCharacterEnums;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 *extracts first 5 features of our given paper
 *
 * Created by julia on 22.11.15.
 */
public class BasicFeaturesExtractor {


    /**
     * getText returns text of a token
     * @param token
     * @return
     */
    public static String getText(CoreLabel token) {
        String word = token.word();
        return word;
    }

    /**
     * getLemma returns lemma of a token
     * @param token
     * @return
     */
    public static String getLemma(CoreLabel token) {
        String lemma = token.lemma();
        return lemma;
    }

    /**
     * getPOS returns the part-of-speech-tag of a token
     * @param token
     * @return
     */
    public static String getPOS(CoreLabel token) {
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
        return pos;
    }

    /**
     * maps unicode character classes to abbreviations
     * @param type
     * @return
     */
    private static String toUnicodeClassAbbrv(int type) {
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

    /**
     * returns the unicode char classes concatenated of a token
     * and with duplicates merged
     * @param token
     * @return
     */
    public static String getUnicodeCharClasses(CoreLabel token) {
        String word = token.originalText();
        //every unicode character has a codepoint number
        //get the codepoints of the String word as a stream of ints
        IntStream codePoints = word.codePoints();
        StringBuilder builder = new StringBuilder();
        //execute the accept method of the anonymous inner class
        //implementing IntConsumer on each code point
        codePoints.forEach(new IntConsumer() {
            private int lastType = -1;
            //get the unicode character class of each codepoint
            //maps the abbreviations to the classes
            //and merges duplicates
            @Override
            public void accept(int codePoint) {
                int type = UCharacter.getType(codePoint);
                if (type != lastType) {
                    String cClass = toUnicodeClassAbbrv(type);
                    builder.append(cClass);
                    lastType = type;
                }
            }
        });
        return builder.toString();
    }


    /**
     * returns the temporal type of each token if existing
     * else noTempType
     * @param token
     * @return
     */
    public static String getTempType(CoreLabel token) {
        String tempType;
        String word = token.word().toLowerCase();
        switch (word) {
            case "monday":
            case "tuesday":
            case "wednesday":
            case "thursday":
            case "friday":
            case "saturday":
            case "sunday":
            case "day":
            case "days":
                tempType = "weekday";
                break;
            case "january":
            case "february":
            case "march":
            case "april":
            case "may":
            case "june":
            case "july":
            case "august":
            case "september":
            case "october":
            case "november":
            case "december":
            case "month":
            case "months":
                tempType = "month";
                break;
            case "spring":
            case "summer":
            case "fall":
            case "autumn":
            case "winter":
            case "season":
            case "seasons":
                tempType = "season";
                break;
            case "week":
            case "weeks":
                tempType = "week";
                break;
            case "second":
            case "seconds":
            case "minute":
            case "minutes":
            case "hour":
            case "hours":
                tempType = "withinDay";
                break;
            case "today":
            case "tomorrow":
            case "yesterday":
                tempType = "viewFromToday";
                break;
            case "morning":
            case "noon":
            case "afternoon":
            case "evening":
                tempType = "duringDay";
                break;
            case "night":
            case "midnight":
                tempType = "duringNight";
                break;
            case "now":
            case "current":
            case "currently":
                tempType = "current";
                break;
            case "century":
            case "centuries":
                tempType = "century";
                break;
            case "decade":
            case "decades":
                tempType = "decade";
                break;
            case "year":
            case "years":
                tempType = "year";
                break;
            case "quarter":
            case "quarters":
                tempType = "quarter";
                break;
            default:
                tempType = "noTempType";
        }
        return tempType;
    }

}