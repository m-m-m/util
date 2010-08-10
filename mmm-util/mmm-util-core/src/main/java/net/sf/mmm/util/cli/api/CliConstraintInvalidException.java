/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliConstraintInvalidException} is thrown if a property is annotated
 * with a constraint (e.g. {@link CliConstraintNumber},
 * {@link CliConstraintFile} or {@link CliConstraintCollection}) that is
 * invalid. Invalid typically means that the type of the property does not match
 * the type of the constraint.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliConstraintInvalidException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5643155246893981397L;

  /**
   * The constructor.
   * 
   * @param constraintAnnotation is the invalid {@link Annotation}. E.g.
   *        {@link CliConstraintNumber}.
   * @param property is the according property.
   */
  public CliConstraintInvalidException(Annotation constraintAnnotation, Object property) {

    super(NlsBundleUtilCore.ERR_CLI_CONSTRAINT_INVALID_FOR_PROPERTY, toMap(KEY_ANNOTATION,
        constraintAnnotation, KEY_PROPERTY, property));
  }

  /**
   * The constructor if the constraint itself is invalid.
   * 
   * @param property is the according property.
   * @param constraintAnnotation is the invalid {@link Annotation}. E.g.
   *        {@link CliConstraintCollection} with negative
   *        {@link CliConstraintCollection#min() minimum}.
   */
  public CliConstraintInvalidException(Object property, Annotation constraintAnnotation) {

    super(NlsBundleUtilCore.ERR_CLI_CONSTRAINT_INVALID, toMap(KEY_ANNOTATION, constraintAnnotation,
        KEY_PROPERTY, property));
  }

}
