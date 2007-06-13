/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.base;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.Function;
import net.sf.mmm.term.api.IllegalArgumentCountException;
import net.sf.mmm.term.api.OperatorPriority;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.term.api.Function} interface. If you want to implement the
 * interface please consider extending this class even if you completely
 * override multiple methods. Just in case a method will be added to the
 * interface it can be implemented here.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractFunction implements Function {

  /**
   * The constructor.
   */
  public AbstractFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final void validateArgumentCount(int count) throws CalculationException {

    assert (getMinimumArgumentCount() >= 0);
    assert (getMinimumArgumentCount() < getMaximumArgumentCount());
    if ((count < getMinimumArgumentCount()) || (count > getMaximumArgumentCount())) {
      throw new IllegalArgumentCountException(this, count);
    }
  }

  /**
   * {@inheritDoc}
   */
  public OperatorPriority getOperatorPriority() {

    return OperatorPriority.MEDIUM;
  }

  /**
   * This method checks if a given string is a legal operator symbol. <br>
   * ATTENTION: Be aware that <code>null</code> is no valid operator symbol
   * even though it is a legal result of the method
   * {@link Function#getOperatorSymbol()}.
   * 
   * @see AbstractFunction#checkSymbolChar(char)
   * 
   * @param symbol is the operator symbol to check.
   * @return <code>true</code> iff the operator symbol is legal.
   */
  public static boolean checkSymbol(String symbol) {

    if ((symbol == null) || (symbol.length() == 0)) {
      return false;
    }
    for (int i = 0; i < symbol.length(); i++) {
      if (!checkSymbolChar(symbol.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * This method checks if a given character can be legally used in an operator
   * symbol string.
   * 
   * @see Function#LEGAL_OPERATOR_CHARS
   * 
   * @param c is the character of an operator symbol.
   * @return <code>true</code> if the given character is a legal character for
   *         an operator symbol string, <code>false</code> otherwise.
   */
  public static boolean checkSymbolChar(char c) {

    for (int i = 0; i < LEGAL_OPERATOR_CHARS.length(); i++) {
      if (c == LEGAL_OPERATOR_CHARS.charAt(i)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String symbol = getOperatorSymbol();
    if (symbol == null) {
      return getName();
    } else {
      return getName() + "[" + symbol + "]";
    }
  }

}
