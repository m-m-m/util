/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatypeBase;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a {@link Length}. It typically represents a width
 * or height of a UI-object. Therefore it consists of two parts:
 * <ul>
 * <li>an {@link #getAmount() amount}</li>
 * <li>a {@link #getUnit() unit}</li>
 * </ul>
 *
 * Examples:
 * <table border="1">
 * <tr>
 * <th>{@link #getValue()}</th>
 * <th>{@link #getAmount()}</th>
 * <th>{@link #getUnit()}</th>
 * </tr>
 * <tr>
 * <td>12px</td>
 * <td>12.0</td>
 * <td>{@link LengthUnit#PIXEL}</td>
 * </tr>
 * <tr>
 * <td>100%</td>
 * <td>100.0</td>
 * <td>{@link LengthUnit#PERCENT}</td>
 * </tr>
 * <tr>
 * <td>0.8em</td>
 * <td>0.8</td>
 * <td>{@link LengthUnit#EM}</td>
 * </tr>
 * </table>
 *
 * @see LengthUnit#newLength(double)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Length extends AbstractSimpleDatatypeBase<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2672855069930598174L;

  /** The empty {@link Length}. */
  public static final Length ZERO = new Length(0, LengthUnit.PIXEL);

  /** The maximum {@link Length}. */
  public static final Length MAX = new Length(Double.MAX_VALUE, LengthUnit.PIXEL);

  /** The full {@link Length} as "100%". */
  public static final Length FULL = new Length(100, LengthUnit.PERCENT);

  /** @see #getAmount() */
  private double amount;

  /** @see #getUnit() */
  private LengthUnit unit;

  /**
   * The constructor.
   */
  protected Length() {

    super();
  }

  /**
   * The constructor.
   *
   * @param value is the {@link #getAmount() value}.
   * @param unit is the {@link #getUnit() unit}.
   */
  public Length(double value, LengthUnit unit) {

    super();
    NlsNullPointerException.checkNotNull(LengthUnit.class, unit);
    this.unit = unit;
    this.amount = value;
  }

  /**
   * The constructor.
   *
   * @param size is the {@link #getValue() value} representing the size as string.
   */
  public Length(String size) {

    super();
    NlsNullPointerException.checkNotNull("size", size);
    for (LengthUnit sizeUnit : LengthUnit.values()) {
      String unitString = sizeUnit.getValue();
      if (size.endsWith(unitString)) {
        this.unit = sizeUnit;
        String amountString = size.substring(0, size.length() - unitString.length());
        this.amount = Double.parseDouble(amountString);
      }
    }
    if (this.unit == null) {
      if ("auto".equals(size)) {
        this.unit = LengthUnit.PIXEL;
        this.amount = 0;
      } else {
        throw new NlsIllegalArgumentException(size, "size");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    if (this.unit == null) {
      // whyever...
      return Double.toString(this.amount);
    }
    return Double.toString(this.amount) + this.unit.getValue();
  }

  /**
   * This method gets the value (amount) of this size. Is to be interpreted in the according
   * {@link #getUnit() unit}.
   *
   * @return the actual value of this {@link Length}.
   */
  public double getAmount() {

    return this.amount;
  }

  /**
   * This method gets the {@link LengthUnit unit} of the {@link #getAmount() value}.
   *
   * @return the {@link LengthUnit}.
   */
  public LengthUnit getUnit() {

    return this.unit;
  }

  /**
   * This method gets the {@link #getAmount() amount} in {@link LengthUnit#PIXEL pixels}.<br/>
   * <b>ATTENTION:</b><br/>
   * This implementation is NOT precise and should only used when you know what you are doing.
   *
   * @param basePixelLength is the base length in pixel used to convert from {@link LengthUnit#PERCENT
   *        percent}. Use <code>0</code> if unknown (what will result in <code>0</code> in case of
   *        {@link LengthUnit#PERCENT percent} length values).
   * @param property is the {@link LengthProperty} or <code>null</code> if not available.
   * @return the <code>length</code> in pixels.
   */
  public double convertToPixel(double basePixelLength, LengthProperty property) {

    switch (this.unit) {
      case PIXEL:
        return this.amount;
      case EM:
        return this.amount * 16.0;
      case PERCENT:
        if (basePixelLength <= 0) {
          if ((property != null) && (this.amount > 0)) {
            // 1% or more of unbounded min/max is still unbounded...
            return property.getDefaultValue().getAmount();
          }
          // 0% of anything is just nothing...
          return 0;
        }
        return this.amount * basePixelLength / 100D;
      default :
        throw new IllegalCaseException(LengthUnit.class, this.unit);
    }
  }

  /**
   * Creates a new {@link Length} with {@link #getUnit() unit} {@link LengthUnit#PERCENT}.
   *
   * @param amount is the {@link #getAmount() amount}.
   * @return the new {@link Length}.
   */
  public static Length valueOfPercent(double amount) {

    return LengthUnit.PERCENT.newLength(amount);
  }

  /**
   * Creates a new {@link Length} with {@link #getUnit() unit} {@link LengthUnit#PERCENT}.
   *
   * @param amount is the {@link #getAmount() amount}.
   * @return the new {@link Length}.
   */
  public static Length valueOfPixel(double amount) {

    return LengthUnit.PIXEL.newLength(amount);
  }

}
