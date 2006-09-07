/* $Id$ */
package net.sf.mmm.nls.base;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Test;

import net.sf.mmm.nls.MyResourceBundle;
import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.api.StringTranslatorIF;
import net.sf.mmm.nls.base.AbstractResourceBundle;
import net.sf.mmm.nls.base.NlsMessage;
import net.sf.mmm.nls.base.SimpleStringTranslator;

import junit.framework.TestCase;

/**
 * This is the test case for {@link AbstractResourceBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractResourceBundleTest extends TestCase {

    public AbstractResourceBundleTest() {
        super();
    }

    @Test
    public void testKeys() {
        final MyResourceBundle myRB = new MyResourceBundle();
        Set<String> expectedKeys = new HashSet<String>();
        expectedKeys.add("ERR_NULL");
        expectedKeys.add("MSG_WELCOME");
        expectedKeys.add("MSG_BYE");
        Enumeration<String> keys = myRB.getKeys();
        int count = 0;
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            assertTrue(expectedKeys.contains(key));
            count++;
        }
        assertEquals(count, expectedKeys.size());
        StringTranslatorIF st = new SimpleStringTranslator(myRB, Locale.GERMAN);
        NlsMessageIF msg = new NlsMessage(MyResourceBundle.MSG_WELCOME, "Paul");
        assertEquals("Welcome \"Paul\"!", msg.getMessage());
        assertEquals("Willkommen \"Paul\"!", msg.getLocalizedMessage(st));
    }
}
