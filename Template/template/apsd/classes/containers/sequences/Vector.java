package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Vettore concreto a disposizione lineare. */
public class Vector<Data> extends LinearVectorBase<Vector<Data>, Data> {
    public Vector() { super(); }
    public Vector(Natural size) { super(size); }
    public Vector(TraversableContainer<?, Data> source) { super(source); }
    @Override public Vector<Data> Empty() { return new Vector<>(); }
}
