package com.binodnme;

/**
 * Generic gene class
 */
public class Gene {
    private int value;

    public Gene(){}

    public Gene(int val){
        this.value = val;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "value=" + value +
                '}';
    }
}
