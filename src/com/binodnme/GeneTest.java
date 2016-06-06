package com.binodnme;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneTest {

    @Test
    public void geneTest(){
        Gene<Integer> gene = new Gene<>();
        gene.setValue(5);
        assertEquals(5, (int) gene.getValue());

        Gene<String> gene1 = new Gene<>();
        gene1.setValue("binod");
        assertEquals("binod", gene1.getValue());
    }

}