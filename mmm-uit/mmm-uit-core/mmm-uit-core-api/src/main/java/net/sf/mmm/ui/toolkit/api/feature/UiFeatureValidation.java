/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the interface for the {@link UiFeature features} of an object that can be validated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the value to validate.
 */
public interface UiFeatureValidation<VALUE> {

  /**
   * This method adds the given <code>validator</code> to this UI object. All {@link ValueValidator}s are
   * {@link ValueValidator#validate(Object) invoked} in the same order as they are added by this method. They
   * are {@link ValueValidator#validate(Object) invoked} no matter if the previous {@link ValueValidator}s
   * failed or succeeded. You should always design {@link ValueValidator}s in a robust and reusable way (e.g.
   * a range validator should accept <code>null</code> as valid input so it can used for both mandatory and
   * optional fields).
   * 
   * @param validator is the {@link ValueValidator} to add.
   */
  void addValidator(ValueValidator<? super VALUE> validator);

  /**
   * This method removes the given <code>validator</code> assuming it has previously been
   * {@link #addValidator(ValueValidator) added}.
   * 
   * @param validator is the {@link ValueValidator} to remove.
   * @return <code>true</code> if the given <code>validator</code> has been removed successfully,
   *         <code>false</code> otherwise.
   */
  boolean removeValidator(ValueValidator<? super VALUE> validator);

  /**
   * This method performs a validation on this UI object. It will clear all validation failures and then
   * perform a new validation. New validation failures should immediately show up near to the place where the
   * incorrect data has been entered by the end-user.
   * 
   * @return <code>true</code> if this object has been validated successfully (no failures),
   *         <code>false</code> if at least one validation failure has occurred.
   */
  boolean validate();

}
