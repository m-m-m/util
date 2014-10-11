/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.math.BigDecimal;
import java.util.Currency;

import javax.inject.Named;

import net.sf.mmm.util.lang.base.datatype.descriptor.AbstractDatatypeDescriptor;
import net.sf.mmm.util.lang.base.datatype.descriptor.AbstractDatatypeSegmentDescriptor;

/**
 * This is the {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} implementation of
 * {@link SampleComposedDatatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@Named
public class SampleComposedDatatypeDescriptor extends AbstractDatatypeDescriptor<SampleComposedDatatype> {

  /**
   * The constructor.
   */
  public SampleComposedDatatypeDescriptor() {

    super(SampleComposedDatatype.class, new AmountSegment(), new CurrencySegment());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SampleComposedDatatype doCreate(Object... segments) {

    BigDecimal amount = (BigDecimal) segments[0];
    Currency currency = (Currency) segments[1];
    return new SampleComposedDatatype(amount, currency);
  }

  /** Implementation for {@link SampleComposedDatatype#getAmount()}. */
  private static class AmountSegment extends AbstractDatatypeSegmentDescriptor<SampleComposedDatatype, BigDecimal> {

    /**
     * The constructor.
     */
    public AmountSegment() {

      super("amount", BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BigDecimal doGetSegment(SampleComposedDatatype datatype) {

      return datatype.getAmount();
    }

  }

  /** Implementation for {@link SampleComposedDatatype#getAmount()}. */
  private static class CurrencySegment extends AbstractDatatypeSegmentDescriptor<SampleComposedDatatype, Currency> {

    /**
     * The constructor.
     */
    public CurrencySegment() {

      super("currency", Currency.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Currency doGetSegment(SampleComposedDatatype datatype) {

      return datatype.getCurrency();
    }

  }

}
