package org.sf.jchemistry.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import org.sf.jchemistry.core.Element;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Swing panel containing the representation of the periodic table of elements.
 * 
 * @author ppinard
 */
public class PeriodicTablePanel extends JPanel {

    /**
     * Action listener for all the element buttons.
     * 
     * @author ppinard
     */
    private class ButtonActionListener implements ActionListener {

        /** Element associated with the button. */
        private final Element element;



        /**
         * Creates a new <code>ButtonActionListener</code>.
         * 
         * @param element
         *            element associated with the button
         */
        public ButtonActionListener(Element element) {
            this.element = element;
        }



        /**
         * Select or unselect an element. The method
         * {@link PeriodicTablePanel#fireElementSelectionChanged()} is called at
         * the end.
         * <p/>
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!selections.contains(element)) {
                if (!isMultiSelection)
                    selections.clear();

                selections.add(element);
            } else {
                selections.remove(element);
            }

            fireElementSelectionChanged();
        }

    }

    /**
     * Mouse motion listener for all the element buttons.
     * 
     * @author ppinard
     */
    private class ButtonMouseMotionListener implements MouseMotionListener {

        /** Element associated with the button. */
        private final Element element;



        /**
         * Creates a new <code>ButtonMouseMotionListener</code>.
         * 
         * @param element
         *            element associated with the button
         */
        public ButtonMouseMotionListener(Element element) {
            this.element = element;
        }



        /**
         * Not activated.
         * <p/>
         * {@inheritDoc}
         */
        @Override
        public void mouseDragged(MouseEvent e) {

        }



        /**
         * Calls all the <code>ElementMouseMotionListener</code> associated with
         * this panel.
         * <p/>
         * {@inheritDoc}
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            ElementEvent event = new ElementEvent(e.getSource(), element);

            for (ElementMouseMotionListener listener : mouseMotionListeners)
                listener.mouseMoved(event);
        }

    }

    /**
     * Internal selection listener to update the appearance of the element
     * buttons.
     * 
     * @author ppinard
     */
    private class InternalElementSelectionListener implements
            ElementSelectionListener {

        /** Border of the element button when selected. */
        private final Border selectedBorder;



        /**
         * Creates a new <code>InternalElementSelectionListener</code>.
         */
        public InternalElementSelectionListener() {
            selectedBorder = layout.getSelectedBorder();
        }



        /**
         * Resets the border of all element buttons and then set the border of
         * the selected element to the selected border as defined in the layout.
         * <p/>
         * {@inheritDoc}
         */
        @Override
        public void selectionChanged(ElementEvent event) {
            for (JComponent button : lookup.values())
                button.setBorder(null);

            for (Element selection : selections)
                lookup.get(selection).setBorder(selectedBorder);
        }

    }

    /**
     * Internal layout for the periodic table.
     * 
     * @author ppinard
     */
    private static class InternalPeriodicTableLayout implements
            PeriodicTableLayout {

        /** Color for the alkali metals. */
        private static final Color ALKALI_METALS_COLOR = new Color(204, 153,
                204);

        /** Color for the alkaline earth metals. */
        private static final Color ALKALINE_EARTH_METALS_COLOR = new Color(0,
                255, 255);

        /** Color for the transition metals. */
        private static final Color TRANSITION_METALS_COLOR = new Color(102,
                255, 153);

        /** Color for the post-transition metals. */
        private static final Color POST_TRANSITION_METALS_COLOR = new Color(
                153, 51, 204);

        /** Color for the METALLOIDS_POSITION. */
        private static final Color METALLOIDS_COLOR = new Color(255, 140, 255);

        /** Color for the non-metals. */
        private static final Color NON_METALS_COLOR = new Color(0, 204, 102);

        /** Color for the HALOGENES_POSITION. */
        private static final Color HALOGENS_COLOR = new Color(255, 204, 0);

        /** Color for the noble gases. */
        private static final Color NOBLE_GASES_COLOR = new Color(255, 0, 0);

        /** Color for the LANTHANIDES_POSITION. */
        private static final Color LANTHANIDES_COLOR = new Color(102, 0, 255);

        /** Color for the ACTINIDES_POSITION. */
        private static final Color ACTINIDES_COLOR = new Color(102, 102, 255);



        @Override
        public Color getActinidesColor() {
            return ACTINIDES_COLOR;
        }



        @Override
        public Color getAlkaliMetalsColor() {
            return ALKALI_METALS_COLOR;
        }



        @Override
        public Color getAlkalineEarthMetalsColor() {
            return ALKALINE_EARTH_METALS_COLOR;
        }



        @Override
        public Color getHalogensColor() {
            return HALOGENS_COLOR;
        }



        @Override
        public Color getLanthanidesColor() {
            return LANTHANIDES_COLOR;
        }



        @Override
        public Color getMetalloidsColor() {
            return METALLOIDS_COLOR;
        }



        @Override
        public Color getNobleGasesColor() {
            return NOBLE_GASES_COLOR;
        }



        @Override
        public Color getNonMetalsColor() {
            return NON_METALS_COLOR;
        }



        @Override
        public Color getPostTransitionMetalsColor() {
            return POST_TRANSITION_METALS_COLOR;
        }



        @Override
        public Border getSelectedBorder() {
            return BorderFactory.createLineBorder(Color.BLACK, 2);
        }



        @Override
        public Color getTransitionMetalsColor() {
            return TRANSITION_METALS_COLOR;
        }

    }

    /** Grid position for the alkali metals. */
    private static final Map<Element, String> ALKALI_METALS_POSITION;

    /** Grid position for the alkaline earth metals. */
    private static final Map<Element, String> ALKALINE_METALS_POSITION;

    /** Grid position for the transition metals. */
    private static final Map<Element, String> TRANSITION_METALS_POSITION;

    /** Grid position for the post-transition metals. */
    private static final Map<Element, String> POST_TRANSITION_METALS_POSITION;

    /** Grid position for the non- metals. */
    private static final Map<Element, String> NON_METALS_POSITION;

    /** Grid position for the metalloids. */
    private static final Map<Element, String> METALLOIDS_POSITION;

    /** Grid position for the halogens. */
    private static final Map<Element, String> HALOGENES_POSITION;

    /** Grid position for the noble gases. */
    private static final Map<Element, String> NOBLE_GASES_POSITION;

    /** Grid position for the lanthanides. */
    private static final Map<Element, String> LANTHANIDES_POSITION;

    /** Grid position for the actinides. */
    private static final Map<Element, String> ACTINIDES_POSITION;

    static {
        ALKALI_METALS_POSITION = new HashMap<Element, String>();
        ALKALI_METALS_POSITION.put(Element.Li, "0 1");
        ALKALI_METALS_POSITION.put(Element.Na, "0 2");
        ALKALI_METALS_POSITION.put(Element.K, "0 3");
        ALKALI_METALS_POSITION.put(Element.Rb, "0 4");
        ALKALI_METALS_POSITION.put(Element.Cs, "0 5");
        ALKALI_METALS_POSITION.put(Element.Fr, "0 6");

        ALKALINE_METALS_POSITION = new HashMap<Element, String>();
        ALKALINE_METALS_POSITION.put(Element.Be, "1 1");
        ALKALINE_METALS_POSITION.put(Element.Mg, "1 2");
        ALKALINE_METALS_POSITION.put(Element.Ca, "1 3");
        ALKALINE_METALS_POSITION.put(Element.Sr, "1 4");
        ALKALINE_METALS_POSITION.put(Element.Ba, "1 5");
        ALKALINE_METALS_POSITION.put(Element.Ra, "1 6");

        TRANSITION_METALS_POSITION = new HashMap<Element, String>();
        TRANSITION_METALS_POSITION.put(Element.Sc, "2 3");
        TRANSITION_METALS_POSITION.put(Element.Ti, "3 3");
        TRANSITION_METALS_POSITION.put(Element.V, "4 3");
        TRANSITION_METALS_POSITION.put(Element.Cr, "5 3");
        TRANSITION_METALS_POSITION.put(Element.Mn, "6 3");
        TRANSITION_METALS_POSITION.put(Element.Fe, "7 3");
        TRANSITION_METALS_POSITION.put(Element.Co, "8 3");
        TRANSITION_METALS_POSITION.put(Element.Ni, "9 3");
        TRANSITION_METALS_POSITION.put(Element.Cu, "10 3");
        TRANSITION_METALS_POSITION.put(Element.Zn, "11 3");

        TRANSITION_METALS_POSITION.put(Element.Y, "2 4");
        TRANSITION_METALS_POSITION.put(Element.Zr, "3 4");
        TRANSITION_METALS_POSITION.put(Element.Nb, "4 4");
        TRANSITION_METALS_POSITION.put(Element.Mo, "5 4");
        TRANSITION_METALS_POSITION.put(Element.Tc, "6 4");
        TRANSITION_METALS_POSITION.put(Element.Ru, "7 4");
        TRANSITION_METALS_POSITION.put(Element.Rh, "8 4");
        TRANSITION_METALS_POSITION.put(Element.Pd, "9 4");
        TRANSITION_METALS_POSITION.put(Element.Ag, "10 4");
        TRANSITION_METALS_POSITION.put(Element.Cd, "11 4");

        TRANSITION_METALS_POSITION.put(Element.Hf, "3 5");
        TRANSITION_METALS_POSITION.put(Element.Ta, "4 5");
        TRANSITION_METALS_POSITION.put(Element.W, "5 5");
        TRANSITION_METALS_POSITION.put(Element.Re, "6 5");
        TRANSITION_METALS_POSITION.put(Element.Os, "7 5");
        TRANSITION_METALS_POSITION.put(Element.Ir, "8 5");
        TRANSITION_METALS_POSITION.put(Element.Pt, "9 5");
        TRANSITION_METALS_POSITION.put(Element.Au, "10 5");
        TRANSITION_METALS_POSITION.put(Element.Hg, "11 5");

        POST_TRANSITION_METALS_POSITION = new HashMap<Element, String>();
        POST_TRANSITION_METALS_POSITION.put(Element.Al, "12 2");
        POST_TRANSITION_METALS_POSITION.put(Element.Ga, "12 3");
        POST_TRANSITION_METALS_POSITION.put(Element.In, "12 4");
        POST_TRANSITION_METALS_POSITION.put(Element.Tl, "12 5");
        POST_TRANSITION_METALS_POSITION.put(Element.Sn, "13 4");
        POST_TRANSITION_METALS_POSITION.put(Element.Pb, "13 5");
        POST_TRANSITION_METALS_POSITION.put(Element.Bi, "14 5");

        NON_METALS_POSITION = new HashMap<Element, String>();
        NON_METALS_POSITION.put(Element.H, "0 0");
        NON_METALS_POSITION.put(Element.C, "13 1");
        NON_METALS_POSITION.put(Element.N, "14 1");
        NON_METALS_POSITION.put(Element.O, "15 1");
        NON_METALS_POSITION.put(Element.P, "14 2");
        NON_METALS_POSITION.put(Element.S, "15 2");
        NON_METALS_POSITION.put(Element.Se, "15 3");

        METALLOIDS_POSITION = new HashMap<Element, String>();
        METALLOIDS_POSITION.put(Element.B, "12 1");
        METALLOIDS_POSITION.put(Element.Si, "13 2");
        METALLOIDS_POSITION.put(Element.Ge, "13 3");
        METALLOIDS_POSITION.put(Element.As, "14 3");
        METALLOIDS_POSITION.put(Element.Sb, "14 4");
        METALLOIDS_POSITION.put(Element.Te, "15 4");
        METALLOIDS_POSITION.put(Element.Po, "15 5");

        NOBLE_GASES_POSITION = new HashMap<Element, String>();
        NOBLE_GASES_POSITION.put(Element.He, "17 0");
        NOBLE_GASES_POSITION.put(Element.Ne, "17 1");
        NOBLE_GASES_POSITION.put(Element.Ar, "17 2");
        NOBLE_GASES_POSITION.put(Element.Kr, "17 3");
        NOBLE_GASES_POSITION.put(Element.Xe, "17 4");
        NOBLE_GASES_POSITION.put(Element.Rn, "17 5");

        HALOGENES_POSITION = new HashMap<Element, String>();
        HALOGENES_POSITION.put(Element.F, "16 1");
        HALOGENES_POSITION.put(Element.Cl, "16 2");
        HALOGENES_POSITION.put(Element.Br, "16 3");
        HALOGENES_POSITION.put(Element.I, "16 4");
        HALOGENES_POSITION.put(Element.At, "16 5");

        LANTHANIDES_POSITION = new HashMap<Element, String>();
        LANTHANIDES_POSITION.put(Element.Ce, "4 7");
        LANTHANIDES_POSITION.put(Element.Pr, "5 7");
        LANTHANIDES_POSITION.put(Element.Nd, "6 7");
        LANTHANIDES_POSITION.put(Element.Pm, "7 7");
        LANTHANIDES_POSITION.put(Element.Sm, "8 7");
        LANTHANIDES_POSITION.put(Element.Eu, "9 7");
        LANTHANIDES_POSITION.put(Element.Gd, "10 7");
        LANTHANIDES_POSITION.put(Element.Tb, "11 7");
        LANTHANIDES_POSITION.put(Element.Dy, "12 7");
        LANTHANIDES_POSITION.put(Element.Ho, "13 7");
        LANTHANIDES_POSITION.put(Element.Er, "14 7");
        LANTHANIDES_POSITION.put(Element.Tm, "15 7");
        LANTHANIDES_POSITION.put(Element.Yb, "16 7");
        LANTHANIDES_POSITION.put(Element.Lu, "17 7");

        ACTINIDES_POSITION = new HashMap<Element, String>();
        ACTINIDES_POSITION.put(Element.Th, "4 8");
        ACTINIDES_POSITION.put(Element.Pa, "5 8");
        ACTINIDES_POSITION.put(Element.U, "6 8");
        ACTINIDES_POSITION.put(Element.Np, "7 8");
        ACTINIDES_POSITION.put(Element.Pu, "8 8");
        ACTINIDES_POSITION.put(Element.Am, "9 8");
        ACTINIDES_POSITION.put(Element.Cm, "10 8");

    }

    /** Serial version UID. */
    private static final long serialVersionUID = -8419799973191039786L;



    /**
     * Returns a value between 0 and 255 corresponding to the brightness of the
     * specified color.
     * <p/>
     * From:
     * http://tech.chitgoks.com/2010/07/27/check-if-color-is-dark-or-light-
     * using-java/
     * 
     * @param c
     *            a color
     * @return brightness value
     */
    private static int getBrightness(Color c) {
        return (int) Math.sqrt(c.getRed() * c.getRed() * .241 + c.getGreen()
                * c.getGreen() * .691 + c.getBlue() * c.getBlue() * .068);
    }

    /** Selection listeners for the element buttons. */
    private final List<ElementSelectionListener> selectionListeners =
            new ArrayList<ElementSelectionListener>();

    /** Mouse motion listeners for the element buttons. */
    private final List<ElementMouseMotionListener> mouseMotionListeners =
            new ArrayList<ElementMouseMotionListener>();

    /** Internal look-up table to link the element to their buttons. */
    private final Map<Element, JComponent> lookup =
            new HashMap<Element, JComponent>();

    /** Selected elements. */
    protected final Set<Element> selections = new HashSet<Element>();

    /** Layout for the periodic table. */
    protected final PeriodicTableLayout layout;

    /** Flag whether multiple elements can be selected. */
    private boolean isMultiSelection = false;



    /**
     * Creates a new <code>PeriodicTablePanel</code>. The default appearance is
     * used.
     */
    public PeriodicTablePanel() {
        this(new InternalPeriodicTableLayout());
    }



    /**
     * Creates a new <code>PeriodicTablePanel</code>.
     * 
     * @param layout
     *            layout of the appearance
     */
    public PeriodicTablePanel(PeriodicTableLayout layout) {
        super();

        if (layout == null)
            throw new NullPointerException("layout == null");
        this.layout = layout;

        setLayout(new MigLayout(
                "",
                "[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]",
                "[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1[grow]1"));

        initElements(NON_METALS_POSITION, layout.getNonMetalsColor());
        initElements(ALKALI_METALS_POSITION, layout.getAlkaliMetalsColor());
        initElements(ALKALINE_METALS_POSITION,
                layout.getAlkalineEarthMetalsColor());
        initElements(TRANSITION_METALS_POSITION,
                layout.getTransitionMetalsColor());
        initElements(POST_TRANSITION_METALS_POSITION,
                layout.getPostTransitionMetalsColor());
        initElements(METALLOIDS_POSITION, layout.getMetalloidsColor());
        initElements(HALOGENES_POSITION, layout.getHalogensColor());
        initElements(NOBLE_GASES_POSITION, layout.getNobleGasesColor());

        initElements(LANTHANIDES_POSITION, layout.getLanthanidesColor());
        initElements(ACTINIDES_POSITION, layout.getActinidesColor());

        add(new JLabel("*"), "cell 2 5, align center");
        add(new JLabel("**"), "cell 2 6, align center");

        add(new JLabel("*"), "cell 3 7, align right");
        add(new JLabel("**"), "cell 3 8, align right");

        // Default listeners for the buttons
        selectionListeners.add(new InternalElementSelectionListener());
    }



    /**
     * Adds an element mouse motion listener.
     * 
     * @param listener
     *            listener
     */
    public void addElementMouseMotionListener(
            ElementMouseMotionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener == null");

        mouseMotionListeners.add(listener);
    }



    /**
     * Adds an element selection listener.
     * 
     * @param listener
     *            listener
     */
    public void addElementSelectionListener(ElementSelectionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener == null");
        selectionListeners.add(listener);
    }



    /**
     * Creates a component for the specified element.
     * 
     * @param element
     *            element
     * @param color
     *            color (based on the position of the element in the periodic
     *            table).
     * @return component
     */
    protected JComponent createElementButton(Element element, Color color) {
        JButton button = new JButton(element.symbol());

        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder());
        if (getBrightness(color) > 128)
            button.setForeground(Color.BLACK);
        else
            button.setForeground(Color.WHITE);

        button.addActionListener(new ButtonActionListener(element));
        button.addMouseMotionListener(new ButtonMouseMotionListener(element));

        lookup.put(element, button);
        return button;
    }



    /**
     * Fires {@link ElementSelectionListener#selectionChanged(ElementEvent)} for
     * all selection listeners.
     */
    protected void fireElementSelectionChanged() {
        ElementEvent event = new ElementEvent(this, getSelections());

        for (ElementSelectionListener listener : selectionListeners)
            listener.selectionChanged(event);
    }



    /**
     * Returns the defined element mouse motion listeners.
     * 
     * @return a copy of the list of element mouse motion listeners
     */
    public List<ElementMouseMotionListener> getElementMouseMotionListeners() {
        return new ArrayList<ElementMouseMotionListener>(mouseMotionListeners);
    }



    /**
     * Returns the defined element selection listeners.
     * 
     * @return a copy of the list of element selection listeners
     */
    public List<ElementSelectionListener> getElementSelectionListeners() {
        return new ArrayList<ElementSelectionListener>(selectionListeners);
    }



    /**
     * Returns the selection. If the panel allows for more than one selection,
     * the method {@link #getSelections()} should be used instead. In this case,
     * this method returns one of the selected element.
     * <p/>
     * Note: The method returns <code>null</code> if no element is selected.
     * 
     * @return selected element or <code>null</code> if no element is selected
     */
    @CheckForNull
    public Element getSelection() {
        if (selections.isEmpty())
            return null;
        else
            return selections.iterator().next();
    }



    /**
     * Returns all the selected elements. The set will be empty if no element is
     * selected.
     * 
     * @return selected elements
     */
    public Set<Element> getSelections() {
        return new HashSet<Element>(selections);
    }



    /**
     * Initialize the elements of a given family (alkaline, noble gases, etc.).
     * 
     * @param positions
     *            element positions
     * @param color
     *            color of the family
     */
    private void initElements(Map<Element, String> positions, Color color) {
        for (Entry<Element, String> entry : positions.entrySet()) {
            add(createElementButton(entry.getKey(), color), "grow, cell "
                    + entry.getValue() + ",w 25::, h 25::");
        }
    }



    /**
     * Returns whether multiple selection of elements is allowed.
     * 
     * @return <code>true</code> if more than one element can be selected,
     *         <code>false</code> otherwise
     */
    public boolean isMultiSelection() {
        return isMultiSelection;
    }



    /**
     * Removes the specified element mouse motion listener.
     * 
     * @param listener
     *            listener to be removed
     */
    public void removeElementMouseMotionListener(
            ElementMouseMotionListener listener) {
        mouseMotionListeners.remove(listener);
    }



    /**
     * Removes the specified element selection listener.
     * 
     * @param listener
     *            listener to be removed
     */
    public void removeElementSelectionListener(ElementSelectionListener listener) {
        selectionListeners.remove(listener);
    }



    /**
     * Sets whether more than one element can be selected.
     * 
     * @param mode
     *            <code>true</code> if more than one element can be selected,
     *            <code>false</code> otherwise
     */
    public void setMultiSelection(boolean mode) {
        isMultiSelection = mode;
    }



    /**
     * Selects the specified element in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param element
     *            element
     */
    public void setSelection(Element element) {
        selections.clear();

        if (element != null)
            selections.add(element);

        fireElementSelectionChanged();
    }



    /**
     * Sets the specified elements in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param elements
     *            elements
     */
    public void setSelection(Iterable<Element> elements) {
        selections.clear();

        for (Element element : elements)
            if (element != null)
                selections.add(element);

        fireElementSelectionChanged();
    }

}
