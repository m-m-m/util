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
 * This annotation is used to mark a
 * {@link java.lang.reflect.Modifier#isPublic(int) public} getter
 * {@link java.lang.reflect.Method method} of
 * {@link net.sf.mmm.content.api.ContentObject} and its sub-types as a
 * {@link net.sf.mmm.content.reflection.api.ContentField}.
 * 
 * @see ContentClassAnnotation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContentFieldAnnotation {

  /**
   * The {@link net.sf.mmm.content.api.ContentObject#getId() id} of the field.
   */
  int id();

  /**
   * The {@link net.sf.mmm.content.reflection.api.ContentField#getTitle() name}
   * of the field. If omitted the bean-property-name of the annotated getter is
   * used (e.g. <code>fooBar</code> if the annotated method is named
   * <code>getFooBar()</code>).
   */
  String name() default "";

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentFieldModifiers#isTransient()
   * transient-flag} of the field.
   */
  boolean isTransient() default false;

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentFieldModifiers#isStatic()
   * static-flag} of the field. Will be ignored if the annotated getter is
   * {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  boolean isStatic() default false;

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentFieldModifiers#isReadOnly()
   * readonly-flag} of the field. Will be ignored if the annotated getter has no
   * according public setter.
   */
  boolean isReadOnly() default false;

  /**
   * The
   * {@link net.sf.mmm.content.reflection.api.ContentFieldModifiers#isFinal()
   * final-flag} of the field. Will be ignored if the annotated getter is
   * {@link java.lang.reflect.Modifier#isFinal(int) final}.
   */
  boolean isFinal() default false;

  /**
   * The inherited-flag of the field.
   * 
   * @see net.sf.mmm.content.reflection.api.ContentFieldModifiers#isInheritedFromParent()
   */
  boolean isInheritedFromParent() default false;

  /**
   * The {@link #id()} of a field of some
   * {@link net.sf.mmm.content.api.ContentObject} <code>X</code> that represents
   * a relation on the annotated type. The annotated field is expected to be a
   * collection of all <code>X</code> that link to this object in the
   * {@link net.sf.mmm.content.reflection.api.ContentField} specified by the
   * given {@link net.sf.mmm.content.reflection.api.ContentField#getId() ID}.<br/>
   * The default is <code>-1</code> indicating that this is not an inverse
   * relation.
   */
  int inverseRelationFieldId() default -1;

}
