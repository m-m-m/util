/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the interface for an object, that {@link #getValue() has a value} as well as
 * {@link #addValidator(ValueValidator) validators} and can be
 * {@link #validate(net.sf.mmm.util.validation.base.ValidationState) validated}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value} to validate.
 * 
 * @author hohwille
 * @since 3.0.0
 */
public interface ValidatableObject<V> extends HasValueValidators<V>, AttributeReadValue<V>, AbstractValidatableObject {

  // nothing to add...

}
