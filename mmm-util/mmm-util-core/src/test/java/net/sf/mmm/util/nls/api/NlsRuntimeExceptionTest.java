/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.test.SerializationHelper;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link net.sf.mmm.util.exception.api.NlsRuntimeException} and subclass(es).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsRuntimeExceptionTest extends Assert {

  /**
   * The constructor.
   */
  public NlsRuntimeExceptionTest() {

    super();
  }

  protected void checkException(String expectedMessage, NlsRuntimeException e, Locale locale,
      NlsTemplateResolver resolver) {

    UUID uuid = e.getUuid();
    assertNotNull(uuid);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    // test stacktrace via PrintWriter in default locale
    if (locale == null) {
      if (resolver == null) {
        e.printStackTrace(pw);
      } else {
        e.printStackTrace(Locale.getDefault(), resolver, pw);
      }
    } else {
      if (resolver == null) {
        e.printStackTrace(locale, pw);
      } else {
        e.printStackTrace(locale, resolver, pw);
      }
    }
    pw.flush();
    String stacktrace = sw.toString();

    // fails in maven surefire if we rest "sw" like this instead of creating new StringWriter()
    // However, in Eclipse it works with both ways... Java remains a mystery
    // sw.getBuffer().setLength(0);
    sw = new StringWriter();
    pw.println(expectedMessage);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    String expected = sw.toString();
    assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));
  }

  /**
   * Test fundamentals of {@link NlsRuntimeException}.
   */
  @Test
  public void testNlsRuntimeException() {

    String source = "bad boy";
    NlsRuntimeException e = new NlsRuntimeException(MyResourceBundle.ERR_NULL, source) {};
    String message = "NullPointerException caused by \"" + source + "\"!";
    assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(new MyResourceBundle());
    resolver.initialize();
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN, resolver));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null, null);

    // test ROOT locale stacktrace
    checkException(message, e, AbstractNlsMessage.LOCALE_ROOT, resolver);

    // test German stacktrace
    checkException(messageDe, e, Locale.GERMAN, resolver);
  }

  /**
   * Test fundamentals of {@link NlsRuntimeException}.
   */
  @Test
  public void testNlsRuntimeExceptionSerializable() {

    String source = "bad boy";
    NlsNullPointerException e = new NlsNullPointerException(source);
    NlsNullPointerException clone = SerializationHelper.reserialize(e);
    Assert.assertNotNull(clone);
    Assert.assertNotSame(e, clone);
    String message = "The object \"" + source + "\" is null!";
    assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String messageDe = "Das Objekt \"" + source + "\" ist null!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null, null);
  }

  /**
   * Test {@link NlsParseException} as this is using complex choice format.
   */
  @Test
  public void testNlsParseException() {

    String value = "value";
    Class type = String.class;
    NlsParseException parseException = new NlsParseException(value, type);
    assertEquals("Failed to parse \"" + value + "\" as value of the type \"" + type.getName() + "\"!", parseException
        .getNlsMessage().getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    String source = "source";
    parseException = new NlsParseException(value, type, source);
    assertEquals("Failed to parse \"" + value + "\" from \"" + source + "\" as value of the type \"" + type.getName()
        + "\"!", parseException.getNlsMessage().getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
  }
}
