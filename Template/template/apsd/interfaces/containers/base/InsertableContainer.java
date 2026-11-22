package apsd.interfaces.containers.base;

import java.util.ArrayList;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<T extends InsertableContainer<T, Data>, Data> extends Container<T> {
    boolean Insert(Data dat);

    default boolean AllInserted(TraversableContainer<?, Data> con) {
        if (con == null) return false;
        ArrayList<Data> snapshot = new ArrayList<>();
        con.TraverseForward(dat -> { snapshot.add(dat); return false; });
        boolean result = true;
        for (Data dat : snapshot) result = Insert(dat) & result;
        return result;
    }

    default boolean SomeInserted(TraversableContainer<?, Data> con) {
        if (con == null) return false;
        ArrayList<Data> snapshot = new ArrayList<>();
        con.TraverseForward(dat -> { snapshot.add(dat); return false; });
        boolean result = false;
        for (Data dat : snapshot) result = Insert(dat) | result;
        return result;
    }
}
