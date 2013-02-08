/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValidationFailure() validation failure}
 * attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteValidationFailure extends AttributeReadValidationFailure {

  /**
   * This method sets the {@link #getValidationFailure() validation failure}.<br/>
   * <b>ATTENTION:</b><br/>
   * For regular usage you should use {@link net.sf.mmm.client.ui.api.feature.UiFeatureValidation}. This
   * method is only intended as back-door for very special cases (e.g. you have to use your own validator
   * infrastructure and perform validation outside this framework). It is not recommended to use this method
   * as an API user.
   * 
   * @param validationFailure is the validation failure text. The empty string or <code>null</code> will clear
   *        the error and mark the field as valid. Otherwise the field will be invalid.
   */
  void setValidationFailure(String validationFailure);

}
