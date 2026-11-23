package zapsdtest.simpletest.apsd.classes.containers.collections.concretecollections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Casi aggiuntivi per le liste sviluppate al punto 8. */
public class ListExtendedTest {
  private static Natural n(long value) { return Natural.Of(value); }
  private <T extends List<T, Integer>> void checkList(T list) {
    list.InsertLast(1); list.InsertLast(null); list.InsertFirst(0);
    assertEquals(3, list.Size().ToLong());
    assertEquals(0, list.GetFirst()); assertNull(list.GetLast());
    assertNull(list.AtNRemove(n(2)));
    list.InsertAt(2, n(1));
    assertEquals(2, list.GetAt(n(1)));
    assertEquals(1, list.LastNRemove());
    assertEquals(0, list.FirstNRemove());
    assertEquals(2, list.GetFirst());
    list.RemoveLast(); assertTrue(list.IsEmpty());
    list.InsertLast(7); list.InsertLast(8); list.InsertLast(9);
    var sub = list.SubSequence(n(1), n(2));
    assertEquals(2, sub.Size().ToLong()); assertEquals(8, sub.GetFirst()); assertEquals(9, sub.GetLast());
    var backwards = list.BIterator();
    assertEquals(9, backwards.DataNPrev()); assertEquals(8, backwards.DataNPrev()); assertEquals(7, backwards.DataNPrev());
    assertFalse(backwards.IsValid());
    assertThrows(IndexOutOfBoundsException.class, () -> list.InsertAt(5, n(5)));
    list.Clear(); assertTrue(list.IsEmpty());
  }
  @Test void vectorList() { checkList(new apsd.classes.containers.collections.concretecollections.VList<>()); }
  @Test void linkedList() { checkList(new apsd.classes.containers.collections.concretecollections.LLList<>()); }
  @Test void linkedBackwardIteratorResetAndMutation() {
    var list = new apsd.classes.containers.collections.concretecollections.LLList<Integer>();
    var empty = list.BIterator();
    assertFalse(empty.IsValid());
    list.InsertLast(1); list.InsertLast(2); list.InsertLast(3);
    empty.Reset();
    assertEquals(3, empty.GetCurrent());
    empty.SetCurrent(30);
    assertEquals(30, empty.DataNPrev());
    assertEquals(2, empty.DataNPrev());
    assertEquals(1, empty.DataNPrev());
    assertFalse(empty.IsValid());
    assertEquals(30, list.GetLast());
    list.RemoveFirst();
    empty.Reset();
    assertEquals(30, empty.DataNPrev());
    assertEquals(2, empty.DataNPrev());
    assertFalse(empty.IsValid());
  }
  private <T extends List<T, Integer>> void differentialOperations(T list) {
    var expected = new java.util.ArrayList<Integer>();
    var random = new java.util.Random(83421);
    for (int step = 0; step < 1000; step++) {
      int op = random.nextInt(4);
      Integer value = random.nextInt(5) == 0 ? null : random.nextInt(12);
      if (expected.isEmpty() || op == 0) {
        int position = random.nextInt(expected.size() + 1);
        expected.add(position, value); list.InsertAt(value, n(position));
      } else if (op == 1) {
        int position = random.nextInt(expected.size());
        assertEquals(expected.remove(position), list.AtNRemove(n(position)));
      } else if (op == 2) {
        int position = random.nextInt(expected.size());
        expected.set(position, value); list.SetAt(value, n(position));
      } else {
        int position = expected.indexOf(value);
        var found = list.Search(value);
        if (position < 0) assertNull(found); else assertEquals(position, found.ToLong());
      }
      assertEquals(expected.size(), list.Size().ToLong(), "passo " + step);
      var forward = list.FIterator();
      for (Integer item : expected) { assertTrue(forward.IsValid()); assertEquals(item, forward.DataNNext()); }
      assertFalse(forward.IsValid());
      var backward = list.BIterator();
      for (int i = expected.size() - 1; i >= 0; i--) { assertTrue(backward.IsValid()); assertEquals(expected.get(i), backward.DataNPrev()); }
      assertFalse(backward.IsValid());
    }
  }
  @Test void vectorDifferentialOperations() { differentialOperations(new apsd.classes.containers.collections.concretecollections.VList<>()); }
  @Test void linkedDifferentialOperations() { differentialOperations(new apsd.classes.containers.collections.concretecollections.LLList<>()); }
  private <T extends List<T, Integer>> void filteringAndCopies(T list) {
    list.InsertLast(1); list.InsertLast(null); list.InsertLast(2); list.InsertLast(1);
    assertEquals(0, list.Search(1).ToLong());
    assertEquals(1, list.Search(null).ToLong());
    assertTrue(list.Filter(x -> x != null && x != 1));
    assertEquals(1, list.Size().ToLong()); assertEquals(2, list.GetFirst());
    assertFalse(list.Filter(x -> true));
    assertTrue(list.Filter(x -> false)); assertTrue(list.IsEmpty());
    assertThrows(IndexOutOfBoundsException.class, list::GetFirst);
    assertThrows(IndexOutOfBoundsException.class, list::LastNRemove);
  }
  @Test void vectorFiltering() { filteringAndCopies(new apsd.classes.containers.collections.concretecollections.VList<>()); }
  @Test void linkedFiltering() { filteringAndCopies(new apsd.classes.containers.collections.concretecollections.LLList<>()); }
}
