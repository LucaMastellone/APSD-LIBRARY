package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<T extends IterableContainer<T, Data>, Data> extends TraversableContainer<T, Data> {
    ForwardIterator<Data> FIterator();
    BackwardIterator<Data> BIterator();

    @Override
    default boolean TraverseForward(Predicate<Data> fun) {
        return fun != null && FIterator().ForEachForward(fun);
    }

    @Override
    default boolean TraverseBackward(Predicate<Data> fun) {
        return fun != null && BIterator().ForEachBackward(fun);
    }
}
