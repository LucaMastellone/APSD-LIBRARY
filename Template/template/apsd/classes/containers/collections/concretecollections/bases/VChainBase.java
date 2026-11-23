package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
// import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<T extends VChainBase<T, Data>, Data> implements Chain<T, Data> {
  // Must implement Chain.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Fields and constructors                                                  */
  /* ************************************************************************ */

  // protected DynVector<?, Data> vec = new DynCircularVector<>();
  // VChainBase()
  // VChainBase(TraversableContainer<?, Data> con)

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // Size

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // Clear

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // FIterator
  // BIterator

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // GetAt
  // SubSequence

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  // AtNRemove

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // Filter

  protected DynVector<?, Data> vec = new DynCircularVector<>();

  protected VChainBase() {}

  protected VChainBase(TraversableContainer<?, Data> con) {
    con.TraverseForward(dat -> { vec.InsertLast(dat); return false; });
  }

  @Override public Natural Size() { return vec.Size(); }
  @Override public void Clear() { vec.Clear(); }
  @Override public ForwardIterator<Data> FIterator() { return vec.FIterator(); }
  @Override public BackwardIterator<Data> BIterator() { return vec.BIterator(); }
  @Override public Data GetAt(Natural pos) { return vec.GetAt(pos); }
  @Override public Data AtNRemove(Natural pos) { return vec.AtNRemove(pos); }
  @Override public boolean Filter(Predicate<Data> pred) { return Chain.super.Filter(pred); }
  @Override public T SubSequence(Natural from, Natural to) {
    T result = Empty();
    if (from == null || to == null || from.compareTo(to) > 0 || !IsInBound(from) || !IsInBound(to)) return result;
    ForwardIterator<Data> it = FIterator();
    it.Next(from);
    long count = to.ToLong() - from.ToLong() + 1;
    for (long i = 0; i < count; i++) result.Insert(it.DataNNext());
    return result;
  }

}
