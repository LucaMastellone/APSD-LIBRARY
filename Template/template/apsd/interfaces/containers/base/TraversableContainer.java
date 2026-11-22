package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<T extends TraversableContainer<T, Data>, Data> extends MembershipContainer<T, Data> {

    boolean TraverseForward(Predicate<Data> fun);
    boolean TraverseBackward(Predicate<Data> fun);

    default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) TraverseForward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
        return acc.Get();
    }

    default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc ini) {
        final Box<Acc> acc = new Box<>(ini);
        if (fun != null) TraverseBackward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
        return acc.Get();
    }

    default Natural Multiplicity(Data dat) {
        return FoldForward((cur, count) -> java.util.Objects.equals(cur, dat) ? count.Increment() : count, Natural.ZERO);
    }

    default boolean IsEqual(TraversableContainer<?, Data> con) {
        return con != null && Size().equals(con.Size())
                && !TraverseForward(dat -> !Multiplicity(dat).equals(con.Multiplicity(dat)));
    }

    @Override
    default Natural Size() {
        return FoldForward((dat, count) -> count.Increment(), Natural.ZERO);
    }

    @Override
    default boolean Exists(Data dat) {
        return TraverseForward(cur -> java.util.Objects.equals(cur, dat));
    }

    // Multiplicity
    // IsEqual
    // Override from Container: Size
    // Override from MembershipContainer: Exists
}
