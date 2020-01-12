package com.mobiquityinc.packer.util;

public interface SnapshotStack<T, V extends SnapshotStack<T,V>> {

    void push(T t);

    T pop();

    V snapshot();
}
