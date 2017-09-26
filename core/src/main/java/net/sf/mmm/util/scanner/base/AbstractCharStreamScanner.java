/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * Abstract base implementation of {@link CharStreamScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.5.0
 */
public abstract class AbstractCharStreamScanner implements CharStreamScanner {

  /**
   * The constructor.
   */
  public AbstractCharStreamScanner() {

    super();
  }

  @Override
  public double readDouble() throws NumberFormatException {

    String number = consumeDecimal();
    return Double.parseDouble(number);
  }

  @Override
  public float readFloat() throws NumberFormatException {

    String number = consumeDecimal();
    return Float.parseFloat(number);
  }

  /**
   * Consumes the characters of a decimal number (double or float).
   *
   * @return the decimal number as {@link String}.
   */
  protected abstract String consumeDecimal();

  @Override
  public int skipWhile(CharFilter filter) {

    return skipWhile(filter, Integer.MAX_VALUE);
  }

  @Override
  public char skipWhileAndPeek(CharFilter filter) {

    return skipWhileAndPeek(filter, Integer.MAX_VALUE);
  }

  @Override
  public char skipWhileAndPeek(CharFilter filter, int max) {

    skipWhile(filter, max);
    return forcePeek();
  }

  @Override
  public String readWhile(CharFilter filter) {

    return readWhile(filter, Integer.MAX_VALUE);
  }

  @Override
  public boolean skipOver(String substring, boolean ignoreCase) {

    return skipOver(substring, ignoreCase, null);
  }

}
