/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.pojo.api.TypedProperty;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that validates that a mandatory value is filled. It
 * will produce a {@link net.sf.mmm.util.validation.api.ValidationFailure} if the value is not provided (empty, blank,
 * {@code null}).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidatorMandatory extends AbstractValueValidator<Object> {

  /** @see #getCode() */
  public static final String CODE = "Mandatory";

  private static final ValidatorMandatory INSTANCE = new ValidatorMandatory();

  /**
   * The constructor.
   */
  public ValidatorMandatory() {

    super();
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  /**
   * @return the singleton instance of this class.
   */
  public static ValidatorMandatory getInstance() {

    return INSTANCE;
  }

  @Override
  protected NlsMessage validateNull() {

    return getFailureMessage();
  }

  /**
   * @return the failure message.
   */
  private NlsMessage getFailureMessage() {

    return createBundle(NlsBundleUtilValidationRoot.class).errorMandatory();
  }

  @Override
  protected NlsMessage validateNotNull(Object value) {

    if (value instanceof Collection) {
      Collection<?> collection = (Collection<?>) value;
      if (collection.isEmpty()) {
        return getFailureMessage();
      }
    } else if (value instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) value;
      if (map.isEmpty()) {
        return getFailureMessage();
      }
    } else if (value instanceof CharSequence) {
      CharSequence sequence = (CharSequence) value;
      if (sequence.length() == 0) {
        return getFailureMessage();
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P getProperty(TypedProperty<P> property) {

    if (property == PROPERTY_MANDATORY) {
      return (P) Boolean.TRUE;
    }
    return super.getProperty(property);
  }

  @Override
  public int hashCode() {

    return 1;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    return true;
  }

}
