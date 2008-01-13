/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Test;

import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.MyResourceBundle;
import net.sf.mmm.util.nls.impl.SingleNlsTemplateResolver;

/**
 * This is the {@link TestCase} for {@link net.sf.mmm.util.nls.NlsException}.
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
    assertEquals("NullPointerException caused by \"" + source + "\"!", e.getMessage());
    NlsTemplateResolver st = new SingleNlsTemplateResolver(new MyResourceBundle());
    assertEquals("NullZeigerAusnahme verursacht durch \"" + source + "\"!", e.getLocalizedMessage(
        Locale.GERMAN, st));
  }
}
