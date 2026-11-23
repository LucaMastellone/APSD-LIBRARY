package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Queue<T extends Queue<T, Data>, Data> extends ClearableContainer<T>, InsertableContainer<T, Data> {
  // Must extend ClearableContainer and InsertableContainer.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Specific member functions of Queue                                       */
  /* ************************************************************************ */

  // Head
  // Dequeue
  // HeadNDequeue
  // Enqueue

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // Clear

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // Insert

  Data Head();
  void Dequeue();
  void Enqueue(Data dat);
  default Data HeadNDequeue() { Data value = Head(); Dequeue(); return value; }
  @Override default void Clear() { while (!IsEmpty()) Dequeue(); }
  @Override default boolean Insert(Data dat) { Enqueue(dat); return true; }

}
