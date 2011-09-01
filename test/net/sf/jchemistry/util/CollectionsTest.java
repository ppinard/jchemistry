package net.sf.jchemistry.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {

    @Test
    public void testJoinEmpty() {
        List<String> collection = new ArrayList<String>();

        assertEquals("", Collections.join(collection, ","));
    }



    @Test
    public void testJoinOneItem() {
        List<String> collection = new ArrayList<String>();
        collection.add("item1");

        assertEquals("item1", Collections.join(collection, ","));
    }



    @Test
    public void testJoinTwoItems() {
        List<String> collection = new ArrayList<String>();
        collection.add("item1");
        collection.add("item2");

        assertEquals("item1,item2", Collections.join(collection, ","));
    }
}
