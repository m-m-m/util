/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringWritable;

/**
 * This is an abstract base implementation of {@link StringWritable} that assumes that {@link #toString()} and
 * {@link #write(Appendable)} shall be implemented together consistently. For flexibilty we did not declare
 * {@link #toString()} and {@link #write(Appendable)} as final. However, overriding one of these methods is discouraged.
 *
 * @author hohwille
 * @since 7.4.0
 */
public abstract class AbstractStringWritable implements StringWritable {

  /**
   * The constructor.
   */
  public AbstractStringWritable() {
    super();
  }

  @Override
  public void write(Appendable appendable) throws RuntimeIoException {

    try {
      doWrite(appendable, false);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * Called from {@link #write(Appendable)} or {@link #toString()} to write the serialized data of this object.
   *
   * @param appendable the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   * @param fromToString - {@code true} if called from {@link #toString()}, {@code false} otherwise. Can be ignored if
   *        not relevant.
   * @throws IOException if an error occurred whilst writing the data.
   */
  protected abstract void doWrite(Appendable appendable, boolean fromToString) throws IOException;

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    try {
      doWrite(buffer, true);
    } catch (IOException e) {
      buffer.append(e.getMessage());
    }
    return buffer.toString();
  }

}
