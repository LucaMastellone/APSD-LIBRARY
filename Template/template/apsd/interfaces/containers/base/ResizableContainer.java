package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: ReallocableContainer che è espandibile e riducibile. */
public interface ResizableContainer<T extends ResizableContainer<T>> extends ReallocableContainer<T> {
  double THRESHOLD_FACTOR = 2.0;

  void Expand(Natural num);
  void Reduce(Natural num);
  default void Expand() { Expand(Natural.ONE); }
  default void Reduce() { Reduce(Natural.ONE); }

  @Override
  Natural Size();

  @Override
  default void Grow(Natural num) {
    long size = Math.addExact(Size().ToLong(), num.ToLong());
    if (size >= Capacity().ToLong()) ReallocableContainer.super.Grow(num);
  }

  @Override
  default void Shrink() {
    if (Size().ToLong() <= Capacity().ToLong() / 4L)
      ReallocableContainer.super.Shrink();
  }
}
