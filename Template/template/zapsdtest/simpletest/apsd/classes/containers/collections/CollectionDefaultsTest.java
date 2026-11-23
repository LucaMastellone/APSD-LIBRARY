package zapsdtest.simpletest.apsd.classes.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Collection;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.BackwardIterator;
import java.util.ArrayList;
import java.util.Objects;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Controlli dei default indipendenti dalle future implementazioni concrete. */
public class CollectionDefaultsTest {
  private static final class Bag implements Collection<Bag, Integer> {
    private final ArrayList<Integer> data = new ArrayList<>();
    public Bag Empty() { return new Bag(); }
    public Natural Size() { return Natural.Of(data.size()); }
    public boolean Insert(Integer value) { data.add(value); return true; }
    public boolean Remove(Integer value) { return data.remove(value); }
    public ForwardIterator<Integer> FIterator() { return new ForwardIterator<>() {
      int i = 0;
      public boolean IsValid() { return i < data.size(); }
      public void Reset() { i = 0; }
      public Integer GetCurrent() { if (!IsValid()) throw new NoSuchElementException(); return data.get(i); }
      public Integer DataNNext() { Integer v = GetCurrent(); i++; return v; }
    }; }
    public BackwardIterator<Integer> BIterator() { return new BackwardIterator<>() {
      int i = data.size() - 1;
      public boolean IsValid() { return i >= 0 && i < data.size(); }
      public void Reset() { i = data.size() - 1; }
      public Integer GetCurrent() { if (!IsValid()) throw new NoSuchElementException(); return data.get(i); }
      public Integer DataNPrev() { Integer v = GetCurrent(); i--; return v; }
    }; }
  }
  private Bag bag(Integer... values) { var b = new Bag(); for (Integer v : values) b.Insert(v); return b; }
  @Test void insertionAndOccurrences() {
    var b = bag(1, 1, null);
    assertFalse(b.InsertIfAbsent(1)); assertTrue(b.InsertIfAbsent(2));
    b.RemoveOccurrences(1); assertFalse(b.Exists(1));
    b.RemoveOccurrences(null); assertFalse(b.Exists(null)); assertTrue(b.Exists(2));
  }
  @Test void filterAndClear() {
    var b = bag(1, 2, 3, 4);
    assertTrue(b.Filter(v -> v % 2 == 0));
    assertEquals(2, b.Size().ToLong()); assertTrue(b.Exists(2)); assertTrue(b.Exists(4));
    assertFalse(b.Filter(v -> true)); b.Clear(); assertTrue(b.IsEmpty());
  }
  @Test void selfOperationsTerminateAndPreserveResults() {
    var b = bag(1, 2); b.Union(b); assertEquals(4, b.Size().ToLong());
    b.Intersection(b); assertEquals(4, b.Size().ToLong());
    b.Difference(b); assertTrue(b.IsEmpty());
  }
  @Test void operationsWithAnotherCollection() {
    var a = bag(1, 2, 3); var b = bag(2, 4);
    a.Intersection(b); assertEquals(1, a.Size().ToLong()); assertTrue(a.Exists(2));
    a.Union(b); assertTrue(a.Exists(4)); a.Difference(b);
    assertFalse(a.Exists(4)); assertTrue(Objects.equals(2, a.FIterator().GetCurrent()));
  }
  @SuppressWarnings("removal")
  @Test void filterKeepsMatchingInstanceEvenWhenValuesAreEqual() {
    Integer keep = new Integer(1000);
    Integer discard = new Integer(1000);
    var b = bag(keep, discard);
    assertTrue(b.Filter(value -> value == keep));
    assertEquals(1, b.Size().ToLong());
    assertSame(keep, b.FIterator().GetCurrent());
  }

  private static final class TestList extends apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase<TestList, Integer>
      implements apsd.interfaces.containers.collections.List<TestList, Integer> {
    public TestList Empty() { return new TestList(); }
  }
  @Test void listReusesInsertionSearchAndRemoval() {
    var list = new TestList();
    assertTrue(list.Insert(2));
    list.InsertFirst(1); list.InsertLast(2);
    assertEquals(Natural.Of(1), list.Search(2));
    assertTrue(list.Remove(2));
    assertEquals(Natural.Of(2), list.Size());
    assertEquals(1, list.GetFirst()); assertEquals(2, list.GetLast());
  }

  private static final class TestSet extends apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase<TestSet, Integer>
      implements apsd.interfaces.containers.collections.Set<TestSet, Integer> {
    public TestSet Empty() { return new TestSet(); }
    @Override public boolean Insert(Integer value) {
      if (Exists(value)) return false;
      InsertLast(value);
      return true;
    }
  }
  @Test void setEqualityAndIntersection() {
    var a = new TestSet(); var b = new TestSet();
    a.Insert(1); a.Insert(2); b.Insert(2); b.Insert(1);
    assertTrue(a.IsEqual((apsd.interfaces.containers.collections.Set<?, Integer>) b));
    assertTrue(a.IsEqual((apsd.interfaces.containers.base.TraversableContainer<?, Integer>) b));
    b.Remove(1); a.Intersection(b);
    assertEquals(1, a.Size().ToLong()); assertTrue(a.Exists(2));
    assertTrue(a.IsEqual((apsd.interfaces.containers.collections.Set<?, Integer>) b));
  }
}
