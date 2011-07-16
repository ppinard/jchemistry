package org.sf.jchemistry.core;

import java.util.Comparator;

/**
 * Comparator based on the atomic number of the element.
 * 
 * @author ppinard
 */
public class ElementComparator implements Comparator<Element> {

    @Override
    public int compare(Element o1, Element o2) {
        int z1 = o1.z();
        int z2 = o2.z();

        if (z1 < z2)
            return -1;
        else if (z1 > z2)
            return 1;
        else
            return 0;
    }

}
