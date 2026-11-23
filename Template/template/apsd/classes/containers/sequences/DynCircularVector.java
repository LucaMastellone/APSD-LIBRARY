package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Vettore dinamico a disposizione circolare. */
public class DynCircularVector<Data> extends DynCircularVectorBase<DynCircularVector<Data>, Data> {
    public DynCircularVector() { super(); }
    public DynCircularVector(Natural capacity) { super(capacity); }
    public DynCircularVector(TraversableContainer<?, Data> source) { super(source); }
    @Override public DynCircularVector<Data> Empty() { return new DynCircularVector<>(); }
}
