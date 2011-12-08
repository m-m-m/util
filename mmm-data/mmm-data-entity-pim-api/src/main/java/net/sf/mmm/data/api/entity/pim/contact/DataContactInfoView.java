/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import javax.mail.internet.InternetAddress;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.phone.PhoneNumber;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity}
 * that represents contact informations for a {@link DataContactView}.
 * 
 * @see DataContactView#getContactInfos()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContactInfoView.CLASS_ID, title = DataContactInfoView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataContactInfoView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_CONTACTINFO;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataContactInfo";

  /**
   * This method gets the {@link DataAddressView} of this
   * {@link DataContactInfoView contact information}.
   * 
   * @return the {@link DataAddressView} or <code>null</code> if undefined
   *         (unknown).
   */
  DataAddressView getAddress();

  /**
   * This method gets the fixed line {@link PhoneNumber} of this
   * {@link DataContactInfoView contact information}.
   * 
   * @return the {@link PhoneNumber} or <code>null</code> if undefined
   *         (unknown).
   */
  PhoneNumber getPhoneNumber();

  /**
   * This method gets the telefax {@link PhoneNumber} of this
   * {@link DataContactInfoView contact information}.
   * 
   * @return the telefax {@link PhoneNumber} or <code>null</code> if undefined
   *         (unknown).
   */
  PhoneNumber getPhoneNumberFax();

  /**
   * This method gets the mobile {@link PhoneNumber} of this
   * {@link DataContactInfoView contact information}.
   * 
   * @return the mobile {@link PhoneNumber} or <code>null</code> if undefined
   *         (unknown).
   */
  PhoneNumber getPhoneNumberMobile();

  /**
   * This method gets the primary email address of this
   * {@link DataContactInfoView contact information}.
   * 
   * @return the primary email address.
   */
  InternetAddress getEmail();

  /**
   * This method gets the home page of this {@link DataContactInfoView contact
   * information}. E.g. "http://peter.pan.net".
   * 
   * @return the home page.
   */
  String getHomepage();

}
