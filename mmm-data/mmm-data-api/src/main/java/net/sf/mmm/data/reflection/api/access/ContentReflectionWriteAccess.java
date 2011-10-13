/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api.access;

import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.reflection.api.ContentClassModifiers;
import net.sf.mmm.data.reflection.api.ContentField;
import net.sf.mmm.data.reflection.api.ContentFieldModifiers;
import net.sf.mmm.data.reflection.api.ContentReflectionException;
import net.sf.mmm.data.reflection.api.ContentReflectionObject;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This interface gives write access to the content-model (reflection).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentReflectionWriteAccess {

  /**
   * This method creates a sub-class of the given class.
   * 
   * @param superClass is the {@link ContentClass#getSuperClass() super-class}
   *        of the class to create.
   * @param name is the {@link net.sf.mmm.data.api.ContentObject#getTitle()
   *        name} of the class to create.
   * @param modifiers are the {@link ContentClass#getContentModifiers()
   *        modifiers} for the class to create.
   * @return the created class.
   * @throws ContentReflectionException if the class could not be created. This
   *         can have one of the following reasons:
   *         <ul>
   *         <li>the given <code>superClass</code> is final</li>
   *         <li>the given <code>id</code> already identifies another class</li>
   *         <li>the given <code>name</code> already identifies another class</li>
   *         <li>both abstract and final flag are set</li>
   *         </ul>
   */
  ContentClass<? extends ContentObject> createContentClass(
      ContentClass<? extends ContentObject> superClass, String name, ContentClassModifiers modifiers)
      throws ContentReflectionException;

  /**
   * This method creates a field in the given class with the specified name.
   * 
   * @param <FIELD> is the generic type of {@link ContentField#getFieldType()}.
   * 
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class}
   *        where the new field will be added.
   * @param name is the {@link net.sf.mmm.data.api.ContentObject#getTitle()
   *        name} of the field to create.
   * @param type is the {@link ContentField#getFieldType() type} of the values
   *        that can be stored in the created field. Use
   *        {@link net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(java.lang.reflect.Type, Class)}
   *        to create an instance of {@link GenericType}.
   * @param modifiers are the {@link ContentField#getContentModifiers()
   *        modifiers} for the field to create.
   * @return the created field.
   * @throws ContentReflectionException if the field could not be created. This
   *         can have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name</li>
   *         </ul>
   */
  <FIELD> ContentField<? extends ContentObject, FIELD> createContentField(
      ContentClass<? extends ContentObject> declaringClass, String name, GenericType<FIELD> type,
      ContentFieldModifiers modifiers) throws ContentReflectionException;

  /**
   * This method creates a {@link FieldModifiersImpl#isTransient() transient}
   * field in the given class with the specified name.
   * 
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class}
   *        where the new field will be added.
   * @param name is the {@link net.sf.mmm.content.ContentObjectIF#getName()
   *        name} of the field to create.
   * @param type is the {@link ContentField#getFieldType() type} of the values
   *        that can be stored in the field to create.
   * @param modifiers are the {@link ContentField#getModifiers() modifiers} for
   *        the field to create. They must be
   *        {@link FieldModifiersImpl#isTransient() transient} (@see
   *        #createField(ContentClass, String, Class, FieldModifiersImpl)).
   * @param term is the term used for dynamic
   *        {@link ContentField#calculate(net.sf.mmm.content.ContentObjectIF)
   *        calculation} of this {@link FieldModifiersImpl#isTransient()
   *        transient} field.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name</li>
   *         </ul>
   */
  // ContentField createField(ContentClass declaringClass, String name,
  // Class type,
  // FieldModifiersImpl modifiers, Term term) throws ContentModelException;
  /**
   * This method sets the status of the deleted flag of the given class or
   * field.
   * 
   * @param classOrField is the class or field to mark as deleted.
   * @param newDeletedFlag - if <code>true</code> the class will be marked as
   *        deleted, else it will be undeleted.
   * @throws ContentReflectionException if the deleted flag could not be
   *         modified. This can have one of the following reasons:
   *         <ul>
   *         <li>the given class or field has the system flag set</li>
   *         </ul>
   */
  void setDeleted(ContentReflectionObject<? extends ContentObject> classOrField,
      boolean newDeletedFlag) throws ContentReflectionException;

}
