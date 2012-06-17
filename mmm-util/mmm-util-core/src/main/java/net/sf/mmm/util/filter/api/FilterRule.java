/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.api;

/**
 * This is the interface for a filter rule. It {@link #accept(Object) decides} if a given string is accepted,
 * denied or ignored by this rule.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface FilterRule<V> {

  /**
   * This method checks if the given <code>string</code> is accepted, denied or ignored by this rule.
   * 
   * @param string is the string to check.
   * @return <code>true</code> if the file should be accepted, <code>false</code> if the file should NOT be
   *         accepted or <code>null</code> if this rule does NOT make a decision about the given
   *         <code>string</code>.
   */
  Boolean accept(V string);

}
