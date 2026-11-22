package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data> {
    Data DataNNext();
    default void Next() { DataNNext(); }
    default void Next(Natural num) { Next(num.ToLong()); }
    default void Next(long num) {
        if (num < 0) throw new IllegalArgumentException("Negative step count");
        for (long i = 0; i < num; i++) Next();
    }
    default boolean ForEachForward(Predicate<Data> pred) {
        if (pred == null) return false;
        while (IsValid()) if (pred.Apply(DataNNext())) return true;
        return false;
    }
}
