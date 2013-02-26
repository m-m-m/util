/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection.access;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This interface gives write access to the content-model (reflection).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataReflectionWriteAccess {

  /**
   * This method creates a sub-class of the given class.
   * 
   * @param <CLASS> is the generic type of the according {@link DataClass#getJavaClass() java class}.
   * @param superClass is the {@link DataClass#getSuperClass() super-class} of the class to create.
   * @param title is the {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the class to create.
   * @param modifiers are the {@link DataClass#getModifiers() modifiers} for the class to create.
   * @return the created class.
   * @throws DataReflectionException if the class could not be created. This can have one of the following
   *         reasons:
   *         <ul>
   *         <li>the given <code>superClass</code> is final</li>
   *         <li>the given <code>id</code> already identifies another class</li>
   *         <li>the given <code>name</code> already identifies another class</li>
   *         <li>both abstract and final flag are set</li>
   *         </ul>
   */
  <CLASS extends DataObject> DataClass<? extends CLASS> createDataClass(DataClass<CLASS> superClass, String title,
      DataClassModifiers modifiers) throws DataReflectionException;

  /**
   * This method creates a field in the given class with the specified name.
   * 
   * @param <CLASS> is the generic type of {@link DataClass#getJavaClass()}.
   * @param <FIELD> is the generic type of {@link DataField#getFieldType()}.
   * 
   * @param declaringClass is the {@link DataField#getDeclaringClass() class} where the new field will be
   *        added.
   * @param title is the {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the field to create.
   * @param type is the {@link DataField#getFieldType() type} of the values that can be stored in the created
   *        field. Use
   *        {@link net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(java.lang.reflect.Type, Class)}
   *        to create an instance of {@link GenericType}.
   * @param modifiers are the {@link DataField#getModifiers() modifiers} for the field to create.
   * @return the created field.
   * @throws DataReflectionException if the field could not be created. This can have one of the following
   *         reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name</li>
   *         </ul>
   */
  <CLASS extends DataObject, FIELD> DataField<CLASS, FIELD> createDataField(DataClass<CLASS> declaringClass,
      String title, GenericType<FIELD> type, DataFieldModifiers modifiers) throws DataReflectionException;

  /**
   * This method creates a {@link FieldModifiersImpl#isTransient() transient} field in the given class with
   * the specified name.
   * 
   * @param declaringClass is the {@link ContentField#getDeclaringClass() class} where the new field will be
   *        added.
   * @param name is the {@link net.sf.mmm.content.ContentObjectIF#getName() name} of the field to create.
   * @param type is the {@link ContentField#getFieldType() type} of the values that can be stored in the field
   *        to create.
   * @param modifiers are the {@link ContentField#getModifiers() modifiers} for the field to create. They must
   *        be {@link FieldModifiersImpl#isTransient() transient} (@see #createField(ContentClass, String,
   *        Class, FieldModifiersImpl)).
   * @param term is the term used for dynamic
   *        {@link ContentField#calculate(net.sf.mmm.content.ContentObjectIF) calculation} of this
   *        {@link FieldModifiersImpl#isTransient() transient} field.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can have one of the following
   *         reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name</li>
   *         </ul>
   */
  // ContentField createField(ContentClass declaringClass, String name,
  // Class type,
  // FieldModifiersImpl modifiers, Term term) throws ContentModelException;
  /**
   * This method sets the status of the deleted flag of the given class or field.
   * 
   * @param classOrField is the class or field to mark as deleted.
   * @param newDeletedFlag - if <code>true</code> the class will be marked as deleted, else it will be
   *        undeleted.
   * @throws DataReflectionException if the deleted flag could not be modified. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the given class or field has the system flag set</li>
   *         </ul>
   */
  void setDeleted(DataReflectionObject<? extends DataObject> classOrField, boolean newDeletedFlag)
      throws DataReflectionException;

}
