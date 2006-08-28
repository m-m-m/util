/* $Id$ */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValueIF;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link StringValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringValueTest extends AbstractGenericValueTest {

    /**
     * The constructor.
     */
    public StringValueTest() {

        super();
    }

    /**
     * @see net.sf.mmm.value.impl.AbstractGenericValueTest#convert(java.lang.Object)
     *      {@inheritDoc}
     */
    @Override
    protected GenericValueIF convert(Object plainValue) {

        String s;
        if (plainValue == null) {
            s = null;
        } else if (plainValue instanceof Class) {
            s = ((Class) plainValue).getName();
        } else {
            s = plainValue.toString();
        }
        return new StringValue(s);
    }
}
