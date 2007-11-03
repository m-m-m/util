/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Test;

import net.sf.mmm.nls.MyResourceBundle;
import net.sf.mmm.nls.api.NlsTranslator;

/**
 * This is the {@link TestCase} for {@link net.sf.mmm.nls.base.NlsException}.
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
    NlsTranslator st = new SimpleStringTranslator(new MyResourceBundle(), Locale.GERMAN);
  }
}
