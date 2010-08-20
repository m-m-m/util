/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is an implementation of {@link net.sf.mmm.util.value.api.validator.ValueValidator}
 * that {@link #validate(Object, Object) validates} that a given
 * <code>value</code> is NOT <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class ValueValidatorNotNull extends AbstractValueValidator<Object> {

  /** The singleton instance. */
  public static final ValueValidatorNotNull INSTANCE = new ValueValidatorNotNull();

  /**
   * The constructor.
   */
  public ValueValidatorNotNull() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void validate(Object value, Object valueSource) throws RuntimeException {

    if (value == null) {
      String argument;
      if (valueSource == null) {
        argument = "value";
      } else {
        argument = valueSource.toString();
      }
      throw new NlsNullPointerException(argument);
    }
  }

}
