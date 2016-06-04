package com.binodnme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final int TOTAL_NUMBER_OF_CHROMOSOMES = 50;
    public static final int TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME = 4;
    // (0 < (a, b, c ,d) < 30 )
    private static final int MAX_VALUE = 31;
    private static final int MIN_VALUE = 1;
    private static float totalFitness = 0;
    private static final float CROSSOVER_PERCENTAGE = 0.40f;
    private static final float MUTATION_PERCENTAGE = 0.1f;
    public static List<Chromosome> chromosomes = new ArrayList<>(TOTAL_NUMBER_OF_CHROMOSOMES);
    public static List<Float> comulativeProbabilities = new ArrayList<>(TOTAL_NUMBER_OF_CHROMOSOMES);

    private static final int TOTAL_GENERATION_TO_RUN = 50;
    private static boolean solutionFound = false;

    public static void main(String[] args) {

        //equation to solve (a + 2b + 3c + 4d = 30)
        //find the value of a, b, c and d

        //Initialization
        generateRandomChromosomes();
        System.out.println(chromosomes);

        //Evaluation
        evaluateFitnessValueOfChromosomes();
        int generation = 1;
//        for (int i = 0; i < TOTAL_GENERATION_TO_RUN; i++) {
        while (true){
            if(!solutionFound){
                generation++;

                //total fitness
                totalFitness = calculateTotalFitness();

                //evaluate probability
                evaluateProbabilityOfChromosomes();

                //compute comulative probability
                computeComulativeProbability();

                //select new chromosomes
                chromosomes = getNewChromosomes();

                //cross over
                crossOverChromosomes();

                //mutation
                mutateChromosomes();

                //Evaluation
                evaluateFitnessValueOfChromosomes();
            }else{
                System.out.println("total generation : "+generation);
                System.out.println("solution found");
                break;
            }

        }


    }

    private static void mutateChromosomes() {
        int totalRandomNumberToBeGenerated = (int) (MUTATION_PERCENTAGE * TOTAL_NUMBER_OF_CHROMOSOMES * TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME);
        for (int i = 0; i < totalRandomNumberToBeGenerated; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, TOTAL_NUMBER_OF_CHROMOSOMES * TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME); //including 0 and excluding 30;
            int chromosomeIndex = randomIndex / TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME;
            int geneIndex = randomIndex % TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME;
            int randomNumber = ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
            chromosomes.get(chromosomeIndex).getGenes().set(geneIndex, randomNumber);
        }
    }

    private static void crossOverChromosomes() {

        List<Integer> parentsIndex = getParentsIndex();

        for (int i = 0; i < parentsIndex.size(); i++) {
            int cutPoint = ThreadLocalRandom.current().nextInt(1, TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME);
            Chromosome parentA = chromosomes.get(parentsIndex.get(i));
            Chromosome parentB;
            if (i + 1 >= parentsIndex.size()) {
                parentB = chromosomes.get(parentsIndex.get(0));
            } else {
                parentB = chromosomes.get(parentsIndex.get(i + 1));
            }

            Chromosome newChromosome = new Chromosome();

            for (int j = 0; j < cutPoint; j++) {
                newChromosome.getGenes().add(parentA.getGenes().get(j));
            }

            for (int j = cutPoint; j < TOTAL_NUMBER_OF_GENES_IN_A_CHROMOSOME; j++) {
                newChromosome.getGenes().add(parentB.getGenes().get(j));
            }

            chromosomes.set(parentsIndex.get(i), newChromosome);
        }
    }

    private static List<Integer> getParentsIndex() {
        List<Integer> parentsIndex = new ArrayList<>();
        for (int i = 0; i < TOTAL_NUMBER_OF_CHROMOSOMES; i++) {
            float randomNumber = ThreadLocalRandom.current().nextFloat(); // generates number between 0 (inclusive) and 1 (exclusive)
            if (randomNumber < CROSSOVER_PERCENTAGE) {
                parentsIndex.add(i);
            }
        }
        return parentsIndex;
    }

    private static List<Chromosome> getNewChromosomes() {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < TOTAL_NUMBER_OF_CHROMOSOMES; i++) {
            float randomNumber = ThreadLocalRandom.current().nextFloat(); // generates number between 0 (inclusive) and 1 (exclusive)

            for (int j = 0; j < TOTAL_NUMBER_OF_CHROMOSOMES; j++) {
                float comulativeProbability = comulativeProbabilities.get(j);
                if (randomNumber <= comulativeProbability) {
                    chromosomeList.add(chromosomes.get(j));
                    break;
                }
            }
        }
        return chromosomeList;
    }


    private static void computeComulativeProbability() {
        float comulativeValue = (float) 0.0;
        for (Chromosome chromosome : chromosomes) {
            comulativeValue += chromosome.getProbabilityOfSelection();
            comulativeProbabilities.add(comulativeValue);
        }
    }

    private static void evaluateProbabilityOfChromosomes() {
        for (Chromosome chromosome : chromosomes) {
            chromosome.setProbabilityOfSelection(chromosome.getFitness() / totalFitness);
        }
    }

    private static float calculateTotalFitness() {
        float result = (float) 0.0;
        for (Chromosome chromosome : chromosomes) {
            result += chromosome.getFitness();
        }
        return result;
    }

    private static void generateRandomChromosomes() {
        for (int i = 0; i < TOTAL_NUMBER_OF_CHROMOSOMES; i++) {
            chromosomes.add(generateRandomChromosome());
        }
    }

    private static Chromosome generateRandomChromosome() {
        Chromosome chromosome = new Chromosome();
        int randomValueA = ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
        int randomValueB = ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
        int randomValueC = ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
        int randomValueD = ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
        chromosome.getGenes().add(randomValueA);
        chromosome.getGenes().add(randomValueB);
        chromosome.getGenes().add(randomValueC);
        chromosome.getGenes().add(randomValueD);
        return chromosome;
    }

    private static void evaluateFitnessValueOfChromosomes() {
        for (Chromosome chromosome : chromosomes) {
            int objectiveFunctionValue = evaluateObjectiveFunctionValue(chromosome);
            if(objectiveFunctionValue == 0){
                solutionFound = true;
                System.out.println(chromosome);
            }
            System.out.print(objectiveFunctionValue + " ");
            float fitness = evaluateFitnessValue(objectiveFunctionValue);
            chromosome.setFitness(fitness);
        }
        System.out.println();
    }

    private static float evaluateFitnessValue(int objectiveFunctionValue) {
        return (float) (1.0 / (1 + objectiveFunctionValue));
    }


    private static int evaluateObjectiveFunctionValue(Chromosome chromosome) {
        return Math.abs(chromosome.getGenes().get(0) + 2 * chromosome.getGenes().get(1)
                + 3 * chromosome.getGenes().get(2) + 4 * chromosome.getGenes().get(3) - 30);
    }
}
