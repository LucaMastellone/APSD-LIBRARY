package apsd.interfaces.containers.deqs;

import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;

public interface Stack<T extends Stack<T, Data>, Data> extends ClearableContainer<T>, InsertableContainer<T, Data> {
  // Must extend ClearableContainer and InsertableContainer.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Specific member functions of Stack                                       */
  /* ************************************************************************ */

  // Top
  // Pop
  // TopNPop
  // SwapTop
  // TopNSwap
  // Push

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // Clear

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  // Insert

  Data Top();
  void Pop();
  void Push(Data dat);
  default Data TopNPop() { Data value = Top(); Pop(); return value; }
  default void SwapTop(Data dat) { Pop(); Push(dat); }
  default Data TopNSwap(Data dat) { Data value = Top(); SwapTop(dat); return value; }
  @Override default void Clear() { while (!IsEmpty()) Pop(); }
  @Override default boolean Insert(Data dat) { Push(dat); return true; }

}
