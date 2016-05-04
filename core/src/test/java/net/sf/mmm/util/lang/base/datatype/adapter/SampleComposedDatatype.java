/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import net.sf.mmm.util.lang.api.AbstractDatatype;

/**
 * This is an example of a composed {@link net.sf.mmm.util.lang.api.Datatype} for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class SampleComposedDatatype extends AbstractDatatype {

  private static final long serialVersionUID = 1L;

  private final BigDecimal amount;

  private final Currency currency;

  /**
   * The constructor.
   *
   * @param amount the {@link #getAmount() amount}.
   * @param currency the {@link #getCurrency() currency}.
   */
  public SampleComposedDatatype(BigDecimal amount, Currency currency) {

    super();
    this.amount = amount;
    this.currency = currency;
    Objects.requireNonNull(amount, "amount");
    Objects.requireNonNull(currency, "currency");
  }

  /**
   * @return the amount of money in the {@link #getCurrency() currency}.
   */
  public BigDecimal getAmount() {

    return this.amount;
  }

  /**
   * @return the {@link Currency} the {@link #getAmount() amount} is given in.
   */
  public Currency getCurrency() {

    return this.currency;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
    result = prime * result + ((this.currency == null) ? 0 : this.currency.hashCode());
    return result;
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
    SampleComposedDatatype other = (SampleComposedDatatype) obj;
    if (!Objects.equals(this.amount, other.amount)) {
      return false;
    }
    if (!Objects.equals(this.currency, other.currency)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return this.amount + " " + this.currency;
  }

}
