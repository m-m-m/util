/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This (functional) interface allows to implement an external function to check if two objects are considered
 * as {@link #isEqual(Object, Object) equal} independent from the objects {@link Object#equals(Object) equals}
 * method. It allows to switch between {@link EqualsCheckerIsEqual equals}, {@link EqualsCheckerIsSame same}
 * or other implementations. Additionally it is <code>null</code>-aware. <br>
 * <b>ATTENTION:</b><br>
 * For specific custom implementations also a corresponding {@link HashCodeFunction} has to be provided. Due
 * to lambda support we keep these aspects in separate functional interfaces. Containers that accept an
 * {@link EqualsChecker} should also accept a {@link HashCodeFunction} for customization. Unfortunately
 * {@link java.util.Collection}s do not allow such customization so you need to wrap objects before using them
 * as
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <VALUE> is the generic type of the values to {@link #isEqual(Object, Object) check}.
 */
public interface EqualsChecker<VALUE> {

  /**
   * This method determines if the two given objects are {@link Object#equals(Object) equal} to each other in
   * a way semantically defined by this implementation. An implementation must fulfill the contract of the
   * method {@link Object#equals(Object)}. This means it is:
   * <ul>
   * <li><em>reflexive</em>: <code>isEquals(x, x) == true</code>, especially:
   * <code>isEquals(null, null) == true</code></li>
   * <li><em>symmetric</em>: <code>isEquals(x, y) == isEquals(y, x)</code></li>
   * <li><em>transitive</em>: <code>(isEquals(x, y) == isEquals(y, z) == true)</code> implies
   * <code>isEquals(x, z)</code></li>
   * <li><em>consistent</em>: multiple invocations of <code>isEquals(x, y)</code> will always return the same
   * result if <code>x</code> and <code>y</code> are unchanged.</li>
   * <li><em>null-compatible</em>: for all x != null: isEquals(x, null) == isEquals(null, x) == false</li>
   * </ul>
   * 
   * @param value1 is the first value.
   * @param value2 is the second value.
   * @return <code>true</code> if both values are <code>null</code> or both are NOT <code>null</code> and
   *         equal to each other in the way defined by this {@link EqualsChecker} implementation,
   *         <code>false</code> otherwise.
   */
  boolean isEqual(VALUE value1, VALUE value2);

}
