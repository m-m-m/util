/* $Id$ */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.value.api.GenericValueIF;
import net.sf.mmm.value.base.AbstractGenericValue;

import junit.framework.TestCase;

/**
 * This is the abstract {@link TestCase} for testing sub-classes of
 * {@link AbstractGenericValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractGenericValueTest extends TestCase {

    /**
     * The constructor.
     */
    public AbstractGenericValueTest() {

        super();
    }

    /**
     * This method converts the given <code>plainValue</code> to a
     * {@link GenericValueIF}.
     * 
     * @param plainValue
     *        is the object to convert.
     * @return is the object as generic value.
     */
    protected abstract GenericValueIF convert(Object plainValue);

    public void testEmptyValue() {
        
        GenericValueIF valueEmpty = convert(null);
        assertFalse(valueEmpty.hasValue());
        assertNull(valueEmpty.getObject(null));
        assertNull(valueEmpty.getBoolean(null));
        assertNull(valueEmpty.getDate(null));
        assertNull(valueEmpty.getDouble(null));
        assertNull(valueEmpty.getInteger(defaultValue));
    }
    
    @Test
    public void testBoolean() {

        GenericValueIF valueTrue = convert(Boolean.TRUE);
        assertTrue(valueTrue.getBoolean());
        assertTrue(valueTrue.getBoolean(null).booleanValue());
        assertTrue(valueTrue.getValue(Boolean.class).booleanValue());
        assertEquals(Boolean.TRUE.toString(), valueTrue.getString());

        GenericValueIF valueFalse = convert(Boolean.FALSE);
        assertFalse(valueFalse.getBoolean());
        assertFalse(valueFalse.getBoolean(null).booleanValue());
        assertFalse(valueFalse.getValue(Boolean.class).booleanValue());
        assertEquals(Boolean.FALSE.toString(), valueFalse.getString());
    }

    @Test
    public void testJavaClass() {

        GenericValueIF valueString = convert(String.class);
        assertSame(String.class, valueString.getJavaClass());
        assertSame(String.class, valueString.getValue(Class.class));
        String s = valueString.getJavaClassInstance(String.class);
        assertEquals(new String(), s);
    }

    @Test
    public void testNumbers() {

        byte byteValue = 42;
        checkNumber(convert(new Byte(byteValue)));
        short shortValue = 4242;
        checkNumber(convert(new Short(shortValue)));
        checkNumber(convert(new Integer(424242)));
        checkNumber(convert(new Long(4242424242424242L)));
        checkNumber(convert(new Float(42.25)));
        checkNumber(convert(new Double(42.42)));
    }

    /**
     * @param value
     *        must be a generic value containing a number.
     */
    public void checkNumber(GenericValueIF value) {

        // this will only work for Number of java.lang.*
        assertTrue(value.hasValue());
        Number number = value.getNumber();
        assertEquals(number.toString(), value.getString());
        assertEquals(number, value.getValue(Number.class));
        number = NumericUtil.toSimplestNumber(number);
        double doubleValue = number.doubleValue();
        assertEquals(doubleValue, value.getDouble(), 0);
        assertEquals(doubleValue, value.getDouble(null).doubleValue(), 0);
        assertEquals(doubleValue, value.getValue(Double.class).doubleValue(), 0);
        assertEquals(doubleValue, value.getValue(Double.class, null).doubleValue(), 0);
        Class type = number.getClass();
        if (type != Double.class) {
            float floatValue = number.floatValue();
            assertEquals(floatValue, value.getValue(Float.class).floatValue(), 0);
            assertEquals(floatValue, value.getValue(float.class).floatValue(), 0);
            if (type != Float.class) {
                long longValue = number.longValue();
                assertEquals(longValue, value.getLong());
                assertEquals(longValue, value.getLong(null).longValue());
                assertEquals(longValue, value.getValue(Long.class).longValue());
                assertEquals(longValue, value.getValue(long.class).longValue());
                if (type != Long.class) {
                    int intValue = number.intValue();
                    assertEquals(intValue, value.getInteger());
                    assertEquals(intValue, value.getInteger(null).intValue());
                    assertEquals(intValue, value.getValue(Integer.class).intValue());
                    assertEquals(intValue, value.getValue(int.class).intValue());
                    if (type != Integer.class) {
                        short shortValue = number.shortValue();
                        assertEquals(shortValue, value.getValue(Short.class).shortValue());
                        assertEquals(shortValue, value.getValue(short.class).shortValue());
                        if (type != Short.class) {
                            byte byteValue = number.byteValue();
                            assertEquals(byteValue, value.getValue(Byte.class).byteValue());
                            assertEquals(byteValue, value.getValue(byte.class).byteValue());
                        }
                    }
                }
            }
        }
    }

}
