package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Vettore dinamico a disposizione lineare. */
public class DynVector<Data> extends DynLinearVectorBase<DynVector<Data>, Data> {
    public DynVector() { super(); }
    public DynVector(Natural capacity) { super(capacity); }
    public DynVector(TraversableContainer<?, Data> source) { super(source); }
    @Override public DynVector<Data> Empty() { return new DynVector<>(); }
}
