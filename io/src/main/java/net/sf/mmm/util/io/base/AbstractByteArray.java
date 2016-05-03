/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.io.api.ByteArray;

/**
 * This is the abstract base implementation of the {@link ByteArray} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractByteArray implements ByteArray {

  /**
   * This method checks that the given {@code minimum} and {@code maximum} are valid for
   * {@link #createSubArray(int, int)}.
   * 
   * @param minimum is the new {@link #getMinimumIndex() minimumIndex}.
   * @param maximum is the new {@link #getMaximumIndex() maximumIndex}.
   */
  protected void checkSubArray(int minimum, int maximum) {

    if (minimum < getMinimumIndex()) {
      throw new NlsIllegalArgumentException("minimum");
    }
    if (maximum > getMaximumIndex()) {
      throw new NlsIllegalArgumentException("maximum");
    }
  }

  @Override
  public ByteArray createSubArray(int minimum, int maximum) {

    checkSubArray(minimum, maximum);
    return new ByteArrayImpl(getBytes(), minimum, maximum);
  }

  @Override
  public int getBytesAvailable() {

    return getMaximumIndex() - getCurrentIndex() + 1;
  }

}
