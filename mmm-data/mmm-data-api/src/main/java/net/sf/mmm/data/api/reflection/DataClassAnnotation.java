/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a java {@link Class type} as a particular
 * {@link net.sf.mmm.data.api.DataObject}. Therefore an according
 * {@link net.sf.mmm.data.api.reflection.DataClass} is created that is
 * configured via this annotation.<br>
 * 
 * @see net.sf.mmm.data.api.DataObject
 * @see net.sf.mmm.data.api.reflection.DataClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DataClassAnnotation {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getObjectId() id} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass}. If omitted the ID will be
   * generated automatically. If an update is performed and the {@link #title()
   * title} has NOT changed then the existing ID will be reassigned. However,
   * for statically typed content classes it is suggested to supply fixed IDs to
   * prevent problems on renaming.
   * 
   * @see net.sf.mmm.data.api.reflection.DataClass#CLASS_ID_MINIMUM_CUSTOM
   */
  int id() default -1;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataClass#getTitle() title} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting the entity.
   * If omitted the {@link Class#getSimpleName() simple-name} of the annotated
   * class is used.
   */
  String title() default "";

  /**
   * The
   * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isExtendable()
   * extendable} flag of a
   * {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isSystem()
   * system-class}. Ignored in all other cases.
   */
  boolean isExtendable() default false;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataClassModifiers#isFinal()
   * final} flag of a {@link net.sf.mmm.data.api.reflection.DataClass}
   * identified by an java interface. Ignored if the annotated type is no
   * interface.
   */
  boolean isFinal() default false;

  /**
   * The {@link DataClassGroupVersion#getGroupId() group ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} identified by the
   * annotated type. Will be inherited from {@link DataClass#getSuperClass()
   * super class} if omitted.
   */
  String groupId() default "";

  /**
   * The {@link net.sf.mmm.util.version.api.VersionIdentifier#toString() version
   * identifier} of the current version of the {@link DataClassGroupVersion
   * class group}. If omitted the {@link #groupVersion()} will be taken from
   * another {@link DataClass} of the data model with the same
   * {@link #groupId()}. Therefore the {@link #groupVersion()} needs to be
   * defined at least once for each {@link #groupId()} or an exception will be
   * raised when
   * {@link net.sf.mmm.data.api.reflection.DataClassLoader#loadClasses()
   * loading} the data model.. Also, if a data model has different
   * {@link #groupVersion()}s for the same {@link #groupId()} an exception will
   * be thrown.
   */
  String groupVersion() default "";

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
