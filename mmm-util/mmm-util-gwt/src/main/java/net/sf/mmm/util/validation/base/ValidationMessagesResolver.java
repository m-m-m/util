/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.validation.client.AbstractValidationMessageResolver;
import com.google.gwt.validation.client.UserValidationMessagesResolver;

/**
 * This is the {@link UserValidationMessagesResolver} for {@link ValidationMessages}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ValidationMessagesResolver extends AbstractValidationMessageResolver implements
    UserValidationMessagesResolver {

  /**
   * The constructor.
   */
  public ValidationMessagesResolver() {

    super(GWT.<ValidationMessages> create(ValidationMessages.class));
  }

}
