package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.TraversableContainer;

public interface Set<T extends Set<T, Data>, Data> extends Collection<T, Data> {
  // Must extend Collection.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Specific member functions of Set                                         */
  /* ************************************************************************ */

  // IsEqual

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */

  // IsEqual

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // Intersection

  default boolean IsEqual(Set<?, Data> other) { return IsEqual((TraversableContainer<?, Data>) other); }
  @Override default boolean IsEqual(TraversableContainer<?, Data> other) { return Collection.super.IsEqual(other); }
  @Override default void Intersection(Collection<?, Data> other) { Collection.super.Intersection(other); }

}
