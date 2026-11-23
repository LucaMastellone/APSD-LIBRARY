package zapsdtest.simpletest.apsd.classes.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Ulteriori verifiche sulle sequenze, senza alterare i test forniti. */
public class SequenceExtendedTest {
  private static Natural n(long value) { return Natural.Of(value); }

  private <T extends DynVector<T, Integer>> void checkDynamicSequence(T v) {
    assertNull(v.Search(9));
    v.InsertLast(10);
    v.InsertLast(null);
    v.InsertLast(10);
    v.InsertAt(20, n(1));
    assertEquals(4, v.Size().ToLong());
    assertEquals(20, v.GetAt(n(1)));
    assertEquals(n(0), v.Search(10));
    assertEquals(n(2), v.Search(null));
    assertNull(v.Search(99));
    T sub = v.SubSequence(n(1), n(2));
    assertEquals(2, sub.Size().ToLong());
    assertEquals(20, sub.GetFirst());
    assertNull(sub.GetLast());
    assertEquals(20, v.AtNRemove(n(1)));
    assertNull(v.GetAt(n(1)));
    assertTrue(v.Remove(null));
    assertFalse(v.Remove(99));
    MutableForwardIterator<Integer> it = v.FIterator();
    assertEquals(10, it.DataNNext());
    assertEquals(10, it.DataNNext());
    assertFalse(it.IsValid());
    it.Reset();
    it.SetCurrent(11);
    assertEquals(11, v.GetFirst());
    assertThrows(IndexOutOfBoundsException.class, () -> v.InsertAt(1, n(9)));
    assertThrows(IndexOutOfBoundsException.class, () -> v.AtNRemove(n(9)));
  }

  @Test void dynamicLinear() { checkDynamicSequence(new apsd.classes.containers.sequences.DynVector<>()); }
  @Test void dynamicCircular() { checkDynamicSequence(new apsd.classes.containers.sequences.DynCircularVector<>()); }

  private <T extends DynVector<T, Integer>> void checkIntegration(T v) {
    v.Expand(n(0));
    assertEquals(0, v.Size().ToLong());
    assertTrue(v.Capacity().ToLong() > 0);
    v.InsertLast(1); v.InsertLast(2);
    var back = v.BIterator();
    assertEquals(2, back.DataNPrev());
    assertEquals(1, back.DataNPrev());
    assertFalse(back.IsValid());
    back.Reset();
    assertEquals(2, back.GetCurrent());
    assertTrue(v.AllInserted(v));
    assertEquals(4, v.Size().ToLong());
    assertTrue(v.AllRemoved(v));
    assertTrue(v.IsEmpty());
  }

  @Test void integrationLinear() { checkIntegration(new apsd.classes.containers.sequences.DynVector<>()); }
  @Test void integrationCircular() { checkIntegration(new apsd.classes.containers.sequences.DynCircularVector<>()); }

  @Test void subsequenceIsIndependent() {
    var v = new apsd.classes.containers.sequences.Vector<Integer>(n(3));
    v.SetAt(1, n(0)); v.SetAt(2, n(1)); v.SetAt(3, n(2));
    var sub = v.SubSequence(n(0), n(1));
    sub.SetAt(9, n(0));
    assertEquals(1, v.GetFirst());
    assertEquals(9, sub.GetFirst());
  }

  @Test void circularShiftAndReallocationPreserveLogicalOrder() {
    var v = new apsd.classes.containers.sequences.CircularVector<Integer>(n(3));
    v.SetAt(1, n(0)); v.SetAt(2, n(1)); v.SetAt(3, n(2));
    v.ShiftLeft(n(0));
    assertEquals(2, v.GetAt(n(0))); assertEquals(3, v.GetAt(n(1))); assertNull(v.GetAt(n(2)));
    v.Realloc(n(5));
    assertEquals(2, v.GetAt(n(0))); assertEquals(3, v.GetAt(n(1)));
  }
}
