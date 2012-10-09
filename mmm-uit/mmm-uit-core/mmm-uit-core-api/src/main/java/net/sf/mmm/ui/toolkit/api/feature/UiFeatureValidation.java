/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.util.validation.api.ValidatableObject;

/**
 * This is the interface for the {@link UiFeature features} of an object that can be validated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the value to validate.
 */
public interface UiFeatureValidation<VALUE> extends ValidatableObject<VALUE> {

  // nothing to add...

}
