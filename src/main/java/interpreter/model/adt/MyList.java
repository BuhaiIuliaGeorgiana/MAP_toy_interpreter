package interpreter.model.adt;

import interpreter.model.exp.ADTException;

import java.util.ArrayList;

public class MyList<T> implements IList<T>{
    private final ArrayList<T> arr = new ArrayList<>();
    @Override
    public void add(T element) {
        arr.add(element);
    }

    @Override
    public T get(int index) throws ADTException {
        if(arr.size()<index || index < 0) throw new ADTException("list: get an invalid index");
        return arr.get(index);
    }

    @Override
    public int size() {
        return arr.size();
    }
}
