/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim.contact;

import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.address.DataAddress;
import net.sf.mmm.data.api.entity.pim.contact.DataContactInfo;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.util.datatype.api.phone.PhoneNumber;

/**
 * This is the implementation of {@link DataContactInfo} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue("" + DataContactInfo.CLASS_ID)
@DataClassAnnotation(id = DataContactInfo.CLASS_ID)
public class DataContactInfoImpl extends AbstractDataEntity implements DataContactInfo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1020311865402787000L;

  /** @see #getPhoneNumber() */
  private PhoneNumber phoneNumber;

  /** @see #getPhoneNumberFax() */
  private PhoneNumber phoneNumberFax;

  /** @see #getPhoneNumberMobile() */
  private PhoneNumber phoneNumberMobile;

  /** @see #getEmail() */
  private InternetAddress email;

  /** @see #getHomepage() */
  private String homepage;

  /** @see #getAddress() */
  private DataAddressImpl address;

  /**
   * The constructor.
   */
  public DataContactInfoImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhoneNumber getPhoneNumber() {

    return this.phoneNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhoneNumber getPhoneNumberFax() {

    return this.phoneNumberFax;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PhoneNumber getPhoneNumberMobile() {

    return this.phoneNumberMobile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InternetAddress getEmail() {

    return this.email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHomepage() {

    return this.homepage;
  }

  /**
   * {@inheritDoc} <br/>
   * <b>ATTENTION:</b><br/>
   * This is a {@link OneToOne} relation. If you want to share a {@link DataAddress} between multiple
   * {@link DataContactInfo}s, you need to create one as primary entity and the others as
   * {@link #getProxyTarget() proxy} pointing to the primary.
   */
  @Override
  @OneToOne(cascade = CascadeType.ALL, optional = true)
  public DataAddressImpl getAddress() {

    return this.address;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAddress(DataAddress address) {

    this.address = (DataAddressImpl) address;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPhoneNumber(PhoneNumber phoneNumber) {

    this.phoneNumber = phoneNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPhoneNumberFax(PhoneNumber phoneNumberFax) {

    this.phoneNumberFax = phoneNumberFax;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPhoneNumberMobile(PhoneNumber phoneNumberMobile) {

    this.phoneNumberMobile = phoneNumberMobile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEmail(InternetAddress email) {

    this.email = email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHomepage(String homepage) {

    this.homepage = homepage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataContactInfo.CLASS_ID;
  }

}
