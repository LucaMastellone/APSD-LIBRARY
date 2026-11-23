package apsd.interfaces.containers.collections;

// import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ClearableContainer;
import apsd.interfaces.containers.base.InsertableContainer;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.base.RemovableContainer;
import apsd.interfaces.traits.Predicate;
import java.util.ArrayList;

public interface Collection<T extends Collection<T, Data>, Data> extends ClearableContainer<T>, InsertableContainer<T, Data>, RemovableContainer<T, Data>, IterableContainer<T, Data> {
  // Must extend ClearableContainer, InsertableContainer, RemovableContainer, and IterableContainer.
  // T must have a recursive bound.

  /* ************************************************************************ */
  /* Specific member functions of Collection                                  */
  /* ************************************************************************ */

  // InsertIfAbsent
  // RemoveOccurrences
  // Filter
  // Union
  // Difference
  // Intersection

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  // Clear

  default boolean InsertIfAbsent(Data dat) {
    return !Exists(dat) && Insert(dat);
  }

  default void RemoveOccurrences(Data dat) {
    while (Remove(dat)) { /* Rimuove ogni occorrenza presente. */ }
  }

  default boolean Filter(Predicate<Data> fun) {
    if (fun == null) return false;
    ArrayList<Data> retained = new ArrayList<>();
    boolean[] changed = {false};
    TraverseForward(dat -> {
      if (fun.Apply(dat)) retained.add(dat); else changed[0] = true;
      return false;
    });
    if (changed[0]) {
      Clear();
      for (Data dat : retained) Insert(dat);
    }
    return changed[0];
  }

  default void Union(Collection<?, Data> col) {
    if (col != null) AllInserted(col);
  }

  default void Difference(Collection<?, Data> col) {
    if (col != null) AllRemoved(col);
  }

  default void Intersection(Collection<?, Data> col) {
    if (col != null) Filter(col::Exists);
  }

  @Override default void Clear() {
    while (!IsEmpty()) Remove(FIterator().GetCurrent());
  }

}
