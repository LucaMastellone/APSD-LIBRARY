package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;

/** Base dei vettori con indirizzamento circolare. */
public abstract class CircularVectorBase<T extends CircularVectorBase<T, Data>, Data> extends VectorBase<T, Data> {
    protected long start = 0;
    protected CircularVectorBase() { super(); }
    protected CircularVectorBase(Natural size) { super(size); }
    protected CircularVectorBase(TraversableContainer<?, Data> source) { super(source); }
    private int physical(long pos) { return (int) ((start + pos) % arr.length); }
    @Override public Data GetAt(Natural pos) { return arr[physical(ExcIfOutOfBound(pos))]; }
    @Override public void SetAt(Data value, Natural pos) { arr[physical(ExcIfOutOfBound(pos))] = value; }
    @Override protected void ArrayAlloc(Natural capacity) { super.ArrayAlloc(capacity); start = 0; }
    @Override public void Realloc(Natural capacity) {
        int length = Math.toIntExact(capacity.ToLong());
        Data[] old = arr;
        long oldStart = start;
        ArrayAlloc(capacity);
        int count = Math.min(old.length, length);
        for (int i = 0; i < count; i++) arr[i] = old[(int) ((oldStart + i) % old.length)];
        start = 0;
    }
    @Override public void ShiftLeft(Natural pos, Natural num) {
        long size = Size().ToLong(), n = num.ToLong();
        if (pos.IsZero() && size == arr.length && size > 0 && n < size) {
            for (long i = 0; i < n; i++) arr[physical(i)] = null;
            start = (start + n) % size;
        } else ShiftLeft(pos, Size(), num);
    }
    @Override public void ShiftRight(Natural pos, Natural num) {
        long size = Size().ToLong(), n = num.ToLong();
        if (pos.IsZero() && size == arr.length && size > 0 && n < size) {
            for (long i = size - n; i < size; i++) arr[physical(i)] = null;
            start = (start + size - n) % size;
        } else ShiftRight(pos, Size(), num);
    }
}
