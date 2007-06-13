/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.term.api.Term;

/**
 * This interface extends the {@link ContentModelReadAccess}interface with
 * methods to edit the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableModelStore extends ContentModelReadAccess {

  /**
   * This method creates a sub-class of the given class with the specified id.
   * 
   * @param superClass is the super-class of the class to create.
   * @param name is the name of the class to create.
   * @param isAbstract is the abstract flag for the class to create.
   * @param isFinal is the final flag for the class to create.
   * @return the created class.
   * @throws ContentModelException if the class could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is final</li>
   *         <li>the given id already identifies another class</li>
   *         <li>both abstract and final flag are set</li>
   *         </ul>
   */
  ContentClass createClass(ContentClass superClass, String name, boolean isAbstract, boolean isFinal)
      throws ContentModelException;

  /**
   * This method creates a field in the given class with the specified name.
   * 
   * @param declaringClass is the class where the new field will be added.
   * @param name is the name of the field to create.
   * @param type is the type of the values that can be stored in the field to
   *        create.
   * @param isFinal the final flag of the field to create.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already has a field with the given name</li>
   *         </ul>
   */
  ContentField createField(ContentClass declaringClass, String name, Class type, boolean isFinal)
      throws ContentModelException;

  /**
   * This method creates a transient field in the given class with the specified
   * name.
   * 
   * @param declaringClass is the class where the new field will be added.
   * @param name is the name of the field to create.
   * @param type is the type of the values that can be stored in the field to
   *        create.
   * @param isFinal the final flag of the field to create.
   * @param term is an expression term that determines the value of the field
   *        dynamically on access.
   * @return the created field.
   * @throws ContentModelException if the field could not be created. This can
   *         have one of the following reasons:
   *         <ul>
   *         <li>the given class is a system class</li>
   *         <li>the given class already declares a field with the given name
   *         </li>
   *         </ul>
   */
  ContentField createTransientField(ContentClass declaringClass, String name, Class type,
      boolean isFinal, Term term) throws ContentModelException;

  /**
   * This method sets the status of the deleted flag of the given class or
   * field.
   * 
   * @param classOrField is the class or field to mark as deleted.
   * @param deleteFlag - if <code>true</code> the class will be marked as
   *        deleted, else it will be undeleted.
   * @throws ContentModelException if the deleted flag could not be modified.
   *         This can have one of the following reasons:
   *         <ul>
   *         <li>the given class or field has the system flag set</li>
   *         </ul>
   */
  void setDeleted(ContentReflectionObject classOrField, boolean deleteFlag)
      throws ContentModelException;

}
