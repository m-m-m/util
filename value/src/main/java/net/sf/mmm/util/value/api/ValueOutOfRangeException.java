/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.util.Comparator;
import java.util.Objects;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.base.ComparableComparator;
import net.sf.mmm.util.lang.base.NumberComparator;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the exception thrown if a numeric value is not in the expected range.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.ValueOutOfRangeException}
 */
@Deprecated
public class ValueOutOfRangeException extends ValueException {

  private static final long serialVersionUID = 3363522277063736719L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ValueOutOfRange";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValueOutOfRangeException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @deprecated - will be removed soon - use {@link #ValueOutOfRangeException(Object, Object, Object)}
   *             instead.
   */
  @Deprecated
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum) {

    this((Object) value, (Object) minimum, (Object) maximum, null);
  }

  /**
   * The constructor.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @param valueSource describes the source of the value.
   * @deprecated - will be removed soon - use
   *             {@link #ValueOutOfRangeException(Object, Object, Object, Object)} instead.
   */
  @Deprecated
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum, Object valueSource) {

    this((Object) value, (Object) minimum, (Object) maximum, valueSource);
  }

  /**
   * The constructor.
   *
   * @param <V> the generic type of the value that is out of range.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @since 7.1.0
   */
  public <V> ValueOutOfRangeException(V value, V minimum, V maximum) {

    this(value, minimum, maximum, null);
  }

  /**
   * The constructor.
   *
   * @param <V> the generic type of the value that is out of range.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @since 7.1.0
   */
  @SuppressWarnings("rawtypes")
  public <V> ValueOutOfRangeException(V value, V minimum, V maximum, Object valueSource) {

    super(createMessage(value, minimum, maximum, valueSource));
    if (value instanceof Comparable) {
      // Comparable is preferred as verifyNumber is incorrect for BigInteger or BigDecimal
      verifyComparable((Comparable) value, (Comparable) minimum, (Comparable) maximum);
    } else if (value instanceof Number) {
      verifyNumber((Number) value, (Number) minimum, (Number) maximum);
    }
  }

  /**
   * The constructor.
   *
   * @param <V> is the generic type of the values.
   *
   * @param valueSource describes the source of the value or {@code null} if NOT available. This may be the
   *        filename where the value was read from, an XPath where the value was located in an XML document,
   *        etc. It is used in exceptions thrown if something goes wrong. This will help to find the problem
   *        easier.
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @since 3.0.0
   * @deprecated - will be removed soon - use
   *             {@link #ValueOutOfRangeException(Object, Object, Object, Object)} instead.
   */
  @Deprecated
  @SuppressWarnings("rawtypes")
  public <V extends Comparable> ValueOutOfRangeException(Object valueSource, V value, V minimum, V maximum) {

    super(createMessage(value, minimum, maximum, valueSource));
    verifyComparable(value, minimum, maximum);
  }

  /**
   * Verifies that the {@code value} is actually out of range.
   *
   * @param <V> is the generic type of the values.
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <V extends Comparable> void verifyComparable(V value, V minimum, V maximum) {

    if (minimum != null) {
      if (maximum != null) {
        assert ((value.compareTo(minimum) < 0) || (value.compareTo(maximum) > 0));
      } else {
        assert (value.compareTo(minimum) < 0);
      }
    } else if (maximum != null) {
      assert (value.compareTo(maximum) > 0);
    } else {
      throw new NlsNullPointerException("minimum & maximum");
    }
  }

  /**
   * Verifies that the {@code value} is actually out of range.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   */
  private void verifyNumber(Number value, Number minimum, Number maximum) {

    if (minimum != null) {
      if (maximum != null) {
        assert ((value.doubleValue() >= minimum.doubleValue()) || (value.doubleValue() <= maximum.doubleValue()));
      } else {
        assert (value.doubleValue() >= minimum.doubleValue());
      }
    } else if (maximum != null) {
      assert (value.doubleValue() <= maximum.doubleValue());
    } else {
      throw new NlsNullPointerException("minimum & maximum");
    }
  }

  /**
   * This method checks that the given {@code value} is in the inclusive range from {@code minimum} to
   * {@code maximum}.
   *
   * @param <V> the generic type of the {@code value} to check.
   *
   * @param value is the value to check.
   * @param minimum is the minimum number allowed.
   * @param maximum is the maximum number allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier. It may be {@code null}
   *        if there is no helpful source available.
   * @throws ValueOutOfRangeException - if the given {@code value} is NOT in the range from {@code minimum} to
   *         {@code maximum}.
   * @since 7.1.0
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <V> void checkRange(V value, V minimum, V maximum, Object valueSource) throws ValueOutOfRangeException {

    Objects.requireNonNull(value, "value");
    Comparator comparator;
    if (value instanceof Comparable) {
      comparator = ComparableComparator.getInstance();
    } else if (value instanceof Number) {
      comparator = NumberComparator.getInstance();
    } else {
      throw new IllegalArgumentException(value.getClass().getName());
    }
    boolean valid = true;
    int delta;
    if (minimum != null) {
      delta = comparator.compare(value, minimum);
      if (delta < 0) {
        // value < min
        valid = false;
      }
    }
    if (maximum != null) {
      delta = comparator.compare(value, maximum);
      if (delta > 0) {
        // value > max
        valid = false;
      }
    }
    if (!valid) {
      throw new ValueOutOfRangeException(value, minimum, maximum, valueSource);
    }
  }

  /**
   * This method checks that the given {@code value} is in the inclusive range from {@code minimum} to
   * {@code maximum}.
   *
   * @param value is the value to check.
   * @param minimum is the minimum number allowed.
   * @param maximum is the maximum number allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier. It may be {@code null}
   *        if there is no helpful source available.
   * @throws ValueOutOfRangeException - if the given {@code value} is NOT in the range from {@code minimum} to
   *         {@code maximum}.
   * @deprecated - will be removed - use {@link #checkRange(Object, Object, Object, Object)} instead.
   */
  @Deprecated
  public static void checkRange(Number value, Number minimum, Number maximum, Object valueSource)
      throws ValueOutOfRangeException {

    double d = value.doubleValue();
    if ((d < minimum.doubleValue()) || (d > maximum.doubleValue())) {
      if (valueSource == null) {
        throw new ValueOutOfRangeException(value, minimum, maximum);
      } else {
        throw new ValueOutOfRangeException(value, minimum, maximum, valueSource);
      }
    }
  }

  /**
   * Creates a new error {@link NlsMessage} that the given {@code value} is not in the range from
   * {@code minimum} to {@code maximum}.
   *
   * @param <V> is the generic type of the values. Needs to be an instance of {@link Number} or
   *        {@link Comparable}.
   * @param value is the invalid value.
   * @param minimum is the minimum value or {@code null} if unbounded.
   * @param maximum is the maximum value or {@code null} if unbounded.
   * @param valueSource describes the source of {@code value} or {@code null} if unknown.
   * @return the error {@link NlsMessage}.
   */
  public static <V> NlsMessage createMessage(V value, V minimum, V maximum, Object valueSource) {

    return createBundle(NlsBundleUtilExceptionRoot.class).errorValueOutOfRange(value,
        (minimum == null) ? "\u2212\u221E" : minimum, (maximum == null) ? "+\u221E" : maximum, valueSource);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
