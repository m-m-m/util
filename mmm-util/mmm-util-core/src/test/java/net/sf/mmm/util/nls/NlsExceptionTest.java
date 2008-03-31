/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;

import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl;

/**
 * This is the test-case for {@link net.sf.mmm.util.nls.NlsException}.
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

  @Test
  public void testNlsException() {

    String source = "bad boy";
    NlsException e = new NlsException(MyResourceBundle.ERR_NULL, source) {};
    String message = "NullPointerException caused by \"" + source + "\"!";
    assertEquals(message, e.getMessage());
    NlsTemplateResolver resolver = new NlsTemplateResolverImpl(new MyResourceBundle());
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN, resolver));

    // test UUID and stacktrace
    UUID uuid = e.getUuid();
    assertNotNull(uuid);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    // test stacktrace via PrintWriter in default locale
    e.printStackTrace(pw);
    pw.flush();
    String stacktrace = sw.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(e.getLocalizedMessage());
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    assertTrue(stacktrace.startsWith(sw.toString()));

    // test German stacktrace
    StringBuilder buffer = new StringBuilder();
    e.printStackTrace(Locale.ROOT, resolver, buffer);
    stacktrace = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(message);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    assertTrue(stacktrace.startsWith(sw.toString()));

    // test German stacktrace
    buffer = new StringBuilder();
    e.printStackTrace(Locale.GERMAN, resolver, buffer);
    String stacktraceDe = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(messageDe);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    assertTrue(stacktraceDe.startsWith(sw.toString()));
  }
}
