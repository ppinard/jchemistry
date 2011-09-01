package net.sf.jchemistry.util;

/**
 * Interface for object that contains a citation.
 * 
 * @author ppinard
 */
public interface Citable {

    /**
     * Returns the reference of this phase in the literature.
     * 
     * @return citation
     */
    public String getCitation();
}
