package com.binodnme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    public static final int TOTAL_NUMBER_OF_CHROMOSOMES = 100;
    public static final int TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME = 4;

    private static final int MAX_VALUE = 31;
    private static final int MIN_VALUE = 1;
    private static final float CROSSOVER_PERCENTAGE = 0.40f;
    private static final float MUTATION_PERCENTAGE = 0.1f;

    public static void main(String[] args) {

        //equation to solve (a + 2b + 3c + 4d = 30)
        //find the value of a, b, c and d

        //Initialization
        List<Chromosome> chromosomes = ChromosomeUtils.generateRandomChromosomes(TOTAL_NUMBER_OF_CHROMOSOMES, TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME, MIN_VALUE, MAX_VALUE);

        //Evaluation
        boolean solutionFound = false;
        int generation = 1;

        while (!(solutionFound = evaluateFitnessValueOfChromosomes(chromosomes))) {

            generation++;

            //total fitness
            float totalFitness = calculateTotalFitness(chromosomes);

            //selection
            List<Chromosome> chromosomeList = ChromosomeUtils.selectChromosomes(chromosomes, totalFitness);

            //cross over
            ChromosomeUtils.crossOverChromosomes(chromosomeList, CROSSOVER_PERCENTAGE);

            //mutation
            ChromosomeUtils.mutateChromosomes(chromosomeList, MUTATION_PERCENTAGE, MIN_VALUE, MAX_VALUE);

            //Evaluation
//            solutionFound = evaluateFitnessValueOfChromosomes(chromosomes);
        }
        System.out.println("total generation : "+generation);

    }


    private static float calculateTotalFitness(List<Chromosome> chromosomes) {
        float result = 0.0f;
        for (Chromosome chromosome : chromosomes) {
            result += chromosome.getFitness();
        }
        return result;
    }


    private static boolean evaluateFitnessValueOfChromosomes(List<Chromosome> chromosomes) {
        boolean solutionFound = false;

        for (Chromosome chromosome : chromosomes) {
            int objectiveFunctionValue = evaluateObjectiveFunctionValue(chromosome);

            float fitness = evaluateFitnessValue(objectiveFunctionValue);
            chromosome.setFitness(fitness);

            if (objectiveFunctionValue == 0) {
                solutionFound = true;
                System.out.println(chromosome);
                break;
            }

        }

        return solutionFound;
    }

    private static float evaluateFitnessValue(int objectiveFunctionValue) {
        return (float) (1.0 / (1 + objectiveFunctionValue));
    }


    private static int evaluateObjectiveFunctionValue(Chromosome chromosome) {
        List<Integer> coefficients = new ArrayList<>();
        coefficients.add(1);
        coefficients.add(2);
        coefficients.add(3);
        coefficients.add(4);
        coefficients.add(-30);
        List<Integer> values = chromosome.getGenes().stream().map(gene -> gene.getValue()).collect(Collectors.toList());

        ObjectiveFunction objectiveFunction = new ObjectiveFunction();
        objectiveFunction.setCoefficients(coefficients);
        return Math.abs(objectiveFunction.compute(values));
    }
}
