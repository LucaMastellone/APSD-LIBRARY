package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;

/** Base dinamica con memorizzazione circolare. */
public abstract class DynCircularVectorBase<T extends DynCircularVectorBase<T, Data>, Data> extends CircularVectorBase<T, Data> implements DynVector<T, Data> {
    protected long size = 0;
    protected DynCircularVectorBase() { super(); }
    protected DynCircularVectorBase(Natural capacity) { super(capacity); }
    protected DynCircularVectorBase(TraversableContainer<?, Data> source) { super(source); size = source.Size().ToLong(); }
    @Override protected void ArrayAlloc(Natural capacity) { super.ArrayAlloc(capacity); }
    @Override public Natural Size() { return Natural.Of(size); }
    @Override public void Clear() { size = 0; super.Realloc(Natural.ZERO); }
    @Override public void Realloc(Natural capacity) {
        super.Realloc(capacity);
        size = Math.min(size, capacity.ToLong());
    }
    @Override public void Expand(Natural amount) {
        long next = Math.addExact(size, amount.ToLong());
        if (next == 0 && arr.length == 0) Realloc(Natural.ONE); else if (next >= arr.length) Grow(amount);
        size = next;
    }
    @Override public void Reduce(Natural amount) {
        if (amount.ToLong() > size) throw new IndexOutOfBoundsException("Reduction exceeds size");
        long next = size - amount.ToLong();
        for (long i = next; i < size; i++) SetAt(null, Natural.Of(i));
        size = next;
        Shrink();
    }
}
