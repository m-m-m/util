/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;
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

  @Test
  public void testNlsException() {

    String source = "bad boy";
    NlsException e = new NlsException(MyResourceBundle.ERR_NULL, source) {};
    String message = "NullPointerException caused by \"" + source + "\"!";
    Assert.assertEquals(message, e.getLocalizedMessage(Locale.ROOT));
    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(new MyResourceBundle());
    resolver.initialize();
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    Assert.assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN, resolver));

    // test UUID and stacktrace
    UUID uuid = e.getUuid();
    Assert.assertNotNull(uuid);
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
    String expected = sw.toString();
    Assert.assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));

    // test German stacktrace
    StringBuilder buffer = new StringBuilder();
    e.printStackTrace(Locale.ROOT, resolver, buffer);
    stacktrace = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(message);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    expected = sw.toString();
    Assert.assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));

    // test German stacktrace
    buffer = new StringBuilder();
    e.printStackTrace(Locale.GERMAN, resolver, buffer);
    String stacktraceDe = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(messageDe);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    Assert.assertTrue(stacktraceDe.contains(sw.toString()));
  }

  @Test
  public void testNlsException2() {

    String source = "bad boy";
    NlsMessagesBundleUtilCore bundle = NlsAccess.getBundleFactory().createBundle(NlsMessagesBundleUtilCore.class);
    NlsException e = new NlsException(bundle.errorArgumentNull(source)) {};
    String message = "NullPointerException caused by \"" + source + "\"!";
    Assert.assertEquals(message, e.getLocalizedMessage(Locale.ROOT));
    NlsTemplateResolverImpl resolver = new NlsTemplateResolverImpl(new MyResourceBundle());
    resolver.initialize();
    String messageDe = "NullZeigerAusnahme verursacht durch \"" + source + "\"!";
    Assert.assertEquals(messageDe, e.getLocalizedMessage(Locale.GERMAN, resolver));

    // test UUID and stacktrace
    UUID uuid = e.getUuid();
    Assert.assertNotNull(uuid);
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
    String expected = sw.toString();
    Assert.assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));

    // test German stacktrace
    StringBuilder buffer = new StringBuilder();
    e.printStackTrace(Locale.ROOT, resolver, buffer);
    stacktrace = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(message);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    expected = sw.toString();
    Assert.assertTrue(stacktrace + "\n*****\n" + expected, stacktrace.contains(expected));

    // test German stacktrace
    buffer = new StringBuilder();
    e.printStackTrace(Locale.GERMAN, resolver, buffer);
    String stacktraceDe = buffer.toString();
    sw.getBuffer().setLength(0); // reset sw
    pw.println(messageDe);
    pw.println(uuid.toString());
    pw.print("\tat ");
    pw.flush();
    Assert.assertTrue(stacktraceDe.contains(sw.toString()));
  }
}
