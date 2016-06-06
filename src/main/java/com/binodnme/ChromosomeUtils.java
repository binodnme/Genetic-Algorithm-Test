package com.binodnme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class ChromosomeUtils {

    public static List<Chromosome> generateRandomChromosomes(int numberOfChromosomes, int length, int minValue, int maxValue) {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < numberOfChromosomes; i++) {
            chromosomes.add(generateRandomChromosome(length, minValue, maxValue));
        }

        return chromosomes;
    }

    public static Chromosome generateRandomChromosome(int lengthOfChromosome, int minValue, int maxValue) {
        Chromosome chromosome = new Chromosome();
        for (int i = 0; i < lengthOfChromosome; i++) {
            int randomValueA = ThreadLocalRandom.current().nextInt(minValue, maxValue);
            chromosome.getGenes().add(new Gene(randomValueA));

        }
        return chromosome;
    }


    //SELECTION
    public static List<Chromosome> selectChromosomes(List<Chromosome> chromosomes, float totalFitness) {
        //evaluate probability
        evaluateProbabilityOfChromosomes(chromosomes, totalFitness);

        //compute comulative probability
        List<Float> comulativeProbabilities = computeComulativeProbability(chromosomes);

        //select new chromosomes
        return getNewChromosomes(chromosomes, comulativeProbabilities);
    }

    public static void evaluateProbabilityOfChromosomes(List<Chromosome> chromosomes, float totalFitness) {
        for (Chromosome chromosome : chromosomes) {
            chromosome.setProbabilityOfSelection(chromosome.getFitness() / totalFitness);
        }
    }

    public static List<Float> computeComulativeProbability(List<Chromosome> chromosomes) {
        List<Float> comulativeProbabilities = new ArrayList<>();
        float comulativeValue = 0.0f;

        for (Chromosome chromosome : chromosomes) {
            comulativeValue += chromosome.getProbabilityOfSelection();
            comulativeProbabilities.add(comulativeValue);
        }

        return comulativeProbabilities;
    }

    public static List<Chromosome> getNewChromosomes(List<Chromosome> chromosomes, List<Float> comulativeProbabilities) {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < chromosomes.size(); i++) {
            float randomNumber = ThreadLocalRandom.current().nextFloat(); // generates number between 0 (inclusive) and 1 (exclusive)

            for (int j = 0; j < chromosomes.size(); j++) {
                float comulativeProbability = comulativeProbabilities.get(j);
                if (randomNumber <= comulativeProbability) {
                    chromosomeList.add(chromosomes.get(j));
                    break;
                }
            }
        }
        return chromosomeList;
    }


    //CROSSOVER
    public static void crossOverChromosomes(List<Chromosome> chromosomes, float crossOverPercentage) {
        List<Integer> parentsIndex = getParentsIndex(chromosomes.size(), crossOverPercentage);

        int totalNumberOfGenesInAChromosome = chromosomes.get(0).getGenes().size();
        for (int i = 0; i < parentsIndex.size(); i++) {
            int cutPoint = ThreadLocalRandom.current().nextInt(1, totalNumberOfGenesInAChromosome);
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

            for (int j = cutPoint; j < totalNumberOfGenesInAChromosome; j++) {
                newChromosome.getGenes().add(parentB.getGenes().get(j));
            }

            chromosomes.set(parentsIndex.get(i), newChromosome);
        }
    }

    public static List<Integer> getParentsIndex(int chromosomeSize, float crossOverPercentage) {
        List<Integer> parentsIndex = new ArrayList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            float randomNumber = ThreadLocalRandom.current().nextFloat(); // generates number between 0 (inclusive) and 1 (exclusive)
            if (randomNumber < crossOverPercentage) {
                parentsIndex.add(i);
            }
        }
        return parentsIndex;
    }

    public static void mutateChromosomes(List<Chromosome> chromosomes, float mutationPercentage, int minValue, int maxValue) {
        int totalChromosomes = chromosomes.size();
        int noOfGenesInAChromosome = chromosomes.get(0).getGenes().size();
        int totalRandomNumberToBeGenerated = (int) (mutationPercentage * totalChromosomes * noOfGenesInAChromosome);
        for (int i = 0; i < totalRandomNumberToBeGenerated; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, totalChromosomes * noOfGenesInAChromosome); //including 0 and excluding 30;
            int chromosomeIndex = randomIndex / noOfGenesInAChromosome;
            int geneIndex = randomIndex % noOfGenesInAChromosome;
            int randomNumber = ThreadLocalRandom.current().nextInt(minValue, maxValue);
            chromosomes.get(chromosomeIndex).getGenes().set(geneIndex, new Gene(randomNumber));
        }
    }
}