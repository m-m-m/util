/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

/**
 * A {@link ValueValidator} allows to {@link #validate(Object) validate}
 * according values.<br/>
 * There can be arbitrary implementations of this interface. A regular
 * implementation shall be thread-safe.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object)
 *        validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface ValueValidator<V> {

  /**
   * This method validates the given <code>value</code>. If the value is
   * invalid, a {@link RuntimeException} is thrown. Otherwise this method will
   * return without any side-effect.
   * 
   * @param value is the value to validate.
   * @throws RuntimeException if the given <code>value</code> in invalid.
   */
  void validate(V value) throws RuntimeException;

  /**
   * This method validates the given <code>value</code>. If the value is
   * invalid, a {@link RuntimeException} is thrown. Otherwise this method will
   * return without any side-effect.
   * 
   * @param value is the value to validate.
   * @param valueSource describes the origin of the given <code>value</code>.
   *        This may be the filename where the value was read from, an XPath
   *        where the value was located in an XML document, etc. It can be used
   *        in exceptions thrown if the <code>value</code> is invalid. This will
   *        help to find the problem easier. It may be <code>null</code> if
   *        there is no helpful source available but you should prefer
   *        {@link #validate(Object)} in such case.
   * @throws RuntimeException if the given <code>value</code> in invalid.
   */
  void validate(V value, Object valueSource) throws RuntimeException;

}
