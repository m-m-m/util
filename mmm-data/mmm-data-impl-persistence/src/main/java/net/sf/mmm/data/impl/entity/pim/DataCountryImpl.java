/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.address.DataCountry;
import net.sf.mmm.data.api.entity.pim.address.DataCountryView;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.util.datatype.api.address.Iso2CountryCode;
import net.sf.mmm.util.datatype.api.phone.CountryCode;
import net.sf.mmm.util.datatype.api.phone.InternationalCallPrefix;

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
  private CountryCode countryCode;

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
  public InternationalCallPrefix getCallPrefix() {

    return this.callPrefix;
  }

  /**
   * {@inheritDoc}
   */
  @Type(type = "")
  @Column(unique = true)
  public CountryCode getCountryCode() {

    return this.countryCode;
  }

  /**
   * {@inheritDoc}
   */
  public Iso2CountryCode getIsoCode() {

    return this.isoCode;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelectable() {

    return this.selectable;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectable(boolean selectable) {

    this.selectable = selectable;
  }

  /**
   * {@inheritDoc}
   */
  public void setCallPrefix(InternationalCallPrefix callPrefix) {

    this.callPrefix = callPrefix;
  }

  /**
   * {@inheritDoc}
   */
  public void setCountryCode(CountryCode countryCode) {

    this.countryCode = countryCode;
  }

  /**
   * {@inheritDoc}
   */
  public void setIsoCode(Iso2CountryCode isoCode) {

    this.isoCode = isoCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataCountryView.CLASS_ID;
  }

}
