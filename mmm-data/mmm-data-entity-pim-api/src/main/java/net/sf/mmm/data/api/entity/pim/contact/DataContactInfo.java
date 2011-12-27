/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import javax.mail.internet.InternetAddress;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.pim.address.DataAddress;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.datatype.api.phone.PhoneNumber;

/**
 * This is the interface for a mutable {@link DataContactInfoView contact info}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContactInfoView.CLASS_ID, title = DataContactInfoView.CLASS_TITLE)
public interface DataContactInfo extends DataContactInfoView, DataEntity {

  /**
   * {@inheritDoc}
   */
  DataAddress getAddress();

  /**
   * This method sets the {@link #getAddress() address}.
   * 
   * @param address is the {@link DataAddress} to set.
   */
  void setAddress(DataAddress address);

  /**
   * This method sets the {@link #getPhoneNumber() phone number}.
   * 
   * @param phoneNumber is the {@link PhoneNumber} to set.
   */
  void setPhoneNumber(PhoneNumber phoneNumber);

  /**
   * This method sets the {@link #getPhoneNumberFax() telefax phone number}.
   * 
   * @param phoneNumberFax is the telefax {@link PhoneNumber} to set.
   */
  void setPhoneNumberFax(PhoneNumber phoneNumberFax);

  /**
   * This method sets the {@link #getPhoneNumberMobile() mobile phone number}.
   * 
   * @param phoneNumberMobile is the mobile {@link PhoneNumber} to set.
   */
  void setPhoneNumberMobile(PhoneNumber phoneNumberMobile);

  /**
   * This method sets the {@link #getEmail() email}.
   * 
   * @param email is the email address to set.
   */
  void setEmail(InternetAddress email);

  /**
   * This method sets the {@link #getHomepage() home page}.
   * 
   * @param homepage is the home page to set.
   */
  void setHomepage(String homepage);

}
