/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This enum contains the available types that configure the
 * {@link DataClassAnnotation#revisionControl() revision-control}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum RevisionControl {

  /**
   * This type indicates that the
   * {@link net.sf.mmm.content.model.api.ContentClass#isRevisionControlled()
   * revision-control} is inherited from the
   * {@link net.sf.mmm.content.model.api.ContentClass#getSuperClass()
   * super-class}.
   */
  INHERIT,

  /**
   * This type indicates that the annotated entity-type should be under
   * revision-control.
   */
  YES,

  /**
   * This type indicates that the annotated entity should NOT be under
   * revision-control.
   */
  NO

}
