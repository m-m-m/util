/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.regex.Pattern;

import net.sf.mmm.value.api.GenericValueIF;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.path.ComparatorIF} interface for the
 * equals function.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EqualsComparator implements ComparatorIF {

    /** the comparator symbol */
    public static final String SYMBOL = "==";

    /**
     * The constructor.
     * 
     */
    public EqualsComparator() {

        super();
    }

    /**
     * @see net.sf.mmm.configuration.base.path.ComparatorIF#accept(net.sf.mmm.value.api.GenericValueIF,
     *      java.lang.String, java.util.regex.Pattern)
     * {@inheritDoc}
     */
    public boolean accept(GenericValueIF value, String string, Pattern pattern) {

        String valueString = value.getString(null);
        if (pattern == null) {
            return string.equals(valueString);
        } else {
            return pattern.matcher(string).matches();
        }
    }

    /**
     * @see net.sf.mmm.configuration.base.path.ComparatorIF#getSymbol()
     * {@inheritDoc}
     */
    public String getSymbol() {

        return SYMBOL;
    }

}
