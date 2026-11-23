package apsd.classes.containers.collections.concretecollections.bases;

// import apsd.classes.containers.sequences.Vector;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.traits.Predicate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/** Object: Abstract chain base implementation on linked-list. */
abstract public class LLChainBase<T extends LLChainBase<T, Data>, Data> implements Chain<T, Data> {
  // Must implement Chain.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Fields and constructors                                                  */
  /* ************************************************************************ */

  // protected final MutableNatural size = new MutableNatural();
  // protected final Box<LLNode<Data>> headref = new Box<>();
  // protected final Box<LLNode<Data>> tailref = new Box<>();
  // LLChainBase()
  // LLChainBase(TraversableContainer<?, Data> con)

  /* ************************************************************************ */
  /* Specific member functions of LLChainBase                                 */
  /* ************************************************************************ */

  // ListFRefIterator
  // FRefIterator
  // ListBRefIterator
  // BRefIterator

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  // Size

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // Clear

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  // Remove

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  // ListFIterator
  // FIterator
  // ListBIterator
  // BIterator

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // GetFirst
  // GetLast
  // SubSequence

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  // AtNRemove
  // FirstNRemove
  // LastNRemove

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  // Filter

  protected final MutableNatural size = new MutableNatural();
  protected final Box<LLNode<Data>> headref = new Box<>();
  protected final Box<LLNode<Data>> tailref = new Box<>();

  protected LLChainBase() {}
  protected LLChainBase(TraversableContainer<?, Data> con) {
    con.TraverseForward(dat -> { appendNode(dat); return false; });
  }
  protected void appendNode(Data dat) {
    LLNode<Data> node = new LLNode<>(dat);
    if (size.IsZero()) headref.Set(node); else tailref.Get().SetNext(node);
    tailref.Set(node);
    size.Increment();
  }
  protected Box<LLNode<Data>> refAt(long index) {
    Box<LLNode<Data>> ref = headref;
    for (long i = 0; i < index; i++) ref = ref.Get().GetNext();
    return ref;
  }
  protected class ListFRefIterator implements ForwardIterator<Box<LLNode<Data>>> {
    private Box<LLNode<Data>> ref = headref;
    public boolean IsValid() { return ref.Get() != null; }
    public void Reset() { ref = headref; }
    public Box<LLNode<Data>> GetCurrent() { if (!IsValid()) throw new NoSuchElementException(); return ref; }
    public Box<LLNode<Data>> DataNNext() { Box<LLNode<Data>> current = GetCurrent(); ref = current.Get().GetNext(); return current; }
  }
  protected class ListBRefIterator implements BackwardIterator<Box<LLNode<Data>>> {
    private final ArrayList<Box<LLNode<Data>>> refs = new ArrayList<>();
    private int index;
    protected ListBRefIterator() { Reset(); }
    public boolean IsValid() { return index >= 0; }
    public void Reset() {
      refs.clear();
      for (Box<LLNode<Data>> ref = headref; ref.Get() != null; ref = ref.Get().GetNext()) refs.add(ref);
      index = refs.size() - 1;
    }
    public Box<LLNode<Data>> GetCurrent() { if (!IsValid()) throw new NoSuchElementException(); return refs.get(index); }
    public Box<LLNode<Data>> DataNPrev() { Box<LLNode<Data>> current = GetCurrent(); index--; return current; }
  }
  protected ForwardIterator<Box<LLNode<Data>>> FRefIterator() { return new ListFRefIterator(); }
  protected BackwardIterator<Box<LLNode<Data>>> BRefIterator() { return new ListBRefIterator(); }
  protected class ListFIterator implements MutableForwardIterator<Data> {
    private final ForwardIterator<Box<LLNode<Data>>> it = FRefIterator();
    public boolean IsValid() { return it.IsValid(); }
    public void Reset() { it.Reset(); }
    public Data GetCurrent() { return it.GetCurrent().Get().Get(); }
    public void SetCurrent(Data dat) { it.GetCurrent().Get().Set(dat); }
    public Data DataNNext() { return it.DataNNext().Get().Get(); }
  }
  protected class ListBIterator implements MutableBackwardIterator<Data> {
    private final BackwardIterator<Box<LLNode<Data>>> it = BRefIterator();
    public boolean IsValid() { return it.IsValid(); }
    public void Reset() { it.Reset(); }
    public Data GetCurrent() { return it.GetCurrent().Get().Get(); }
    public void SetCurrent(Data dat) { it.GetCurrent().Get().Set(dat); }
    public Data DataNPrev() { return it.DataNPrev().Get().Get(); }
  }
  @Override public Natural Size() { return size.ToNatural(); }
  @Override public void Clear() { headref.Set(null); tailref.Set(null); size.Zero(); }
  @Override public boolean Remove(Data dat) { return Chain.super.Remove(dat); }
  @Override public ForwardIterator<Data> FIterator() { return new ListFIterator(); }
  @Override public BackwardIterator<Data> BIterator() { return new ListBIterator(); }
  @Override public Data GetFirst() { return GetAt(Natural.ZERO); }
  @Override public Data GetLast() { if (IsEmpty()) throw new IndexOutOfBoundsException(); return tailref.Get().Get(); }
  @Override public Data AtNRemove(Natural pos) {
    long index = ExcIfOutOfBound(pos);
    Box<LLNode<Data>> ref = refAt(index);
    LLNode<Data> removed = ref.Get();
    ref.Set(removed.GetNext().Get());
    size.Decrement();
    if (size.IsZero()) tailref.Set(null);
    else if (index == size.ToLong()) tailref.Set(refAt(index - 1).Get());
    return removed.Get();
  }
  @Override public Data FirstNRemove() { return AtNRemove(Natural.ZERO); }
  @Override public Data LastNRemove() { if (IsEmpty()) throw new IndexOutOfBoundsException(); return AtNRemove(Natural.Of(size.ToLong() - 1)); }
  @Override public void RemoveFirst() { FirstNRemove(); }
  @Override public void RemoveLast() { LastNRemove(); }
  @Override public boolean Filter(Predicate<Data> pred) { return Chain.super.Filter(pred); }
  @Override public T SubSequence(Natural from, Natural to) {
    T result = Empty();
    if (from == null || to == null || from.compareTo(to) > 0 || !IsInBound(from) || !IsInBound(to)) return result;
    ForwardIterator<Data> it = FIterator(); it.Next(from);
    for (long i = from.ToLong(); i <= to.ToLong(); i++) result.Insert(it.DataNNext());
    return result;
  }

}
