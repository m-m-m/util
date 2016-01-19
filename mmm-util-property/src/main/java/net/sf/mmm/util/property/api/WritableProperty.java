/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.property.Property;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the interface for a generic property.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableProperty<VALUE>
    extends Property<VALUE>, AttributeWriteValue<VALUE>, ReadableProperty<VALUE> {

  /** Empty array instance. */
  WritableProperty<?>[] NO_PROPERTIES = new WritableProperty<?>[0];

  /**
   * @return <code>true</code> if this property is read-only and {@link #setValue(Object)} will fail with an exception,
   *         <code>false</code> otherwise.
   */
  boolean isReadOnly();

  /**
   * @return the {@link #isReadOnly() read only} view on this property.
   */
  WritableProperty<VALUE> getReadOnly();

  /**
   * @return <code>true</code> if this property is mandatory (a {@link #getValue() value} of <code>null</code> is NOT
   *         {@link #isValid() valid}.
   */
  boolean isMandatory();

  /**
   * @see ValueValidator#validate(Object)
   *
   * @return the {@link ValidationFailure} or <code>null</code> if the {@link #getValue() value} is {@link #isValid()
   *         valid}.
   */
  ValidationFailure validate();

  /**
   * @see #validate()
   *
   * @return <code>true</code> if valid, <code>false</code> otherwise.
   */
  boolean isValid();

}
