/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.UnicodeUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link UnicodeUtil}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class UnicodeUtilTest extends Assert {

  /**
   * This method gets the {@link UnicodeUtil} instance to test.
   * 
   * @return the {@link UnicodeUtil}.
   */
  protected UnicodeUtil getUnicodeUtil() {

    return UnicodeUtilImpl.getInstance();
  }

  /**
   * Test for {@link UnicodeUtil#isMinus(char)}.
   */
  @Test
  public void testMinus() {

    UnicodeUtil util = getUnicodeUtil();
    assertTrue(util.isMinus(UnicodeUtil.MINUS_SIGN));
    assertTrue(util.isMinus(UnicodeUtil.HYPHEN_MINUS));
    assertFalse(util.isMinus(UnicodeUtil.SOFT_HYPHEN));
  }

  /**
   * Test for {@link UnicodeUtil#normalize2Ascii(CharSequence, char)}.
   */
  @Test
  public void testNormalizeToAscii() {

    UnicodeUtil util = getUnicodeUtil();
    assertEquals("haesslich", util.normalize2Ascii("häßlich"));
    assertEquals("voila maitre asteroide", util.normalize2Ascii("voilà maître astéroïde"));
  }

}
