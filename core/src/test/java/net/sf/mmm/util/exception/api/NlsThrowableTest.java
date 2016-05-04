/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import net.sf.mmm.test.SerializationHelper;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.session.api.UserSessionAccess;

/**
 * This is the test-case for {@link net.sf.mmm.util.exception.api.NlsException}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsThrowableTest extends Assert {

  /**
   * The constructor.
   */
  public NlsThrowableTest() {

    super();
  }

  protected void checkException(String expectedMessage, NlsThrowable e, Locale locale) {

    UUID uuid = e.getUuid();
    assertNotNull(uuid);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    // test stacktrace via PrintWriter in default locale
    if (locale == null) {
      e.printStackTrace(UserSessionAccess.getUserLocale(), pw);
    } else {
      e.printStackTrace(locale, pw);
    }
    pw.flush();
    String stacktrace = sw.toString();

    sw.getBuffer().setLength(0); // reset sw
    pw.println(expectedMessage);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    String expected = sw.toString();
    assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));
    LoggerFactory.getLogger(NlsThrowableTest.class)
        .error("This is a test and should contain the UUID and code of the exception", e);
  }

  /**
   * Test fundamentals of {@link NlsException}.
   */
  @Test
  public void testNlsException() {

    String source = "bad boy";
    NlsException e = new NlsException(NlsAccess.getFactory().create(MyResourceBundle.ERR_NULL, source)) {

      /** TODO: javadoc. */
      private static final long serialVersionUID = 1L;
    };
    String message = "NullPointerException caused by \"" + source + "\"!";
    assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null);

    // test ROOT locale stacktrace
    checkException(message, e, AbstractNlsMessage.LOCALE_ROOT);

    // test German stacktrace
    checkException(messageDe, e, Locale.GERMAN);
  }

  /**
   * Test {@link NlsParseException} as this is using complex choice format.
   */
  @Test
  public void testNlsParseException() {

    String value = "value";
    Class type = String.class;
    NlsParseException parseException = new NlsParseException(value, type);
    assertEquals("Failed to parse \"" + value + "\" as value of the type \"" + type.getName() + "\"!",
        parseException.getNlsMessage().getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String source = "source";
    parseException = new NlsParseException(value, type, source);
    assertEquals("Failed to parse \"" + value + "\" from \"" + source + "\" as value of the type \""
        + type.getName() + "\"!",
        parseException.getNlsMessage().getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
  }

  /**
   * Test fundamentals of {@link NlsRuntimeException}.
   */
  @Test
  public void testNlsRuntimeException() {

    String source = "bad boy";
    NlsRuntimeException e = new NlsRuntimeException(
        NlsAccess.getFactory().create(MyResourceBundle.ERR_NULL, source)) {

      /** TODO: javadoc. */
      private static final long serialVersionUID = 1L;

      @Override
      public String getCode() {

        return "CustomCode";
      }
    };
    String message = "NullPointerException caused by \"" + source + "\"!";
    assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null);

    // test ROOT locale stacktrace
    checkException(message, e, AbstractNlsMessage.LOCALE_ROOT);

    // test German stacktrace
    checkException(messageDe, e, Locale.GERMAN);
  }

  /**
   * Test fundamentals of {@link NlsRuntimeException}.
   */
  @Test
  public void testNlsRuntimeExceptionSerializable() {

    String source = "bad boy";
    NlsNullPointerException e = new NlsNullPointerException(source);
    NlsNullPointerException clone = SerializationHelper.reserialize(e);
    assertNotNull(clone);
    assertNotSame(e, clone);
    String message = "The object \"" + source + "\" is null!";
    assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String messageDe = "Das Objekt \"" + source + "\" ist null!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null);
  }

}
