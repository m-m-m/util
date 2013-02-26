/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the interface for the combination of {@link UiFeatureValue} and {@link UiFeatureValidation}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureValueAndValidation<VALUE> extends UiFeatureValue<VALUE>, UiFeatureValidation<VALUE>,
    UiFeatureMessages {

  /**
   * This method is a combination of {@link #getValue()} and {@link #validate(ValidationState)}. It is
   * designed for API users implementing higher level dialog logic and is therefore easy to use. It performs a
   * {@link #validate(ValidationState) validation} and only on success it returns the {@link #getValue()
   * value}.<br/>
   * <b>ATTENTION:</b><br/>
   * The method design assumes that <code>null</code> is not a valid value. Never call this method with
   * <code>null</code> as argument if &lt;VALUE&gt; is {@link Void} or anything else that considers
   * <code>null</code> as a valid value. It should be used for high-level UI nodes such as entire input forms
   * or (sub-)dialogs.
   * 
   * @param state is the {@link ValidationState}. Shall initially be created as new instance and passed on in
   *        case of recursive validations. May also be <code>null</code> and will then be created internally
   *        (in case you do not need the validation result).
   * @return the actual {@link #getValue() value} or <code>null</code> if the
   *         {@link #validate(ValidationState) validation} failed or <code>null</code> is the actual (valid)
   *         value.
   */
  VALUE getValueAndValidate(ValidationState state);

  /**
   * This method is a combination of {@link #getValueOrException(Object)} and
   * {@link #validate(ValidationState)}. It allows more efficient implementations as determining and creating
   * the {@link #getValueOrException(Object) value} can be expensive. Saving changes requires validation AND
   * on success getting the value, while already validation requires getting the value. For composite UI
   * objects this effect would reinforce to multiply the number of calls to
   * {@link #getValueOrException(Object)}.<br/>
   * <b>NOTE:</b><br/>
   * This method is designed for implementors of composite UI objects. API users should only use
   * {@link #getValueAndValidate(ValidationState)}.
   * 
   * @param template is the object where the data is filled in. May also be <code>null</code> - then this
   *        method will create a new instance.
   * @param state is the {@link ValidationState}. If <code>null</code> the validation will be omitted,
   *        otherwise an implicit validation is performed.
   * @return the {@link #getValue() current value}. May be <code>null</code> (esp. if invalid).
   * @throws RuntimeException if <code>state</code> is <code>null</code> no validation is performed and input
   *         errors are directly thrown causing a fast fail. Otherwise if validation is performed all
   *         {@link RuntimeException} are catched and according validation failures are
   *         {@link ValidationState#onFailure(net.sf.mmm.util.validation.api.ValidationFailure) collected} and
   *         visualized by the UI.
   */
  VALUE getValueDirect(VALUE template, ValidationState state) throws RuntimeException;

}
