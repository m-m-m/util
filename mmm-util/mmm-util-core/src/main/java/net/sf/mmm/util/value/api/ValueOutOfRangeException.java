/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the exception thrown if a numeric value is not in the expected range.
 *
 * @see GenericValueConverter#convertValue(Object, Object, Number, Number)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ValueOutOfRangeException extends ValueException {

  /** UID for serialization. */
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
   */
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum) {

    this(value, minimum, maximum, null);
  }

  /**
   * The constructor.
   *
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum, Object valueSource) {

    super(createMessage(value, minimum, maximum, valueSource));
    if (value instanceof Comparable) {
      // Comparable is preferred as verifyNumber is incorrect for BigInteger or BigDecimal
      verifyComparable((Comparable) value, (Comparable) minimum, (Comparable) maximum);
    } else {
      verifyNumber(value, minimum, maximum);
    }
  }

  /**
   * The constructor.
   *
   * @param <V> is the generic type of the values.
   *
   * @param valueSource describes the source of the value or <code>null</code> if NOT available. This may be
   *        the filename where the value was read from, an XPath where the value was located in an XML
   *        document, etc. It is used in exceptions thrown if something goes wrong. This will help to find the
   *        problem easier.
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @since 3.0.0
   */
  public <V extends Comparable<V>> ValueOutOfRangeException(Object valueSource, V value, V minimum, V maximum) {

    super(createMessage(value, minimum, maximum, valueSource));
    verifyComparable(value, minimum, maximum);
  }

  /**
   * Verifies that the <code>value</code> is actually out of range.
   *
   * @param <V> is the generic type of the values.
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   */
  private <V extends Comparable<V>> void verifyComparable(V value, V minimum, V maximum) {

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
   * Verifies that the <code>value</code> is actually out of range.
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
   * This method checks that the given <code>value</code> is in the inclusive range from <code>minimum</code>
   * to <code>maximum</code>.
   *
   * @param value is the value to check.
   * @param minimum is the minimum number allowed.
   * @param maximum is the maximum number allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier. It may be
   *        <code>null</code> if there is no helpful source available.
   * @throws ValueOutOfRangeException - if the given <code>value</code> is NOT in the range from
   *         <code>minimum</code> to <code>maximum</code>.
   */
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
   * Creates a new error {@link NlsMessage} that the given <code>value</code> is not in the {@link Range} from
   * <code>minimum</code> to <code>maximum</code>.
   *
   * @param <V> is the generic type of the values. Needs to be an instance of {@link Number} or
   *        {@link Comparable}.
   * @param value is the invalid value.
   * @param minimum is the minimum value or <code>null</code> if unbounded.
   * @param maximum is the maximum value or <code>null</code> if unbounded.
   * @param valueSource describes the source of <code>value</code> or <code>null</code> if unknown.
   * @return the error {@link NlsMessage}.
   */
  public static <V> NlsMessage createMessage(V value, V minimum, V maximum, Object valueSource) {

    return createBundle(NlsBundleUtilCoreRoot.class).errorValueOutOfRange(value,
        (minimum == null) ? Range.MIN_UNBOUND : minimum, (maximum == null) ? Range.MAX_UNBOUND : maximum, valueSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
