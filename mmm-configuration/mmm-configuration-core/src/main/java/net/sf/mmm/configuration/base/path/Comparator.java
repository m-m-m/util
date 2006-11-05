/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.regex.Pattern;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the interface for a comparator that determines if a given
 * {@link GenericValue value} is
 * {@link #accept(GenericValue, String, Pattern) acceptable} in comparison to
 * a given {@link java.util.regex.Pattern pattern} or (if pattern is
 * <code>null</code>) {@link java.lang.String string}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Comparator {

  /**
   * This method gets the symbol of the comparator. This must be unique for each
   * implementation.<br>
   * Examples are "==", "!=", "<", "<=", etc.
   * 
   * @return the operation symbol.
   */
  String getSymbol();

  /**
   * This method determines if the given <code>value</code> is acceptable in
   * comparison to the given <code>pattern</code> (if pattern is
   * <code>null</code>) or <code>string</code>.
   * 
   * @param value
   *        is the value to check.
   * @param string
   *        is the string the <code>value</code> will be compared to.
   * @param pattern
   *        is the pattern the <code>value</code> must match or
   *        <code>null</code> if <code>string</code> should be preferred.
   * 
   * @return <code>true</code> if the given <code>value</code> is
   *         acceptable, <code>false</code> otherwise.
   */
  boolean accept(GenericValue value, String string, Pattern pattern);

}
