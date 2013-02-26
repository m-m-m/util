/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.address.DataCountry;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.util.datatype.api.address.Iso2CountryCode;
import net.sf.mmm.util.datatype.api.phone.InternationalCallPrefix;
import net.sf.mmm.util.datatype.api.phone.PhoneCountryCode;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of {@link DataCountry} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
public class DataCountryImpl extends AbstractDataEntity implements DataCountry {

  /** UID for serialization. */
  private static final long serialVersionUID = 4741569602613392952L;

  /** @see #getCallPrefix() */
  private InternationalCallPrefix callPrefix;

  /** @see #getCountryCode() */
  private PhoneCountryCode countryCode;

  /** @see #getIsoCode() */
  private Iso2CountryCode isoCode;

  /** @see #isSelectable() */
  private boolean selectable;

  /**
   * The constructor.
   */
  public DataCountryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InternationalCallPrefix getCallPrefix() {

    return this.callPrefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Type(type = "")
  @Column(unique = true)
  public PhoneCountryCode getCountryCode() {

    return this.countryCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iso2CountryCode getIsoCode() {

    return this.isoCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelectable() {

    return this.selectable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectable(boolean selectable) {

    this.selectable = selectable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCallPrefix(InternationalCallPrefix callPrefix) {

    this.callPrefix = callPrefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCountryCode(PhoneCountryCode countryCode) {

    this.countryCode = countryCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIsoCode(Iso2CountryCode isoCode) {

    this.isoCode = isoCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataCountry.CLASS_ID;
  }

}
