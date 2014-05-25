/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.gwt;

import javax.validation.Validator;
import javax.validation.groups.Default;

import net.sf.mmm.showcase.client.dialog.editor.ContactBean;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class SampleValidatorFactory extends AbstractGwtValidatorFactory {

  /**
   * Validator marker for the Validation Sample project. Only the classes listed in the {@link GwtValidation}
   * annotation can be validated.
   */
  @GwtValidation(value = { ContactBean.class }, groups = { Default.class })
  public interface GwtValidator extends Validator {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractGwtValidator createValidator() {

    return GWT.create(GwtValidator.class);
  }

}
