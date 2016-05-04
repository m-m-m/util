/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * This are the {@link ConstantsWithLookup} for the I18N/NLS of custom {@link javax.validation.Constraint}s for GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface ValidationMessages extends ConstantsWithLookup {

  /**
   * @return failure message for {@link net.sf.mmm.util.validation.base.Mandatory}.
   */
  @Key("net.sf.mmm.util.validation.base.Mandatory.message")
  // CHECKSTYLE:OFF (GWT requirements force this method name mapping)
  String net_sf_mmm_util_validation_base_Mandatory_message();

  // /**
  // * @return failure message for {@link net.sf.mmm.util.validation.base.PhoneNumber}.
  // */
  // @Key("net.sf.mmm.util.validation.base.PhoneNumber.message")
  // String net_sf_mmm_util_validation_base_PhoneNumber_message();
  // CHECKSTYLE:ON
}
