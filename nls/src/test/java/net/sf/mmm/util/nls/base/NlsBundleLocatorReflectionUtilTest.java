/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

/**
 * Test of {@link NlsBundleLocatorReflectionUtil}.
 */
public class NlsBundleLocatorReflectionUtilTest extends NlsBundleLocatorTest {

  @Override
  protected NlsBundleLocator getBundleLocator() {

    return new NlsBundleLocatorReflectionUtil();
  }

}
