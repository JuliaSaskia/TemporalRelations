package de.uni_stuttgart.ims.temporalrelations;

import java.util.ArrayList;

/**
 * Created by julia on 20.02.16.
 */
public abstract class Writer {
    public abstract void write(ArrayList<ArrayList<ClassedToken>> annotatedSentence);

    public abstract void close();
}
