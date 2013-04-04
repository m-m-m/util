/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.AbstractDatatype;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

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
 * <td>{@link SizeUnit#PIXEL}</td>
 * </tr>
 * <tr>
 * <td>100%</td>
 * <td>100.0</td>
 * <td>{@link SizeUnit#PERCENT}</td>
 * </tr>
 * <tr>
 * <td>0.8em</td>
 * <td>0.8</td>
 * <td>{@link SizeUnit#EM}</td>
 * </tr>
 * </table>
 * 
 * @see SizeUnit#newLength(double)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Length extends AbstractDatatype<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2672855069930598174L;

  /** The empty {@link Length}. */
  public static final Length ZERO = new Length(0, SizeUnit.PIXEL);

  /** The full {@link Length} as "100%". */
  public static final Length FULL = new Length(100, SizeUnit.PERCENT);

  /** @see #getAmount() */
  private double amount;

  /** @see #getUnit() */
  private SizeUnit unit;

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
  public Length(double value, SizeUnit unit) {

    super();
    NlsNullPointerException.checkNotNull(SizeUnit.class, unit);
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
    for (SizeUnit sizeUnit : SizeUnit.values()) {
      String unitString = sizeUnit.getValue();
      if (size.endsWith(unitString)) {
        this.unit = sizeUnit;
        String amountString = size.substring(0, size.length() - unitString.length());
        this.amount = Double.parseDouble(amountString);
      }
    }
    if (this.unit == null) {
      throw new NlsIllegalArgumentException(size, "size");
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
   * This method gets the {@link SizeUnit unit} of the {@link #getAmount() value}.
   * 
   * @return the {@link SizeUnit}.
   */
  public SizeUnit getUnit() {

    return this.unit;
  }

  /**
   * Creates a new {@link Length} with {@link #getUnit() unit} {@link SizeUnit#PERCENT}.
   * 
   * @param amount is the {@link #getAmount() amount}.
   * @return the new {@link Length}.
   */
  public static Length valueOfPercent(double amount) {

    return SizeUnit.PERCENT.newLength(amount);
  }

  /**
   * Creates a new {@link Length} with {@link #getUnit() unit} {@link SizeUnit#PERCENT}.
   * 
   * @param amount is the {@link #getAmount() amount}.
   * @return the new {@link Length}.
   */
  public static Length valueOfPixel(double amount) {

    return SizeUnit.PIXEL.newLength(amount);
  }

}
