/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.lang.api.BasicUtil;

/**
 * This is a fast implementation of {@link net.sf.mmm.util.text.api.StringHasher}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FastStringHasher extends AbstractStringHasher {

  /** The hash-factor - should be prime (however Bernstein uses 33). */
  private static final int HASH_FACTOR = 31;

  /**
   * The constructor.
   */
  public FastStringHasher() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int getHashCode(char[] string, int start, int end) {

    int hash = 0;
    for (int i = start; i < end; i++) {
      hash = hash * HASH_FACTOR + string[i];
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHashCode(CharSequence string, int start, int end) {

    // may not work according to CharSequence implementation
    // return string.subSequence(start, end).hashCode();
    int hash = 0;
    for (int i = start; i < end; i++) {
      hash = hash * HASH_FACTOR + string.charAt(i);
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] getHashCodes(char[] string, int length, int stringStart, int stringEnd) {

    int wordLength = stringEnd - stringStart;
    if (wordLength <= 0) {
      return BasicUtil.EMPTY_INT_ARRAY;
    }
    int[] hashCodes;
    int hashCodesSize = wordLength - length + 1;
    if (hashCodesSize > 0) {
      hashCodes = new int[hashCodesSize];
      int hash = 0;
      int powFactor = 0;
      int stop = length + stringStart;
      for (int stringIndex = stringStart; stringIndex < stop; stringIndex++) {
        hash = hash * HASH_FACTOR + string[stringIndex];
        if (powFactor == 0) {
          powFactor = 1;
        } else {
          powFactor = powFactor * HASH_FACTOR;
        }
      }
      hashCodes[0] = hash;
      int headIndex = stringStart;
      int tailIndex = stringStart + length;
      for (int hashCodesIndex = 1; hashCodesIndex < hashCodesSize; hashCodesIndex++) {
        hash = hash - powFactor * string[headIndex++];
        hash = hash * HASH_FACTOR + string[tailIndex++];
        hashCodes[hashCodesIndex] = hash;
      }
    } else {
      hashCodes = BasicUtil.EMPTY_INT_ARRAY;
    }
    return hashCodes;
  }

}
