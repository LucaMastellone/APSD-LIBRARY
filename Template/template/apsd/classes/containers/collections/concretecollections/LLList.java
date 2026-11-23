package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
// import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<LLList<Data>, Data> implements List<LLList<Data>, Data> {
  // Must extend LLChainBase.
  // Must implement List.

  /* ************************************************************************ */
  /* Fields and constructors                                                  */
  /* ************************************************************************ */

  // LLList()
  // LLList(TraversableContainer<?, Data> con)

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // Empty

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  // FIterator
  // BIterator

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  // SetAt
  // SetFirst
  // SetLast

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  // InsertAt
  // InsertFirst
  // InsertLast

  public LLList() { super(); }
  public LLList(TraversableContainer<?, Data> con) { super(con); }
  @Override public LLList<Data> Empty() { return new LLList<>(); }
  @Override public MutableForwardIterator<Data> FIterator() { return (MutableForwardIterator<Data>) super.FIterator(); }
  @Override public MutableBackwardIterator<Data> BIterator() { return (MutableBackwardIterator<Data>) super.BIterator(); }
  @Override public void SetAt(Data dat, Natural pos) { refAt(ExcIfOutOfBound(pos)).Get().Set(dat); }
  @Override public void SetFirst(Data dat) { SetAt(dat, Natural.ZERO); }
  @Override public void SetLast(Data dat) { if (IsEmpty()) throw new IndexOutOfBoundsException(); tailref.Get().Set(dat); }
  @Override public void InsertAt(Data dat, Natural pos) {
    if (pos == null || pos.ToLong() > size.ToLong()) throw new IndexOutOfBoundsException("Invalid position: " + pos);
    if (pos.ToLong() == size.ToLong()) { appendNode(dat); return; }
    Box<LLNode<Data>> ref = refAt(pos.ToLong());
    ref.Set(new LLNode<>(dat, ref.Get()));
    size.Increment();
  }
  @Override public void InsertFirst(Data dat) { InsertAt(dat, Natural.ZERO); }
  @Override public void InsertLast(Data dat) { appendNode(dat); }

}
