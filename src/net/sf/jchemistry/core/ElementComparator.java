package net.sf.jchemistry.core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator based on the atomic number of the element.
 * 
 * @author ppinard
 */
public class ElementComparator implements Comparator<Element>, Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -236437422763230177L;



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
