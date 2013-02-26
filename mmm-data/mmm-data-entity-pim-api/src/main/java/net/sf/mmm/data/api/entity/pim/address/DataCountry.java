/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.DataSelectionList;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassCachingStrategy;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.address.Iso2CountryCode;
import net.sf.mmm.util.datatype.api.phone.InternationalCallPrefix;
import net.sf.mmm.util.datatype.api.phone.PhoneCountryCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the view interface for a {@link net.sf.mmm.data.api.entity.DataEntity entity} that represents a
 * country of the world.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataCountry.CLASS_ID, title = DataCountry.CLASS_TITLE, //
isFinal = BooleanEnum.TRUE, cachingStrategy = DataClassCachingStrategy.FULLY_IMUTABLE)
public interface DataCountry extends DataEntity, DataSelectionList {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_COUNTRY;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataCountry";

  /**
   * This method gets the official name of the country in English language. For internationalization see
   * {@link net.sf.mmm.util.nls.api.NlsMessage}.
   * 
   * {@inheritDoc}
   */
  @Override
  String getTitle();

  /**
   * This method gets the {@link InternationalCallPrefix} (the exit code) to dial out of the country.<br/>
   * <b>ATTENTION:</b><br/>
   * Some countries have multiple {@link InternationalCallPrefix call prefixes}. In this case the method
   * returns one of them. If there is no default (carrier select) then an arbitrary one is selected (e.g. the
   * lowest number).
   * 
   * @return the {@link InternationalCallPrefix}.
   */
  InternationalCallPrefix getCallPrefix();

  /**
   * This method sets the {@link #getCallPrefix() call prefix}.
   * 
   * @param callPrefix is the {@link InternationalCallPrefix} to set.
   */
  void setCallPrefix(InternationalCallPrefix callPrefix);

  /**
   * This method gets the phone code of this country (e.g. "49" for Germany, "1" for USA, etc.).
   * 
   * @return the {@link PhoneCountryCode}.
   */
  PhoneCountryCode getCountryCode();

  /**
   * This method sets the {@link #getCountryCode() country code}.
   * 
   * @param countryCode is the {@link PhoneCountryCode} to set.
   */
  void setCountryCode(PhoneCountryCode countryCode);

  /**
   * This method gets the 2-letter code of the country according to ISO 639.
   * 
   * @see java.util.Locale#getCountry()
   * @see java.util.Locale#getISO3Country()
   * 
   * @return the 2-letter ISO code.
   */
  Iso2CountryCode getIsoCode();

  /**
   * This method sets the {@link #getIsoCode() ISO code}.
   * 
   * @param isoCode is the ISO code to set.
   */
  void setIsoCode(Iso2CountryCode isoCode);

}
