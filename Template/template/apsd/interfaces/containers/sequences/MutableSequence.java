package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence con scrittura tramite posizione. */
public interface MutableSequence<T extends MutableSequence<T, Data>, Data> extends Sequence<T, Data>, MutableIterableContainer<T, Data> {
    default void SetAt(Data dat, Natural pos) {
        long index = ExcIfOutOfBound(pos);
        MutableForwardIterator<Data> it = FIterator();
        it.Next(index);
        it.SetCurrent(dat);
    }
    default Data GetNSetAt(Data dat, Natural pos) {
        long index = ExcIfOutOfBound(pos);
        MutableForwardIterator<Data> it = FIterator();
        it.Next(index);
        Data old = it.GetCurrent();
        it.SetCurrent(dat);
        return old;
    }
    default void SetFirst(Data dat) { SetAt(dat, Natural.ZERO); }
    default Data GetNSetFirst(Data dat) { return GetNSetAt(dat, Natural.ZERO); }
    default void SetLast(Data dat) {
        if (IsEmpty()) throw new IndexOutOfBoundsException("Empty sequence");
        SetAt(dat, Size().Decrement());
    }
    default Data GetNSetLast(Data dat) {
        if (IsEmpty()) throw new IndexOutOfBoundsException("Empty sequence");
        return GetNSetAt(dat, Size().Decrement());
    }
    default void Swap(Natural fst, Natural snd) {
        Data first = GetAt(fst);
        Data second = GetAt(snd);
        SetAt(second, fst);
        SetAt(first, snd);
    }
}
