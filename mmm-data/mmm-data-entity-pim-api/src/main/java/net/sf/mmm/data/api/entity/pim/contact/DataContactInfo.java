/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import javax.mail.internet.InternetAddress;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.pim.address.DataAddress;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.phone.PhoneNumber;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity} that represents contact
 * informations for a {@link DataContact}.
 * 
 * @see DataContact#getContactInfos()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContactInfo.CLASS_ID, title = DataContactInfo.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataContactInfo extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_CONTACTINFO;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataContactInfo";

  /**
   * This method gets the {@link DataAddress} of this {@link DataContactInfo contact information}.
   * 
   * @return the {@link DataAddress} or <code>null</code> if undefined (unknown).
   */
  DataAddress getAddress();

  /**
   * This method sets the {@link #getAddress() address}.
   * 
   * @param address is the {@link DataAddress} to set.
   */
  void setAddress(DataAddress address);

  /**
   * This method gets the fixed line {@link PhoneNumber} of this {@link DataContactInfo contact information}.
   * 
   * @return the {@link PhoneNumber} or <code>null</code> if undefined (unknown).
   */
  PhoneNumber getPhoneNumber();

  /**
   * This method sets the {@link #getPhoneNumber() phone number}.
   * 
   * @param phoneNumber is the {@link PhoneNumber} to set.
   */
  void setPhoneNumber(PhoneNumber phoneNumber);

  /**
   * This method gets the telefax {@link PhoneNumber} of this {@link DataContactInfo contact information}.
   * 
   * @return the telefax {@link PhoneNumber} or <code>null</code> if undefined (unknown).
   */
  PhoneNumber getPhoneNumberFax();

  /**
   * This method sets the {@link #getPhoneNumberFax() telefax phone number}.
   * 
   * @param phoneNumberFax is the telefax {@link PhoneNumber} to set.
   */
  void setPhoneNumberFax(PhoneNumber phoneNumberFax);

  /**
   * This method gets the mobile {@link PhoneNumber} of this {@link DataContactInfo contact information}.
   * 
   * @return the mobile {@link PhoneNumber} or <code>null</code> if undefined (unknown).
   */
  PhoneNumber getPhoneNumberMobile();

  /**
   * This method sets the {@link #getPhoneNumberMobile() mobile phone number}.
   * 
   * @param phoneNumberMobile is the mobile {@link PhoneNumber} to set.
   */
  void setPhoneNumberMobile(PhoneNumber phoneNumberMobile);

  /**
   * This method gets the primary email address of this {@link DataContactInfo contact information}.
   * 
   * @return the primary email address.
   */
  InternetAddress getEmail();

  /**
   * This method sets the {@link #getEmail() email}.
   * 
   * @param email is the email address to set.
   */
  void setEmail(InternetAddress email);

  /**
   * This method gets the home page of this {@link DataContactInfo contact information}. E.g.
   * "http://peter.pan.net".
   * 
   * @return the home page.
   */
  String getHomepage();

  /**
   * This method sets the {@link #getHomepage() home page}.
   * 
   * @param homepage is the home page to set.
   */
  void setHomepage(String homepage);

}
