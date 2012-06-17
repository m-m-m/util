/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.UnicodeUtil;

import org.junit.Test;

/**
 * This is the test-case for {@link UnicodeUtil}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class UnicodeUtilTest {

  /**
   * This method gets the {@link UnicodeUtil} instance to test.
   * 
   * @return the {@link UnicodeUtil}.
   */
  protected UnicodeUtil getUnicodeUtil() {

    return UnicodeUtilImpl.getInstance();
  }

  /**
   * This method "tests" different minus-signs.
   */
  @Test
  public void testMinus() {

    System.out.println(UnicodeUtil.MINUS_SIGN);
    System.out.println(UnicodeUtil.SOFT_HYPHEN);
    System.out.println(UnicodeUtil.NO_BREAK_SPACE);
  }

}
