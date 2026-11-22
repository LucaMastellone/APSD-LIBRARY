package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;
import java.util.Objects;

/** Interface: IterableContainer con lettura e ricerca per posizione. */
public interface Sequence<T extends Sequence<T, Data>, Data> extends IterableContainer<T, Data> {
    default boolean IsInBound(Natural pos) { return pos != null && pos.compareTo(Size()) < 0; }
    default long ExcIfOutOfBound(Natural pos) {
        if (!IsInBound(pos)) throw new IndexOutOfBoundsException("Invalid position: " + pos);
        return pos.ToLong();
    }
    default Data GetAt(Natural pos) {
        long index = ExcIfOutOfBound(pos);
        ForwardIterator<Data> it = FIterator();
        it.Next(index);
        return it.GetCurrent();
    }
    default Data GetFirst() { return GetAt(Natural.ZERO); }
    default Data GetLast() {
        if (IsEmpty()) throw new IndexOutOfBoundsException("Empty sequence");
        return GetAt(Size().Decrement());
    }
    /**
     * Cerca il dato in ordine di sequenza, confrontando anche eventuali valori null.
     * @return la posizione della prima occorrenza, oppure null se il dato è assente
     */
    default Natural Search(Data dat) {
        ForwardIterator<Data> it = FIterator();
        long index = 0;
        while (it.IsValid()) {
            if (Objects.equals(it.DataNNext(), dat)) return Natural.Of(index);
            index++;
        }
        return null;
    }
    default boolean IsEqual(Sequence<?, Data> seq) {
        if (seq == null || !Size().equals(seq.Size())) return false;
        ForwardIterator<Data> left = FIterator();
        ForwardIterator<Data> right = seq.FIterator();
        while (left.IsValid() && right.IsValid())
            if (!Objects.equals(left.DataNNext(), right.DataNNext())) return false;
        return !left.IsValid() && !right.IsValid();
    }
    T SubSequence(Natural from, Natural to);
}
