package apsd.interfaces.containers.base;

import java.util.ArrayList;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<T extends RemovableContainer<T, Data>, Data> extends Container<T> {
    boolean Remove(Data dat);

    default boolean AllRemoved(TraversableContainer<?, Data> con) {
        if (con == null) return false;
        ArrayList<Data> snapshot = new ArrayList<>();
        con.TraverseForward(dat -> { snapshot.add(dat); return false; });
        boolean result = true;
        for (Data dat : snapshot) result = Remove(dat) & result;
        return result;
    }

    default boolean SomeRemoved(TraversableContainer<?, Data> con) {
        if (con == null) return false;
        ArrayList<Data> snapshot = new ArrayList<>();
        con.TraverseForward(dat -> { snapshot.add(dat); return false; });
        boolean result = false;
        for (Data dat : snapshot) result = Remove(dat) | result;
        return result;
    }
}
