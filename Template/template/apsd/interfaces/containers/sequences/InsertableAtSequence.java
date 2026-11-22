package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.InsertableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: Sequence con inserimento tramite posizione. */
public interface InsertableAtSequence<T extends InsertableAtSequence<T, Data>, Data> extends Sequence<T, Data>, InsertableContainer<T, Data> {
    void InsertAt(Data dat, Natural pos);
    default void InsertFirst(Data dat) { InsertAt(dat, Natural.ZERO); }
    default void InsertLast(Data dat) { InsertAt(dat, Size()); }
    @Override
    default boolean Insert(Data dat) { InsertLast(dat); return true; }
    @Override
    default T SubSequence(Natural from, Natural to) {
        T result = Empty();
        if (from == null || to == null || from.compareTo(to) > 0 || !IsInBound(from) || !IsInBound(to)) return result;
        ForwardIterator<Data> it = FIterator();
        it.Next(from);
        long count = to.ToLong() - from.ToLong() + 1;
        for (long i = 0; i < count; i++) result.InsertLast(it.DataNNext());
        return result;
    }
}
