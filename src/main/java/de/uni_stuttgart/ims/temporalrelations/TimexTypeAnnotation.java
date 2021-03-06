package de.uni_stuttgart.ims.temporalrelations;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.ErasureUtils;
import edu.stanford.nlp.util.Pair;
import edu.stanford.nlp.util.TypesafeMap;

/**
 * This class is used as a Key in a CoreMap, to associate timex information. Apart from that
 * it does nothing.
 *
 * @author julia bettinger
 * @author jens beck
 *
 */
public class TimexTypeAnnotation implements CoreAnnotation<String> {

    private TimexTypeAnnotation(){}

    @Override
    public Class<String> getType() {
        return (Class) ErasureUtils.uncheckedCast(String.class);
    }
}
