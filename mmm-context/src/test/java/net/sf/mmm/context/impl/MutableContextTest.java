/* $Id$ */
package net.sf.mmm.context.impl;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;

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
        String key = "key";
        String value = "value";
        context.setVariable(key, value);
        assertSame(value, context.getObject(key));
        assertSame(value, context.getValue(key).getString());
        MutableContextIF childContext = context.createChildContext();
        childContext.unsetVariable(key);
        assertEquals(childContext.getVariableNames().size(), 1);
        assertSame(value, childContext.getObject(key));
        String value2 = "value2";
        childContext.setVariable(key, value2);
        assertSame(value2, childContext.getObject(key));
        String key3 = "key3";
        String value3 = "value3";
        context.setVariable(key3, value3);        
        assertSame(value3, childContext.getObject(key3));
    }
    
}
