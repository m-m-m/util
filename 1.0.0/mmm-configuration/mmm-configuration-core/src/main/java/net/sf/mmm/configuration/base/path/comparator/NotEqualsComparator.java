/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.comparator;

import java.util.regex.Pattern;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.path.comparator.Comparator} interface
 * for the equals function.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NotEqualsComparator implements Comparator {

  /** the comparator symbol */
  public static final String SYMBOL = "!=";

  /**
   * The constructor.
   */
  public NotEqualsComparator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(GenericValue value, String string, Pattern pattern) {

    String valueString = value.getString(null);
    if (pattern == null) {
      return !string.equals(valueString);
    } else {
      return !pattern.matcher(string).matches();
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getSymbol() {

    return SYMBOL;
  }

}
