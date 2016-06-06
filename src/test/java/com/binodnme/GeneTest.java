package com.binodnme;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneTest {

    @Test
    public void geneTest(){
        Gene gene = new Gene();
        gene.setValue(5);
        assertEquals(5, gene.getValue());
    }

}