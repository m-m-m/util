/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Objects;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the abstract implementation of a {@link ValueValidator} {@link #validate(Object) validating} that a given
 * value {@link Range#isContained(Object) is contained} in a given {@link Range}.
 *
 * @param <V> the generic type of the value to {@link #validate(Object) validate}.
 * @param <R> the generic type of the {@link Range}-bounds.
 *
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("rawtypes")
public class AbstractValidatorRange<V, R> extends AbstractValueValidator<V> {

  /** @see #getCode() */
  public static final String CODE = "Range";

  /** @see #validateNotNull(Comparable) */
  private final Range<R> range;

  /**
   * The constructor.
   *
   * @param range is the {@link Range} the value has to be {@link Range#isContained(Object) contained in}.
   */
  public AbstractValidatorRange(Range<R> range) {
    super();
    this.range = range;
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  /**
   * Converts the value to the type of the range.
   *
   * @param value is the value to convert.
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected R convertValue(V value) {

    return (R) value;
  }

  @Override
  protected NlsMessage validateNotNull(V value) {

    R convertedValue = convertValue(value);
    if (this.range.isContained(convertedValue)) {
      return null;
    } else {
      return createBundle(NlsBundleUtilCoreRoot.class).errorValueOutOfRange(convertedValue, this.range.getMin(),
          this.range.getMax(), null);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P getProperty(TypedProperty<P> property) {

    if (property == PROPERTY_MINIMUM) {
      return (P) this.range.getMin();
    } else if (property == PROPERTY_MAXIMUM) {
      return (P) this.range.getMax();
    }
    return super.getProperty(property);
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(this.range);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractValidatorRange other = (AbstractValidatorRange) obj;
    if (!Objects.equals(this.range, other.range)) {
      return false;
    }
    return true;
  }

}
