package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data> {
    Data DataNPrev();
    default void Prev() { DataNPrev(); }
    default void Prev(Natural num) { Prev(num.ToLong()); }
    default void Prev(long num) {
        if (num < 0) throw new IllegalArgumentException("Negative step count");
        for (long i = 0; i < num; i++) Prev();
    }
    default boolean ForEachBackward(Predicate<Data> pred) {
        if (pred == null) return false;
        while (IsValid()) if (pred.Apply(DataNPrev())) return true;
        return false;
    }
}
