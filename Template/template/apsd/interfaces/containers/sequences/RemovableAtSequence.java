package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.RemovableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import java.util.Objects;

/** Interface: Sequence con rimozione tramite posizione. */
public interface RemovableAtSequence<T extends RemovableAtSequence<T, Data>, Data> extends Sequence<T, Data>, RemovableContainer<T, Data> {
    Data AtNRemove(Natural pos);
    default void RemoveAt(Natural pos) { AtNRemove(pos); }
    default void RemoveFirst() { AtNRemove(Natural.ZERO); }
    default Data FirstNRemove() { return AtNRemove(Natural.ZERO); }
    default void RemoveLast() { LastNRemove(); }
    default Data LastNRemove() {
        if (IsEmpty()) throw new IndexOutOfBoundsException("Empty sequence");
        return AtNRemove(Size().Decrement());
    }
    @Override
    default boolean Remove(Data dat) {
        ForwardIterator<Data> it = FIterator();
        long index = 0;
        while (it.IsValid()) {
            if (Objects.equals(it.DataNNext(), dat)) { RemoveAt(Natural.Of(index)); return true; }
            index++;
        }
        return false;
    }
}
