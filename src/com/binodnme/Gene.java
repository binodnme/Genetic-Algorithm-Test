package com.binodnme;

/**
 * Generic gene class
 * @param <T>
 */
public class Gene<T> {
    private T value;

    public Gene(){}

    public Gene(T val){
        this.value = val;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "value=" + value +
                '}';
    }
}
