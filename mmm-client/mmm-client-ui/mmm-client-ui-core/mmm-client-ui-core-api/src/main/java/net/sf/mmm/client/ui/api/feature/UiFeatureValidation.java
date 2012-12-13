/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.util.validation.api.ValidatableObject;

/**
 * This is the interface for the {@link UiFeature features} of an object that can be validated. It allows to
 * {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) add validators} and perform a
 * {@link #validate(net.sf.mmm.util.validation.api.ValidationState) validation} of the current
 * {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the value to validate.
 */
public interface UiFeatureValidation<VALUE> extends ValidatableObject<VALUE> {

  /**
   * This is a convenience method for adding the most common validator
   * {@link net.sf.mmm.util.validation.base.ValidatorMandatory}.
   */
  void addValidatorMandatory();

  /**
   * This method determines if {@link net.sf.mmm.util.validation.base.ValidatorMandatory} (or a sub-class) has
   * been {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) added} to this object.
   * 
   * @return <code>true</code> if this the {@link #getValue() value} of this object is mandatory.
   */
  boolean isMandatory();

}
