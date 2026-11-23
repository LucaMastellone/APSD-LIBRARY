package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.CircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Vettore concreto a disposizione circolare. */
public class CircularVector<Data> extends CircularVectorBase<CircularVector<Data>, Data> {
    public CircularVector() { super(); }
    public CircularVector(Natural size) { super(size); }
    public CircularVector(TraversableContainer<?, Data> source) { super(source); }
    @Override public CircularVector<Data> Empty() { return new CircularVector<>(); }
}
