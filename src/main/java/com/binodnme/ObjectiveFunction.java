package com.binodnme;

import java.util.List;

public class ObjectiveFunction {
    private List<Integer> coefficients;

    public int compute(List<Integer> solutionSet) {
        int sum = 0;
        int i = 0;

        for (; i < coefficients.size() - 1; i++) {
            sum += coefficients.get(i) * solutionSet.get(i);
        }

        return sum + coefficients.get(i);
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }
}
