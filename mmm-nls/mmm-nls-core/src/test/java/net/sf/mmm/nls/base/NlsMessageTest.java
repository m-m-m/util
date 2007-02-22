/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import org.junit.Test;

import junit.framework.TestCase;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.api.StringTranslator;
import net.sf.mmm.nls.base.NlsMessageImpl;

/**
 * This is the {@link TestCase} for {@link net.sf.mmm.nls.base.NlsMessageImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsMessageTest extends TestCase {

  /**
   * The constructor.
   */
  public NlsMessageTest() {

    super();
  }

  /**
   * This method tests the {@link net.sf.mmm.nls.api.NlsMessage} implementation ({@link net.sf.mmm.nls.base.NlsMessageImpl}).
   */
  @Test
  public void testMessage() {

    String hello = "Hello";
    String helloDe = "Hallo ";
    String arg = "Joelle";
    String suffix = "!";
    final String msg = hello + "{0}" + suffix;
    final String msgDe = helloDe + "{0}" + suffix;
    NlsMessage testMessage = new NlsMessageImpl(msg, arg);
    assertEquals(testMessage.getInternationalizedMessage(), msg);
    assertEquals(testMessage.getArgumentCount(), 1);
    assertEquals(testMessage.getArgument(0), arg);
    assertEquals(testMessage.getMessage(), hello + arg + suffix);
    StringTranslator translatorDe = new StringTranslator() {

      public String translate(String message) {

        if (message.equals(msg)) {
          return msgDe;
        }
        return null;
      }
    };
    assertEquals(helloDe + arg + suffix, testMessage.getLocalizedMessage(translatorDe));
  }

  @Test
  public void testCascadedMessage() {

    final String integer = "integer";
    final String integerDe = "Ganze Zahl";
    final String real = "real[{0},{1}]";
    final String realDe = "relle Zahl[{0},{1}]";
    NlsMessage simpleMessageInteger = new NlsMessageImpl(integer);
    NlsMessage simpleMessageReal = new NlsMessageImpl(real, Double.valueOf(-5), Double.valueOf(5));
    final String err = "The given value must be of the type \"{0}\" but has the type \"{1}\"!";
    final String errDe = "Der angegebene Wert muss vom Typ \"{0}\" sein hat aber den Typ \"{1}\"!";
    NlsMessage cascadedMessage = new NlsMessageImpl(err, simpleMessageInteger, simpleMessageReal);
    StringTranslator translatorDe = new StringTranslator() {

      public String translate(String message) {

        if (message.equals(integer)) {
          return integerDe;
        } else if (message.equals(real)) {
          return realDe;
        } else if (message.equals(err)) {
          return errDe;
        }
        return null;
      }
    };
    String msgDe = cascadedMessage.getLocalizedMessage(translatorDe);
    assertEquals(
        "Der angegebene Wert muss vom Typ \"Ganze Zahl\" sein hat aber den Typ \"relle Zahl[-5,5]\"!",
        msgDe);
  }
}
