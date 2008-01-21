/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a java {@link Class type} as
 * {@link net.sf.mmm.content.api.ContentObject entity}. Therefore an according
 * {@link net.sf.mmm.content.model.api.ContentClass content-class} is created
 * that can be configured via this annotation.<br>
 * 
 * @see net.sf.mmm.content.api.ContentObject
 * @see net.sf.mmm.content.model.api.ContentClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ClassAnnotation {

  /**
   * @see net.sf.mmm.content.value.base.SmartId#CLASS_ID_MINIMUM_CUSTOM
   * @return the {@link net.sf.mmm.content.value.base.SmartId#getClassId() id}
   *         of the class.
   */
  int id();

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.ContentClass#getName() name} of
   *         the {@link net.sf.mmm.content.model.api.ContentClass} reflecting
   *         the entity. If omitted the
   *         {@link Class#getSimpleName() simple-name} of the annotated class is
   *         used.
   */
  String name() default "";

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.ClassModifiers#isExtendable() extendable}
   *         flag of a
   *         {@link net.sf.mmm.content.model.api.ClassModifiers#isSystem() system-class}.
   *         Ignored in all other cases.
   */
  boolean isExtendable() default false;

  /**
   * @return <code>true</code> if the annotated entity is a
   *         {@link net.sf.mmm.content.api.ContentObject#isFolder() folder}.
   */
  boolean isFolder() default false;

  /**
   * @return the {@link RevisionControl} that determines if the annotated
   *         entity-type should be
   *         {@link net.sf.mmm.content.model.api.ContentClass#isRevisionControlled() revision-controlled}
   *         or NOT.
   */
  RevisionControl revisionControl() default RevisionControl.INHERIT;

  /**
   * TODO: is this part of the model or configuration of the GUI?
   * 
   * @return the selection type of the entity.
   */
  // EntitySelection selection() default EntitySelection.DEFAULT;
}
