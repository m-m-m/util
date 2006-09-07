/* $Id$ */
package net.sf.mmm.context.impl;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.value.api.ValueNotSetException;

import junit.framework.TestCase;

/**
 * This is the test case for {@link MutableContext}
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class MutableContextTest extends TestCase {

    public MutableContextTest() {
    }

    public void testContext() {
        MutableContextIF context = new MutableContext();
        assertEquals(context.getVariableNames().size(), 0);
        ContextIF immutableContext = context.getImmutableContext();
        assertTrue(ContextIF.class.isAssignableFrom(immutableContext.getClass()));
        assertFalse(MutableContextIF.class.isAssignableFrom(immutableContext.getClass()));
        String key = "key";
        String value = "value";
        assertFalse(context.hasValue(key));
        assertFalse(immutableContext.hasValue(key));
        assertTrue(context.getValue(key).isEmpty());
        try {
            context.getObject(key);
            fail("Exception expected");
        } catch (ValueNotSetException e) {
            
        }
        context.setObject(key, value);
        assertTrue(context.hasValue(key));
        assertTrue(immutableContext.hasValue(key));
        assertSame(value, context.getObject(key));
        assertSame(value, immutableContext.getObject(key));
        assertSame(value, context.getValue(key).getString());
        assertSame(value, immutableContext.getValue(key).getString());
        assertEquals(1, immutableContext.getVariableNames().size());
        MutableContextIF childContext = immutableContext.createChildContext();
        assertTrue(childContext.hasValue(key));
        childContext.unsetValue(key);
        assertTrue(childContext.hasValue(key));
        assertEquals(childContext.getVariableNames().size(), 1);
        assertSame(value, childContext.getObject(key));
        String value2 = "value2";
        childContext.setObject(key, value2);
        assertSame(value2, childContext.getObject(key));
        String key3 = "key3";
        Object value3 = context;
        context.setObject(key3, value3);        
        assertSame(value3, childContext.getObject(key3));
    }
    
}
