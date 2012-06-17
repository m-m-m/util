/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * A {@link ValueValidator} allows to {@link #validate(Object) validate} according values.<br/>
 * There can be arbitrary implementations of this interface. A regular implementation shall be stateless and
 * therefore thread-safe. All parameterization shall therefore happen on initialization - ideally at
 * construction.<br/>
 * <b>NOTE:</b><br/>
 * This API intentionally does NOT make use of {@link Throwable exceptions} as they are expensive to produce
 * and shall only occur in exceptional situations, while a validation failure is a regular use-case. Further,
 * a validation shall validate entire objects to the end collecting all {@link ValidationFailure failures} so
 * the end-user can see and fix all problems at once.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface ValueValidator<V> {

  /**
   * This method validates the given <code>value</code>.
   * 
   * @param value is the value to validate.
   * @return the {@link ValidationFailure} or <code>null</code> if the given <code>value</code> is valid
   *         according to this {@link ValueValidator}.
   */
  ValidationFailure validate(V value);

  /**
   * This method validates the given <code>value</code>.
   * 
   * @param value is the value to validate.
   * @param valueSource is the {@link ValidationFailure#getSource() source} describing the origin of the given
   *        <code>value</code>. This may be the filename where the value was read from, an XPath where the
   *        value was located in an XML document, the label of a widget of the UI containing the value, etc.
   *        This will help to find the problem easier. The source needs to have a reasonable
   *        {@link Object#toString() string-representation} as this may be displayed to the end-user to locate
   *        the source of the failure. In most cases it is suitable to directly pass a {@link String}.
   * @return the {@link ValidationFailure} or <code>null</code> if the given <code>value</code> is valid
   *         according to this {@link ValueValidator}.
   */
  ValidationFailure validate(V value, Object valueSource);

}
