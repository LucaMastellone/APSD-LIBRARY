package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ReallocableContainer;

/** Interface: vettore modificabile e riallocabile. */
public interface Vector<T extends Vector<T, Data>, Data> extends MutableSequence<T, Data>, ReallocableContainer<T> {
    default void ShiftLeft(Natural pos) { ShiftLeft(pos, Natural.ONE); }
    default void ShiftLeft(Natural pos, Natural num) { ShiftLeft(pos, Size(), num); }
    default void ShiftLeft(Natural start, Natural end, Natural num) {
        long s = start.ToLong(), e = end.ToLong(), n = num.ToLong(), size = Size().ToLong();
        if (s > e || e > size) throw new IndexOutOfBoundsException("Invalid range");
        if (n == 0 || s == e) return;
        for (long i = s; i < e; i++)
            if (i >= n) SetAt(GetAt(Natural.Of(i)), Natural.Of(i - n));
        long clearFrom = Math.max(s, e > n ? e - n : 0);
        for (long i = clearFrom; i < e; i++) SetAt(null, Natural.Of(i));
    }
    default void ShiftFirstLeft() { ShiftLeft(Natural.ZERO); }
    default void ShiftLastLeft() { if (!IsEmpty()) ShiftLeft(Size().Decrement()); }
    default void ShiftRight(Natural pos) { ShiftRight(pos, Natural.ONE); }
    default void ShiftRight(Natural pos, Natural num) { ShiftRight(pos, Size(), num); }
    default void ShiftRight(Natural start, Natural end, Natural num) {
        long s = start.ToLong(), e = end.ToLong(), n = num.ToLong(), size = Size().ToLong();
        if (s > e || e > size) throw new IndexOutOfBoundsException("Invalid range");
        if (n == 0 || s == e) return;
        for (long i = e; i > s;) {
            i--;
            if (n < size - i) SetAt(GetAt(Natural.Of(i)), Natural.Of(i + n));
        }
        long count = Math.min(n, e - s);
        for (long i = 0; i < count; i++) SetAt(null, Natural.Of(s + i));
    }
    default void ShiftFirstRight() { ShiftRight(Natural.ZERO); }
    default void ShiftLastRight() { if (!IsEmpty()) ShiftRight(Size().Decrement()); }
    @Override
    default Natural Size() { return ReallocableContainer.super.Size(); }
    @Override
    default T SubSequence(Natural from, Natural to) {
        T result = Empty();
        if (from == null || to == null || from.compareTo(to) > 0 || !IsInBound(from) || !IsInBound(to)) return result;
        long count = to.ToLong() - from.ToLong() + 1;
        result.Realloc(Natural.Of(count));
        for (long i = 0; i < count; i++) result.SetAt(GetAt(Natural.Of(from.ToLong() + i)), Natural.Of(i));
        return result;
    }
}
