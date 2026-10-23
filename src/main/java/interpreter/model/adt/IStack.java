package interpreter.model.adt;

import interpreter.model.exp.ADTException;

public interface IStack<T> {
    void push(T v);

    T pop() throws ADTException;
    boolean isEmpty();
}
