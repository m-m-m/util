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
 * This annotation is used to mark a
 * {@link java.lang.reflect.Modifier#isPublic(int) public} getter
 * {@link java.lang.reflect.Method method} of an
 * {@link net.sf.mmm.content.api.ContentObject entity} as a
 * {@link net.sf.mmm.content.model.api.ContentField content-field}.
 * 
 * @see ClassAnnotation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldAnnotation {

  /**
   * @return the {@link net.sf.mmm.content.value.base.SmartId#getClassId() id}
   *         of the field.
   */
  int id();

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.ContentField#getName() name} of
   *         the field. If omitted the bean-property-name of the annotated
   *         getter is used (e.g. <code>fooBar</code> for the annotated method
   *         <code>getFooBar()</code>).
   */
  String name() default "";

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isTransient() transient-flag}
   *         of the field.
   */
  boolean isTransient() default false;

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isStatic() static-flag}
   *         of the field. Will be ignored if the annotated getter is
   *         {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  boolean isStatic() default false;

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isReadOnly() readonly-flag}
   *         of the field. Will be ignored if the annotated getter has no
   *         according public setter.
   */
  boolean isReadOnly() default false;

  /**
   * @return the
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isFinal() final-flag}
   *         of the field. Will be ignored if the annotated getter if
   *         {@link java.lang.reflect.Modifier#isFinal(int) final}.
   */
  boolean isFinal() default false;

  /**
   * @return the inherited-flag of the field.
   */
  boolean isInherited() default false;

  /**
   * @return the {@link #id()} of a field of some
   *         {@link net.sf.mmm.content.api.ContentObject entity} <code>X</code>
   *         that represents a relation on the annotated entity. The annotated
   *         field is expected to be a collection of <code>X</code>.
   */
  int inverseRelationFieldId() default -1;

}
