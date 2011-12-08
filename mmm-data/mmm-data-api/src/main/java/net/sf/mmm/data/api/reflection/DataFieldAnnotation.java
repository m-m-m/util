/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This annotation is used to mark a
 * {@link java.lang.reflect.Modifier#isPublic(int) public} getter
 * {@link java.lang.reflect.Method method} of
 * {@link net.sf.mmm.data.api.DataObjectView} and its sub-types as a
 * {@link net.sf.mmm.data.api.reflection.DataField}.
 * 
 * @see DataClassAnnotation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFieldAnnotation {

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getId() id} of the field. If
   * omitted the ID will be generated automatically. If an update is performed
   * and the {@link #title() title} has NOT changed then the existing ID will be
   * reassigned. However, for statically typed data classes it is suggested to
   * supply fixed IDs to prevent problems on renaming.
   */
  long id() default DataId.OBJECT_ID_ILLEGAL;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataField#getTitle() name} of the
   * field. If omitted the bean-property-name of the annotated getter is used
   * (e.g. <code>fooBar</code> if the annotated method is named
   * <code>getFooBar()</code>).
   */
  String title() default "";

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isTransient()
   * transient-flag} of the field. The value defaults to <code>false</code>. All
   * values of {@link #isTransient()} that differ from {@link BooleanEnum#NULL}
   * for a single field declared in view interface, mutable interface or
   * implementation have to be equal. Otherwise the
   * {@link net.sf.mmm.data.api.reflection.DataClassLoader} will throw an
   * exception.
   */
  BooleanEnum isTransient() default BooleanEnum.NULL;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isStatic()
   * static-flag} of the field. If not set, the value will be determined
   * automatically. This is done by checking if the annotated getter is
   * {@link java.lang.reflect.Modifier#isStatic(int) static} what is only
   * possible if declared in a static implementation.
   */
  BooleanEnum isStatic() default BooleanEnum.NULL;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isReadOnly()
   * readonly-flag} of the field. If not set, the value will be determined
   * automatically. For regular datatypes this is determined by the existence of
   * a public setter to the annotated getter. For a {@link java.util.Collection}
   * read only is assumed to be <code>false</code>. For
   * {@link net.sf.mmm.data.api.link.LinkList} read only is assumed to be
   * <code>true</code> except the getter of the implementation or mutable
   * interface returns {@link net.sf.mmm.data.api.link.MutableLinkList}.
   */
  BooleanEnum isReadOnly() default BooleanEnum.NULL;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isFinal()
   * final-flag} of the field. If not set, the value will be determined
   * automatically. This is determined by checking if the annotated getter is
   * {@link java.lang.reflect.Modifier#isFinal(int) final} in the
   * implementation.
   */
  BooleanEnum isFinal() default BooleanEnum.NULL;

  /**
   * The inherited-flag of the field.
   * 
   * @see net.sf.mmm.data.api.reflection.DataFieldModifiers#isInheritedFromParent()
   */
  boolean isInheritedFromParent() default false;

  /**
   * The {@link #id()} of a field of some
   * {@link net.sf.mmm.data.api.DataObjectView} <code>X</code> that represents a
   * relation on the annotated type. The annotated field is expected to be a
   * collection of all <code>X</code> that link to this object in the
   * {@link net.sf.mmm.data.api.reflection.DataField} specified by the given
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() ID}.<br/>
   * The default is <code>-1</code> indicating that this is not an inverse
   * relation.
   */
  long inverseRelationFieldId() default -1;

}
