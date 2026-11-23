package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.DynVector;
import java.util.Arrays;

/** Base dinamica con memorizzazione lineare. */
public abstract class DynLinearVectorBase<T extends DynLinearVectorBase<T, Data>, Data> extends LinearVectorBase<T, Data> implements DynVector<T, Data> {
    protected long size = 0;
    protected DynLinearVectorBase() { super(); }
    protected DynLinearVectorBase(Natural capacity) { super(capacity); }
    protected DynLinearVectorBase(TraversableContainer<?, Data> source) { super(source); size = source.Size().ToLong(); }
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
        int next = Math.toIntExact(size - amount.ToLong());
        Arrays.fill(arr, next, Math.toIntExact(size), null);
        size = next;
        Shrink();
    }
}
