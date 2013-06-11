/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.annotation.Annotation;

import javax.inject.Named;

import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

/**
 * This is the (default) implementation of
 * {@link net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorValidatorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Named
public class PojoDescriptorValidatorBuilderImpl extends AbstractPojoDescriptorValidatorBuilder {

  /**
   * The constructor.
   */
  public PojoDescriptorValidatorBuilderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ValueValidator<?> createValidator(Annotation annotation) {

    String simpleName = annotation.getClass().getSimpleName();
    if ("NotNull".equals(simpleName) || "NonNull".equals(simpleName)) {
      return ValidatorMandatory.getInstance();
    } else {
      // TODO hohwille add support for JSR 303, etc.
      return null;
    }
  }

}
