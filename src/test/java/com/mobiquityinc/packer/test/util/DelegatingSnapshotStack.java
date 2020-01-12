package com.mobiquityinc.packer.test.util;

import com.mobiquityinc.packer.util.SnapshotStack;

import java.util.Stack;

public class DelegatingSnapshotStack<T> implements SnapshotStack<T, DelegatingSnapshotStack<T>> {

    private final Stack<T> delegate;

    public DelegatingSnapshotStack(Stack<T> delegate) {
        this.delegate = delegate;
    }


    @Override
    public void push(T t) {
        delegate.push(t);
    }

    @Override
    public T pop() {
        return delegate.pop();
    }

    @Override
    public DelegatingSnapshotStack<T> snapshot() {
        Stack<T> newStack = new Stack<>();
        newStack.addAll(delegate);
        return new DelegatingSnapshotStack(newStack) {};
    }

    public Stack<T> delegate() {
        return delegate;
    }
}
