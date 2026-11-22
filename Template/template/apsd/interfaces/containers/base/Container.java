package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: Container, la base di tutti i contenitori. */
public interface Container<T extends Container<T>> {
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Specific member functions of Container                                   */
  /* ************************************************************************ */

  T Empty();
  Natural Size();
  default boolean IsEmpty() { return Size().IsZero(); }

}
