/* $Id: UIReadSelectionValueIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the selected
 * {@link #getSelectedValue() value} of an object.
 * 
 * @param <V>
 *        is the templated type of the selectable value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSelectionValueIF<V> {

    /**
     * This method gets the currently selected value (item).
     * 
     * @return the selected value or <code>null</code> if no value is
     *         selected.
     */
    V getSelectedValue();

}
