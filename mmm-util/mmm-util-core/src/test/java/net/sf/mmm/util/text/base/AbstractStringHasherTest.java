/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.test.TestValues;
import net.sf.mmm.util.text.api.StringHasher;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the abstract base class for test-cases of {@link StringHasher} implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractStringHasherTest {

  /**
   * This method gets the {@link StringHasher} to test.
   * 
   * @return the {@link StringHasher} to test.
   */
  protected abstract StringHasher getStringHasher();

  /**
   * This method performs arbitrary checks to verify consistency of the various methods of a
   * {@link StringHasher}.
   * 
   * @param hasher is the {@link StringHasher} to test.
   * @param string is a reasonable test-string.
   */
  protected void check(StringHasher hasher, String string) {

    int fullHash = hasher.getHashCode(string);
    Assert.assertEquals(fullHash, hasher.getHashCode(string, 0, string.length()));
    Assert.assertEquals(fullHash, hasher.getHashCode(string.toCharArray(), 0, string.length()));
    StringBuilder stringBuilder = new StringBuilder(string);
    Assert.assertEquals(fullHash, hasher.getHashCode(stringBuilder));
    Assert.assertEquals(fullHash, hasher.getHashCode(stringBuilder, 0, string.length()));
    if (string.length() > 3) {
      int start = 1;
      int end = string.length() - 1;
      String substring = string.substring(start, end);
      int hash = hasher.getHashCode(substring);
      Assert.assertEquals(hash, hasher.getHashCode(string, start, end));
      Assert.assertEquals(hash, hasher.getHashCode(string.toCharArray(), start, end));
      int length = 3;
      int[] hashes = hasher.getHashCodes(string.toCharArray(), length, start, end);
      Assert.assertEquals(end - start - length + 1, hashes.length);
      Assert.assertArrayEquals(hashes, hasher.getHashCodes(string.substring(start, end), length));
    }
    for (int length = 1; length <= string.length(); length++) {
      int[] hashes = hasher.getHashCodes(string, length);
      Assert.assertEquals(string.length() - length + 1, hashes.length);
      for (int start = 0; start < hashes.length; start++) {
        Assert.assertEquals("Test ('" + string + "', length=" + length + ", start=" + start,
            hasher.getHashCode(string, start, start + length), hashes[start]);
      }
      Assert.assertArrayEquals(hashes, hasher.getHashCodes(string, length, 0, string.length()));
      Assert.assertArrayEquals(hashes, hasher.getHashCodes(string.toCharArray(), length));
      Assert.assertArrayEquals(hashes, hasher.getHashCodes(string.toCharArray(), length, 0, string.length()));
    }
  }

  /**
   * This method performs general {@link #check(StringHasher, String) checks} to verify the
   * {@link #getStringHasher() hasher}.
   */
  @Test
  public void testHasher() {

    StringHasher hasher = getStringHasher();
    check(hasher, "hello world!");
    check(hasher, "");
    check(hasher, TestValues.THAI_SENTENCE);
  }

}
