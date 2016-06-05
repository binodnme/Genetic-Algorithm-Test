package com.binodnme;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {
    private List<Integer> genes = new ArrayList<>();
    private float fitness = 0;
    private float probabilityOfSelection = 0;

    public List<Integer> getGenes() {
        return genes;
    }

    public void setGenes(List<Integer> genes) {
        this.genes = genes;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float getProbabilityOfSelection() {
        return probabilityOfSelection;
    }

    public void setProbabilityOfSelection(float probabilityOfSelection) {
        this.probabilityOfSelection = probabilityOfSelection;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "genes=" + genes +
                ", f=" + fitness +
                ", p=" + probabilityOfSelection +
                '}';
    }
}
