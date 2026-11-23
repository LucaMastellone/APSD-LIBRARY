package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.sequences.Vector;
import java.util.NoSuchElementException;

/** Base comune ai vettori, con iteratori sulle posizioni logiche. */
public abstract class VectorBase<T extends VectorBase<T, Data>, Data> implements Vector<T, Data> {
    protected Data[] arr;

    protected VectorBase() { ArrayAlloc(Natural.ZERO); }
    protected VectorBase(Natural capacity) { ArrayAlloc(capacity); }
    protected VectorBase(TraversableContainer<?, Data> source) {
        ArrayAlloc(source.Size());
        final int[] index = {0};
        source.TraverseForward(value -> { arr[index[0]++] = value; return false; });
    }

    @SuppressWarnings("unchecked")
    protected void ArrayAlloc(Natural capacity) {
        arr = (Data[]) new Object[Math.toIntExact(capacity.ToLong())];
    }

    @Override public Natural Capacity() { return Natural.Of(arr.length); }
    @Override public void Clear() { Realloc(Natural.ZERO); }
    @Override public abstract Data GetAt(Natural position);
    @Override public abstract void SetAt(Data value, Natural position);

    protected class VectorFIterator implements MutableForwardIterator<Data> {
        private long position = 0;
        public boolean IsValid() { return position < Size().ToLong(); }
        public void Reset() { position = 0; }
        public Data GetCurrent() {
            if (!IsValid()) throw new NoSuchElementException();
            return GetAt(Natural.Of(position));
        }
        public void SetCurrent(Data value) {
            if (!IsValid()) throw new NoSuchElementException();
            SetAt(value, Natural.Of(position));
        }
        public Data DataNNext() { Data value = GetCurrent(); position++; return value; }
    }
    protected class VectorBIterator implements MutableBackwardIterator<Data> {
        private long position = Size().ToLong() - 1;
        public boolean IsValid() { return position >= 0 && position < Size().ToLong(); }
        public void Reset() { position = Size().ToLong() - 1; }
        public Data GetCurrent() {
            if (!IsValid()) throw new NoSuchElementException();
            return GetAt(Natural.Of(position));
        }
        public void SetCurrent(Data value) {
            if (!IsValid()) throw new NoSuchElementException();
            SetAt(value, Natural.Of(position));
        }
        public Data DataNPrev() { Data value = GetCurrent(); position--; return value; }
    }
    @Override public MutableForwardIterator<Data> FIterator() { return new VectorFIterator(); }
    @Override public MutableBackwardIterator<Data> BIterator() { return new VectorBIterator(); }
}
