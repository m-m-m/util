/* $Id$ */
package net.sf.mmm.nls;

import junit.framework.TestCase;

import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.api.StringTranslatorIF;
import net.sf.mmm.nls.base.NlsMessage;

/**
 * This is the test case for {@link net.sf.mmm.nls.base.NlsMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsMessageTest extends TestCase {

    /**
     * The constructor.
     */
    public NlsMessageTest() {

        super();
    }

    /**
     * This method tests the {@link net.sf.mmm.nls.api.NlsMessageIF}
     * implementation ({@link net.sf.mmm.nls.base.NlsMessage}).
     */
    public void testMessage() {

        String hello = "Hello";
        String hello_de = "Hallo ";
        String arg = "Joelle";
        String suffix = "!";
        final String msg = hello + "{0}" + suffix;
        final String msg_de = hello_de + "{0}" + suffix;
        NlsMessageIF testMessage = new NlsMessage(msg, arg);
        assertEquals(testMessage.getInternationalizedMessage(), msg);
        assertEquals(testMessage.getArgumentCount(), 1);
        assertEquals(testMessage.getArgument(0), arg);
        assertEquals(testMessage.getMessage(), hello + arg + suffix);
        StringTranslatorIF translator_de = new StringTranslatorIF() {

            public String translate(String message) {

                if (message.equals(msg)) {
                    return msg_de;
                }
                return null;
            }
        };
        assertEquals(testMessage.getLocalizedMessage(translator_de), hello_de + arg + suffix);
    }

}