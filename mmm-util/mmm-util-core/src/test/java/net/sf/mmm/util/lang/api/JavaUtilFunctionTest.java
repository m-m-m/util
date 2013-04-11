/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link java.util.function} (back-port).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class JavaUtilFunctionTest extends Assert {

  /**
   * Tests of {@link Function} interface (Classloading, etc.).
   */
  @Test
  public void testFunction() {

    Function<String, Double> doubleParser = new Function<String, Double>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public Double apply(String t) {

        return Double.valueOf(t);
      }
    };

    String string = "1.42";
    assertEquals(Double.valueOf(string), doubleParser.apply(string));
  }
}
