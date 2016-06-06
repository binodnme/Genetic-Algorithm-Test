package com.binodnme;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class ChromosomeUtilsTest {

    @Test
    public void testGenerateRandomChromosome() throws Exception {
        //given
        int length = 4;
        int minValue = 5;
        int maxValue = 15;

        //when
        Chromosome chromosome = ChromosomeUtils.generateRandomChromosome(length, minValue, maxValue);

        //then
        assertEquals(length, chromosome.getGenes().size());

        for (Gene gene : chromosome.getGenes()) {
            assertThat(gene.getValue(), lessThan(maxValue));
            assertThat(gene.getValue(), greaterThanOrEqualTo(minValue));
        }
    }

    @Test
    public void testGenerateRandomChromosomes() {
        //given
        int totalChromosomes = 10;
        int length = 4;
        int minValue = 5;
        int maxValue = 15;

        //when
        List<Chromosome> chromosomeList = ChromosomeUtils.generateRandomChromosomes(totalChromosomes, length, minValue, maxValue);

        //then
        assertEquals(totalChromosomes, chromosomeList.size());

    }

    @Test
    public void testEvaluateProbabilityOfChromosomes() throws Exception {
        //given
        float totalFitness = 0.3453f;
        List<Chromosome> chromosomeList = new ArrayList<>();
        chromosomeList.add(new Chromosome());
        chromosomeList.add(new Chromosome());

        Chromosome chromosome1 = new Chromosome();
        chromosome1.setFitness(0.23f);

        Chromosome chromosome2 = new Chromosome();
        chromosome2.setFitness(0.23f);

        Chromosome chromosome3 = new Chromosome();
        chromosome3.setFitness(0.23f);

        chromosomeList.add(chromosome1);
        chromosomeList.add(chromosome2);
        chromosomeList.add(chromosome3);


        //when
        ChromosomeUtils.evaluateProbabilityOfChromosomes(chromosomeList, totalFitness);


        //then
        for (Chromosome chromosome : chromosomeList) {
            assertEquals(chromosome.getFitness() / totalFitness, chromosome.getProbabilityOfSelection(), 0.0);
        }

    }

    @Test
    public void testComputeComulativeProbability() throws Exception {
        //given
        List<Chromosome> chromosomeList = new ArrayList<>();
        chromosomeList.add(new Chromosome());
        chromosomeList.add(new Chromosome());

        Chromosome chromosome1 = new Chromosome();
        chromosome1.setProbabilityOfSelection(0.1f);

        Chromosome chromosome2 = new Chromosome();
        chromosome2.setProbabilityOfSelection(0.2f);

        Chromosome chromosome3 = new Chromosome();
        chromosome3.setProbabilityOfSelection(0.3f);

        chromosomeList.add(chromosome1);
        chromosomeList.add(chromosome2);
        chromosomeList.add(chromosome3);

        //when
        List<Float> cumulativeValues = ChromosomeUtils.computeComulativeProbability(chromosomeList);

        //then
        float cumulativeProbability = 0.0f;
        for (int i = 0; i < chromosomeList.size(); i++) {
            cumulativeProbability += chromosomeList.get(i).getProbabilityOfSelection();
            assertEquals(cumulativeProbability, cumulativeValues.get(i), 0.0);
        }
    }

    @Test
    public void testGetNewChromosomes() throws Exception {

        //given
        List<Chromosome> chromosomeList = new ArrayList<>();
        chromosomeList.add(new Chromosome());
        chromosomeList.add(new Chromosome());

        Chromosome chromosome1 = new Chromosome();
        chromosome1.setProbabilityOfSelection(0.1f);

        Chromosome chromosome2 = new Chromosome();
        chromosome2.setProbabilityOfSelection(0.2f);

        Chromosome chromosome3 = new Chromosome();
        chromosome3.setProbabilityOfSelection(0.3f);

        Chromosome chromosome4 = new Chromosome();
        chromosome4.setProbabilityOfSelection(0.4f);

        chromosomeList.add(chromosome1);
        chromosomeList.add(chromosome2);
        chromosomeList.add(chromosome3);
        chromosomeList.add(chromosome4);

        //when
        List<Float> cumulativeValues = ChromosomeUtils.computeComulativeProbability(chromosomeList);
        List<Chromosome> chromosomes = ChromosomeUtils.getNewChromosomes(chromosomeList, cumulativeValues);

        //then
        assertEquals(chromosomeList.size(), chromosomes.size());

    }

    @Test
    public void testSelectChromosomes() throws Exception {
        //given
        float totalFitness = 0.7f;

        List<Chromosome> chromosomeList = new ArrayList<>();
        chromosomeList.add(new Chromosome());
        chromosomeList.add(new Chromosome());

        Chromosome chromosome1 = new Chromosome();
        chromosome1.setFitness(0.1f);
        chromosome1.setProbabilityOfSelection(0.1f);

        Chromosome chromosome2 = new Chromosome();
        chromosome2.setFitness(0.2f);
        chromosome2.setProbabilityOfSelection(0.2f);

        Chromosome chromosome3 = new Chromosome();
        chromosome3.setFitness(0.1f);
        chromosome3.setProbabilityOfSelection(0.3f);

        Chromosome chromosome4 = new Chromosome();
        chromosome4.setFitness(0.3f);
        chromosome4.setProbabilityOfSelection(0.4f);

        chromosomeList.add(chromosome1);
        chromosomeList.add(chromosome2);
        chromosomeList.add(chromosome3);
        chromosomeList.add(chromosome4);

        //when
        List<Chromosome> chromosomes = ChromosomeUtils.selectChromosomes(chromosomeList, totalFitness);

        //then
        assertEquals(chromosomeList.size(), chromosomes.size());
    }

    @Test
    public void testGetParentsIndex() throws Exception {
        //given
        int chromosomeSize = 10;
        float crossOverPercentage = 0.4f;

        //when
        List<Integer> parentsIndex = ChromosomeUtils.getParentsIndex(chromosomeSize, crossOverPercentage);

        //then
        for (Integer integer : parentsIndex) {
            assertThat(integer, greaterThanOrEqualTo(0));
            assertThat(integer, lessThan(chromosomeSize));
        }

    }

    @Test
    public void testCrossOverChromosomes() throws Exception {
        //given
        float crossOverPercentage = 0.4f;

        List<Chromosome> chromosomeList = new ArrayList<>();
        chromosomeList.add(new Chromosome());
        chromosomeList.add(new Chromosome());

        Chromosome chromosome1 = new Chromosome();
        chromosome1.setProbabilityOfSelection(0.1f);

        Chromosome chromosome2 = new Chromosome();
        chromosome2.setProbabilityOfSelection(0.2f);

        Chromosome chromosome3 = new Chromosome();
        chromosome3.setProbabilityOfSelection(0.3f);

        Chromosome chromosome4 = new Chromosome();
        chromosome4.setProbabilityOfSelection(0.4f);

        chromosomeList.add(chromosome1);
        chromosomeList.add(chromosome2);
        chromosomeList.add(chromosome3);
        chromosomeList.add(chromosome4);

        //when
        ChromosomeUtils.crossOverChromosomes(chromosomeList, crossOverPercentage);



    }
}