/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.text.api.Justification;
import net.sf.mmm.util.text.api.JustificationBuilder;

/**
 * This is the test-case for {@link JustificationBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public class JustificationBuilderTest {

  /**
   * This method gets an instance of the {@link JustificationBuilder}.
   * 
   * @return the {@link JustificationBuilder}.
   */
  protected JustificationBuilder getJustificationBuilder() {

    JustificationBuilderImpl builder = new JustificationBuilderImpl();
    builder.initialize();
    return builder;
  }

  /**
   * This method tests the examples specified from the javadoc of
   * {@link JustificationBuilder}.
   */
  @Test
  public void testExamples() {

    JustificationBuilder builder = getJustificationBuilder();
    Justification justification;
    justification = builder.build("0+4");
    Assert.assertEquals("0042", justification.justify("42"));
    justification = builder.build(".-4");
    Assert.assertEquals("42..", justification.justify("42"));
    justification = builder.build("_~11");
    Assert.assertEquals("____42_____", justification.justify("42"));
    justification = builder.build("_+5");
    Assert.assertEquals("Hello World", justification.justify("Hello World"));
    justification = builder.build("_+5|");
    Assert.assertEquals("Hello", justification.justify("Hello World"));
  }

}
