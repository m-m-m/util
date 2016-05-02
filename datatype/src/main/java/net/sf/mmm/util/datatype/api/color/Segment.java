/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMinimumValue;

/**
 * This is the interface for a single segment of a {@link Color}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Segment<V extends Number> extends SimpleDatatype<V>, AttributeReadMinimumValue<V>,
    AttributeReadMaximumValue<V> {

  /**
   * @return the value in the range from {@code 0} to {@code 1} ({@code [0, 1]}).
   */
  double getValueAsFactor();

  /**
   * @return the value in percent in the range from {@code 0} to {@code 100}. Will be rounded to an
   *         {@code int}. If you want to keep (potential) precision, use {@link #getValueAsFactor()} and
   *         divide by {@code 100}.
   */
  int getValueAsPercent();

  /**
   * @return the {@link #getValueAsFactor() value} as {@link String}. E.g. "0.512".
   */
  String toStringAsFactor();

  /**
   * @return the {@link #getValueAsFactor() value} as {@link String} formatted in percent. E.g. "51.2%".
   * @see #getValueAsPercent()
   */
  String toStringAsPercent();

  /**
   * {@inheritDoc}
   * 
   * @return the default {@link String} representation. Depending on the type this might be the same as
   *         {@link #toStringAsFactor()} but it might also be an absolute integer (e.g. in the range
   *         {@code [0, 255]} for RGBA segments).
   */
  String toString();
}
