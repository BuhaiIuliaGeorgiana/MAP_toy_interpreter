package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayDeque;

public class MyStack<T> implements IStack<T>{
    private final ArrayDeque<T> stack = new ArrayDeque<>();


    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException {
        if(stack.isEmpty()) throw new ADTException("stack: pop on an empty stack");
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return stack.toString();
    }
}
