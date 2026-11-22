package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

/** Interface: vettore con dimensione variabile. */
public interface DynVector<T extends DynVector<T, Data>, Data> extends ResizableContainer<T>, InsertableAtSequence<T, Data>, RemovableAtSequence<T, Data>, Vector<T, Data> {
    @Override
    default void InsertAt(Data dat, Natural pos) {
        if (pos == null || pos.ToLong() > Size().ToLong()) throw new IndexOutOfBoundsException("Invalid position: " + pos);
        Expand();
        ShiftRight(pos);
        SetAt(dat, pos);
    }
    @Override
    default Data AtNRemove(Natural pos) {
        long index = ExcIfOutOfBound(pos);
        Data old = GetAt(pos);
        ShiftLeft(Natural.Of(index + 1));
        Reduce();
        return old;
    }
    @Override
    Natural Size();
    @Override
    default T SubSequence(Natural from, Natural to) { return InsertableAtSequence.super.SubSequence(from, to); }
}
