/* $Id$ */
package net.sf.mmm.nls.base;

import java.util.Locale;

import net.sf.mmm.nls.MyResourceBundle;
import net.sf.mmm.nls.api.StringTranslator;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link net.sf.mmm.nls.base.NlsException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsExceptionTest extends TestCase {

    /**
     * The constructor.
     * 
     */
    public NlsExceptionTest() {

        super();
    }

    public void testNlsException() {
        
        String source = "bad boy";
        NlsException e = new NlsException(MyResourceBundle.ERR_NULL, source) {};
        assertEquals("NullPointerException caused by \""+ source+"\"!", e.getMessage());
        StringTranslator st = new SimpleStringTranslator(new MyResourceBundle(), Locale.GERMAN);
    }
}
