package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Reallocable;

/** Interface: ClearableContainer che è anche Reallocable. */
public interface ReallocableContainer<T extends ReallocableContainer<T>> extends ClearableContainer<T>, Reallocable {
  double GROW_FACTOR = 2.0;
  double SHRINK_FACTOR = 2.0;

  Natural Capacity();

  default void Grow() { Grow(Natural.ONE); }

  default void Grow(Natural dim) {
    long size = Math.addExact(Size().ToLong(), dim.ToLong());
    if (size > Long.MAX_VALUE / 2) throw new ArithmeticException("Capacity overflow");
    Realloc(Natural.Of(size * 2));
  }

  default void Shrink() { Realloc(Natural.Of(Capacity().ToLong() / 2L)); }

  @Override
  default Natural Size() { return Capacity(); }

  @Override
  default void Clear() { Realloc(Natural.ZERO); }
}
