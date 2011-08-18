/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.gui;

import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;

import net.sf.jchemistry.core.Element;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Event object returned by listener of the periodic table panel.
 * 
 * @author Philippe T. Pinard
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
    @CheckForNull
    public Element getElement() {
        if (elements.isEmpty())
            return null;
        else
            return elements.iterator().next();
    }

}
