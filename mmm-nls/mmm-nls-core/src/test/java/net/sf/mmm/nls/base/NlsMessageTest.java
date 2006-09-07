/* $Id$ */
package net.sf.mmm.nls.base;

import org.junit.Test;

import junit.framework.TestCase;

import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.api.StringTranslatorIF;
import net.sf.mmm.nls.base.NlsMessage;

/**
 * This is the {@link TestCase} for {@link net.sf.mmm.nls.base.NlsMessage}.
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
    @Test
    public void testMessage() {

        String hello = "Hello";
        String helloDe = "Hallo ";
        String arg = "Joelle";
        String suffix = "!";
        final String msg = hello + "{0}" + suffix;
        final String msgDe = helloDe + "{0}" + suffix;
        NlsMessageIF testMessage = new NlsMessage(msg, arg);
        assertEquals(testMessage.getInternationalizedMessage(), msg);
        assertEquals(testMessage.getArgumentCount(), 1);
        assertEquals(testMessage.getArgument(0), arg);
        assertEquals(testMessage.getMessage(), hello + arg + suffix);
        StringTranslatorIF translatorDe = new StringTranslatorIF() {

            public String translate(String message) {

                if (message.equals(msg)) {
                    return msgDe;
                }
                return null;
            }
        };
        assertEquals(testMessage.getLocalizedMessage(translatorDe), helloDe + arg + suffix);
    }

}