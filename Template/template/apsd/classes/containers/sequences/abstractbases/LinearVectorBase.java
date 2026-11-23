package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import java.util.Arrays;

/** Base dei vettori a disposizione lineare. */
public abstract class LinearVectorBase<T extends LinearVectorBase<T, Data>, Data> extends VectorBase<T, Data> {
    protected LinearVectorBase() { super(); }
    protected LinearVectorBase(Natural size) { super(size); }
    protected LinearVectorBase(TraversableContainer<?, Data> source) { super(source); }

    @Override public void Realloc(Natural size) {
        arr = Arrays.copyOf(arr, Math.toIntExact(size.ToLong()));
    }
    @Override public Data GetAt(Natural pos) {
        return arr[Math.toIntExact(ExcIfOutOfBound(pos))];
    }
    @Override public void SetAt(Data value, Natural pos) {
        arr[Math.toIntExact(ExcIfOutOfBound(pos))] = value;
    }
}
