/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliConstraintIllegalException} is thrown if a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * is annotated with a constraint (e.g. {@link CliConstraintNumber},
 * {@link CliConstraintFile} or {@link CliConstraintContainer}) that is illegal.
 * Invalid typically means that the type of the property does not match the type
 * of the constraint or the values of the annotation are inconsistent.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliConstraintIllegalException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5643155246893981397L;

  /**
   * The constructor.
   * 
   * @param constraintAnnotation is the invalid {@link Annotation}. E.g.
   *        {@link CliConstraintNumber}.
   * @param property is the according property.
   */
  public CliConstraintIllegalException(Annotation constraintAnnotation, Object property) {

    super(NlsBundleUtilCore.ERR_CLI_CONSTRAINT_ILLEGAL_FOR_PROPERTY, toMap(KEY_ANNOTATION,
        constraintAnnotation, KEY_PROPERTY, property));
  }

  /**
   * The constructor if the constraint itself is invalid.
   * 
   * @param property is the according property.
   * @param constraintAnnotation is the invalid {@link Annotation}. E.g.
   *        {@link CliConstraintContainer} with negative
   *        {@link CliConstraintContainer#min() minimum}.
   */
  public CliConstraintIllegalException(Object property, Annotation constraintAnnotation) {

    super(NlsBundleUtilCore.ERR_CLI_CONSTRAINT_ILLEGAL_VALUES, toMap(KEY_ANNOTATION, constraintAnnotation,
        KEY_PROPERTY, property));
  }

}
