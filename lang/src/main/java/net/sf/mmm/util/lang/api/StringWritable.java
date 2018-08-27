/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the abstract interface for an object that can be {@link #write(Appendable) written as string
 * representation} to an {@link Appendable}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public abstract interface StringWritable {

  /**
   * Serializes this object to the given {@link Appendable}.<br>
   *
   * @param appendable the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   * @throws RuntimeException ({@code RuntimeIoException}) if an error occurred whilst writing the data.
   */
  void write(Appendable appendable);

}
