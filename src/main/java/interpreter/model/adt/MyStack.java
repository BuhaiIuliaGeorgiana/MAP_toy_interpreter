package interpreter.model.adt;

import interpreter.model.exp.ADTException;

import java.util.ArrayDeque;

public class MyStack<T> implements IStack<T>{
    private final ArrayDeque<T> stack = new ArrayDeque<>();


    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException {
        if(stack.isEmpty()) throw new ADTException("pop: the stack is empty");
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
