/* $Id$ */
package net.sf.mmm.configuration.base.path.comparator;

import java.util.regex.Pattern;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.path.comparator.Comparator} interface for the
 * equals function.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EqualsComparator implements Comparator {

  /** the comparator symbol */
  public static final String SYMBOL = "==";

  /**
   * The constructor.
   */
  public EqualsComparator() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.path.comparator.Comparator#accept(net.sf.mmm.value.api.GenericValue,
   *      java.lang.String, java.util.regex.Pattern) 
   */
  public boolean accept(GenericValue value, String string, Pattern pattern) {

    String valueString = value.getString(null);
    if (pattern == null) {
      return string.equals(valueString);
    } else {
      return pattern.matcher(string).matches();
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.path.comparator.Comparator#getSymbol()
   */
  public String getSymbol() {

    return SYMBOL;
  }

}
