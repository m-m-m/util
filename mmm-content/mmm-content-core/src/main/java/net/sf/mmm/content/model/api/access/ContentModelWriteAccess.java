/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api.access;

import java.lang.reflect.Type;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.model.api.FieldModifiers;

/**
 * This interface gives write access to the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelWriteAccess {

  /**
   * This method creates a sub-class of the given class.
   * 
   * @param superClass is the {@link ContentClass#getSuperClass() super-class}
   *        of the class to create.
   * @param name is the
   *        {@link net.sf.mmm.content.api.ContentObject#getName() name} of the
   *        class to create.
   * @param modifiers are the {@link ContentClass#getModifiers() modifiers} for
   *        the class to create.
   * @return the created class.
   * @throws ContentModelException if the class could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given <code>superClass</code> is final</li>
   *         <li>the given <code>id</code> already identifies another class</li>
   *         <li>the given <code>name</code> already identifies another class</li>
   *         <li>both abstract and final flag are set</li>
   *         </ul>
   */
  ContentClass createContentClass(ContentClass superClass, String name, ClassModifiers modifiers)
      throws ContentModelException;

  /**
   * This method creates a field in the given class with the specified name.
   * 
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class}
   *        where the new field will be added.
   * @param name is the
   *        {@link net.sf.mmm.content.api.ContentObject#getName() name} of the
   *        field to create.
   * @param type is the {@link ContentField#getFieldType() type} of the values
   *        that can be stored in the field to create.
   * @param modifiers are the {@link ContentField#getModifiers() modifiers} for
   *        the field to create.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name
   *         </li>
   *         </ul>
   */
  ContentField createContentField(ContentClass declaringClass, String name, Type type,
      FieldModifiers modifiers) throws ContentModelException;

  /**
   * This method creates a {@link FieldModifiersImpl#isTransient() transient}
   * field in the given class with the specified name.
   * 
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class}
   *        where the new field will be added.
   * @param name is the
   *        {@link net.sf.mmm.content.ContentObjectIF#getName() name} of the
   *        field to create.
   * @param type is the {@link ContentField#getFieldType() type} of the values
   *        that can be stored in the field to create.
   * @param modifiers are the {@link ContentField#getModifiers() modifiers} for
   *        the field to create. They must be
   *        {@link FieldModifiersImpl#isTransient() transient} (@see
   *        #createField(ContentClass, String, Class, FieldModifiersImpl)).
   * @param term is the term used for dynamic
   *        {@link ContentField#calculate(net.sf.mmm.content.ContentObjectIF) calculation}
   *        of this {@link FieldModifiersImpl#isTransient() transient} field.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name
   *         </li>
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
   * @throws ContentModelException if the deleted flag could not be modified.
   *         This can have one of the following reasons:
   *         <ul>
   *         <li>the given class or field has the system flag set</li>
   *         </ul>
   */
  void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentModelException;

}
