/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * This is the abstract base implementation of the {@link StringHasher} interface.<br>
 * <b>NOTE:</b><br>
 * For efficiency you should override some of the default method-implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractStringHasher implements StringHasher {

  /**
   * The constructor.
   */
  public AbstractStringHasher() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int getHashCode(CharSequence string) {

    return getHashCode(string, 0, string.length());
  }

  /**
   * {@inheritDoc}
   */
  public int getHashCode(CharSequence string, int start, int end) {

    return getHashCode(string.toString().toCharArray(), start, end);
  }

  /**
   * {@inheritDoc}
   */
  public int[] getHashCodes(CharSequence string, int length) {

    return getHashCodes(string, length, 0, string.length());
  }

  /**
   * {@inheritDoc}
   */
  public int[] getHashCodes(char[] string, int length) {

    return getHashCodes(string, length, 0, string.length);
  }

  /**
   * {@inheritDoc}
   */
  public int[] getHashCodes(CharSequence string, int length, int stringStart, int stringEnd) {

    return getHashCodes(string.subSequence(stringStart, stringEnd).toString().toCharArray(), length);
  }

  /**
   * {@inheritDoc}
   */
  public int[] getHashCodes(char[] string, int length, int stringStart, int stringEnd) {

    int size = stringEnd - stringStart - length + 1;
    if (size <= 0) {
      return BasicUtil.EMPTY_INT_ARRAY;
    }
    int[] result = new int[size];
    for (int i = 0; i < size; i++) {
      result[i] = getHashCode(string, stringStart + i, stringStart + i + length);
    }
    return result;
  }

}
