/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.test.TestStrings;
import net.sf.mmm.util.text.api.StringHasher;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SimpleStringHasher}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class SimpleStringHasherTest extends AbstractStringHasherTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected StringHasher getStringHasher() {

    return new SimpleStringHasher();
  }

  /**
   * This method tests that {@link SimpleStringHasher} is compatible to {@link String#hashCode()}.
   */
  @Test
  public void testCompatibleHashCode() {

    StringHasher hasher = getStringHasher();
    String s;
    s = "";
    Assert.assertEquals(s.hashCode(), hasher.getHashCode(s));
    s = "Hello World";
    Assert.assertEquals(s.hashCode(), hasher.getHashCode(s));
    s = TestStrings.THAI_SENTENCE;
    Assert.assertEquals(s.hashCode(), hasher.getHashCode(s));
  }

}
