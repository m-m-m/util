/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import java.io.IOException;

/**
 * This is the interface for a specific justification. See {@link JustificationBuilder} for details.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public interface Justification {

  /**
   * This method applies the justification to the given {@code value} and
   * {@link Appendable#append(CharSequence) appends} the result to the given {@code target}.
   * 
   * @param value is the string to justify.
   * @param target is where to {@link Appendable#append(CharSequence) append} the justified data.
   * @throws IOException if caused by {@link Appendable#append(CharSequence)}.
   */
  void justify(CharSequence value, Appendable target) throws IOException;

  /**
   * This method applies the justification to the given {@code value} and returns the result.
   * 
   * @param value is the string to justify.
   * @return the justified string.
   */
  String justify(CharSequence value);

}
