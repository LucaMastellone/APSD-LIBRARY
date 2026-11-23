package zapsdtest.simpletest.apsd.classes.containers.deqs;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.deqs.Queue;
import apsd.interfaces.containers.deqs.Stack;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Verifica dei default mediante implementazioni minime locali al test. */
public class DeqsInterfaceTest {
  private static final class TestStack implements Stack<TestStack, Integer> {
    private final LinkedList<Integer> data = new LinkedList<>();
    public TestStack Empty() { return new TestStack(); }
    public Natural Size() { return Natural.Of(data.size()); }
    public Integer Top() { return data.peekFirst(); }
    public void Pop() { if (!data.isEmpty()) data.removeFirst(); }
    public void Push(Integer value) { data.addFirst(value); }
  }
  private static final class TestQueue implements Queue<TestQueue, Integer> {
    private final LinkedList<Integer> data = new LinkedList<>();
    public TestQueue Empty() { return new TestQueue(); }
    public Natural Size() { return Natural.Of(data.size()); }
    public Integer Head() { return data.peekFirst(); }
    public void Dequeue() { if (!data.isEmpty()) data.removeFirst(); }
    public void Enqueue(Integer value) { data.addLast(value); }
  }
  @Test void stackDefaults() {
    var s = new TestStack();
    assertNull(s.TopNPop());
    assertTrue(s.Insert(1)); s.Push(2);
    assertEquals(2, s.TopNSwap(3));
    assertEquals(3, s.TopNPop());
    assertEquals(1, s.Top());
    s.Clear(); assertTrue(s.IsEmpty());
  }
  @Test void queueDefaults() {
    var q = new TestQueue();
    assertNull(q.HeadNDequeue());
    assertTrue(q.Insert(1)); q.Enqueue(2);
    assertEquals(1, q.HeadNDequeue());
    assertEquals(2, q.Head());
    q.Clear(); assertTrue(q.IsEmpty());
  }

  @Test void stackSwapAndNullElementPreserveSize() {
    var s = new TestStack();
    s.Push(1); s.Push(null);
    assertEquals(2, s.Size().ToLong());
    assertNull(s.TopNSwap(5));
    assertEquals(2, s.Size().ToLong());
    assertEquals(5, s.TopNPop());
    assertEquals(1, s.TopNPop());
    assertTrue(s.IsEmpty());
    s.Clear(); assertTrue(s.IsEmpty());
  }

  @Test void queueInsertAllRetainsFifoOrder() {
    var source = new apsd.classes.containers.sequences.Vector<Integer>(Natural.Of(3));
    source.SetAt(1, Natural.ZERO); source.SetAt(null, Natural.ONE); source.SetAt(3, Natural.Of(2));
    var q = new TestQueue();
    assertTrue(q.AllInserted(source));
    assertEquals(3, q.Size().ToLong());
    assertEquals(1, q.HeadNDequeue());
    assertNull(q.HeadNDequeue());
    assertEquals(3, q.HeadNDequeue());
    assertTrue(q.IsEmpty());
    q.Clear(); assertTrue(q.IsEmpty());
  }

  @Test void clearRemovesNullAndNonNullElements() {
    var stack = new TestStack();
    stack.Push(1); stack.Push(null); stack.Push(2);
    stack.Clear();
    assertEquals(0, stack.Size().ToLong());
    assertNull(stack.Top());
    var queue = new TestQueue();
    queue.Enqueue(1); queue.Enqueue(null); queue.Enqueue(2);
    queue.Clear();
    assertEquals(0, queue.Size().ToLong());
    assertNull(queue.Head());
  }

  @Test void nullElementIsDistinctFromEmptyBySize() {
    var stack = new TestStack();
    stack.Push(null);
    assertNull(stack.Top());
    assertEquals(1, stack.Size().ToLong());
    assertNull(stack.TopNPop());
    assertTrue(stack.IsEmpty());
    var queue = new TestQueue();
    queue.Enqueue(null);
    assertNull(queue.Head());
    assertEquals(1, queue.Size().ToLong());
    assertNull(queue.HeadNDequeue());
    assertTrue(queue.IsEmpty());
  }

  @Test void emptySwapCurrentDefaultWithNoOpPop() {
    var stack = new TestStack();
    stack.SwapTop(7);
    assertEquals(1, stack.Size().ToLong());
    assertEquals(7, stack.Top());
  }
}
