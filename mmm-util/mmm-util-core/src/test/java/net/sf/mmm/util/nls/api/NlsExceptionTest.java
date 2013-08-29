/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link net.sf.mmm.util.nls.api.NlsException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NlsExceptionTest {

  /**
   * The constructor.
   */
  public NlsExceptionTest() {

    super();
  }

  protected void checkException(String expectedMessage, NlsException e, Locale locale, NlsTemplateResolver resolver) {

    UUID uuid = e.getUuid();
    Assert.assertNotNull(uuid);
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

    sw.getBuffer().setLength(0); // reset sw
    pw.println(expectedMessage);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    String expected = sw.toString();
    Assert.assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));
  }

  /**
   * Test fundamentals of {@link NlsException}.
   */
  @Test
  public void testNlsException() {

    String source = "bad boy";
    NlsException e = new NlsException(MyResourceBundle.ERR_NULL, source) {};
    String message = "NullPointerException caused by \"" + source + "\"!";
    Assert.assertEquals(message, e.getLocalizedMessage(AbstractNlsMessage.LOCALE_ROOT));
    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(new MyResourceBundle());
    resolver.initialize();
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    Assert.assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN, resolver));

    // test UUID and stacktrace
    checkException(e.getLocalizedMessage(), e, null, null);

    // test ROOT locale stacktrace
    checkException(message, e, AbstractNlsMessage.LOCALE_ROOT, resolver);

    // test German stacktrace
    checkException(messageDe, e, Locale.GERMAN, resolver);
  }
}
