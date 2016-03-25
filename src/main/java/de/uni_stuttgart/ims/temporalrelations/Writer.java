package de.uni_stuttgart.ims.temporalrelations;

import java.util.ArrayList;

/**
 * abstract writer class for different writing annotated sentences to file
 *
 * @author julia bettinger
 * @author jens beck
 */
public abstract class Writer {
    public abstract void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence);
    public abstract void close();
}
