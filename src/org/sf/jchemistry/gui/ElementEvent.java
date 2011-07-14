package org.sf.jchemistry.gui;

import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;

import org.sf.jchemistry.core.Element;

/**
 * Event object returned by listener of the periodic table panel.
 * 
 * @author ppinard
 */
public class ElementEvent extends EventObject {

    /** Serial version UID. */
    private static final long serialVersionUID = 1145342313169779672L;

    /** Elements passed by the event. */
    private final Set<Element> elements;



    /**
     * Creates a new <code>ElementEvent</code>.
     * 
     * @param source
     *            source of the event
     */
    private ElementEvent(Object source) {
        super(source);
        elements = new HashSet<Element>();
    }



    /**
     * Creates a new <code>ElementEvent</code>.
     * 
     * @param source
     *            source of the event
     * @param elements
     *            set of elements
     */
    public ElementEvent(Object source, Set<Element> elements) {
        this(source);
        if (elements.isEmpty())
            throw new IllegalArgumentException(
                    "At least one element must be specified");

        this.elements.addAll(elements);
    }



    /**
     * Creates a new <code>ElementEvent</code>.
     * 
     * @param source
     *            source of the event
     * @param element
     *            an element
     */
    public ElementEvent(Object source, Element element) {
        this(source);
        elements.add(element);
    }



    /**
     * Returns the elements associated with this event.
     * 
     * @return elements
     */
    public Set<Element> getElements() {
        return elements;
    }



    /**
     * Returns an element associated with this event. If more than one element
     * is part of this event, one is returned.
     * 
     * @return element
     */
    public Element getElement() {
        return elements.iterator().next();
    }

}
