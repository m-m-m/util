/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a java {@link Class type} as
 * {@link net.sf.mmm.content.api.ContentObject} and its sub-types. Therefore an
 * according {@link net.sf.mmm.content.reflection.api.ContentClass} is created
 * that can be configured via this annotation.<br>
 * 
 * @see net.sf.mmm.content.api.ContentObject
 * @see net.sf.mmm.content.reflection.api.ContentClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ContentClassAnnotation {

  /**
   * The {@link net.sf.mmm.content.datatype.api.ContentId#getObjectId() id} of
   * the {@link net.sf.mmm.content.reflection.api.ContentClass}. If omitted the
   * ID will be generated automatically. If an update is performed and the
   * {@link #title() title} has NOT changed then the existing ID will be
   * reassigned. However, for statically typed content classes it is suggested
   * to supply fixed IDs to prevent problems on renaming.
   * 
   * @see net.sf.mmm.content.reflection.api.ContentClass#CLASS_ID_MINIMUM_CUSTOM
   */
  int id() default -1;

  /**
   * The {@link net.sf.mmm.content.reflection.api.ContentClass#getTitle() title}
   * of the {@link net.sf.mmm.content.reflection.api.ContentClass} reflecting
   * the entity. If omitted the {@link Class#getSimpleName() simple-name} of the
   * annotated class is used.
   */
  String title() default "";

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentClassModifiers#isExtendable()
   * extendable} flag of a
   * {@link net.sf.mmm.content.reflection.api.ContentClassModifiers#isSystem()
   * system-class}. Ignored in all other cases.
   */
  boolean isExtendable() default false;

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentClassModifiers#isFinal()
   * final} flag of a {@link net.sf.mmm.content.reflection.api.ContentClass}
   * identified by an java interface. Ignored if the annotated type is no
   * interface.
   */
  boolean isFinal() default false;

  // /**
  // * the {@link RevisionControl} that determines if the annotated entity-type
  // * should be
  // * {@link
  // net.sf.mmm.content.reflection.api.ContentClass#isRevisionControlled()
  // * revision-controlled} or NOT.
  // */
  // RevisionControl revisionControl() default RevisionControl.INHERIT;

  /**
   * TODO: is this part of the model or configuration of the GUI?
   * 
   * @return the selection type of the entity.
   */
  // EntitySelection selection() default EntitySelection.DEFAULT;
}
