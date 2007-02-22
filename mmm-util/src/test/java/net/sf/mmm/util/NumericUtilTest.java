/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import junit.framework.TestCase;


/**
 * This is the test-case for {@link net.sf.mmm.util.NumericUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NumericUtilTest extends TestCase {

    /**
     * The constructor.
     */
    public NumericUtilTest() {

        super();
    }

    private void verifySimplestNumber(Number value, Class simplestType) {
        Number simpleValue = NumericUtil.toSimplestNumber(value);
        assertEquals(simpleValue.doubleValue(), value.doubleValue(), 0.0);
        assertSame(simpleValue.getClass(), simplestType);
    }
    
    /**
     * 
     */
    public void testToSimplestNumber() {
        verifySimplestNumber(new Double(1.0), Byte.class);
        verifySimplestNumber(new Float(128.0), Short.class);
        verifySimplestNumber(new Double(1234567L), Integer.class);
        verifySimplestNumber(new Double(12345678901234576L), Long.class);
        verifySimplestNumber(new Double(42.25), Float.class);
        verifySimplestNumber(new Double(42.2F), Float.class);
        verifySimplestNumber(new Double(42.2), Double.class);
        //verifySimplestNumber(Double.parseDouble("42.2F"), Float.class);
        verifySimplestNumber(new Double(42.4242424242), Double.class);
    }
    
}
