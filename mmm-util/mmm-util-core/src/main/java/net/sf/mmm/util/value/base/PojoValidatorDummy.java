/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.PojoValidator;

/**
 * This is a dummy implementation of {@link PojoValidator} that accepts all objects as
 * {@link #validate(Object) valid}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 * @deprecated Use {@link net.sf.mmm.util.validation.api.ValueValidator} instead.
 */
@Deprecated
public class PojoValidatorDummy extends AbstractValueValidator<Object> implements PojoValidator {

  /**
   * The constructor.
   */
  public PojoValidatorDummy() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(Object value, Object valueSource) throws RuntimeException {

    // dummy implementation - nothing to do...
  }
}
