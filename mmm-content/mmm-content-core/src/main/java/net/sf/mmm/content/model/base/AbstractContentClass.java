/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.util.collection.CollectionIterable;
import net.sf.mmm.util.collection.SizedIterable;

/**
 * This is the abstract base implementation of the {@link ContentClass}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClass extends AbstractContentReflectionObject implements
    ContentClass {

  /** UID for serialization. */
  private static final long serialVersionUID = -8298083039801634149L;

  /** the super-class of this class */
  private ContentClass superClass;

  /** @see #getModifiers() */
  private ClassModifiers modifiers;

  /** @see #getImplementation() */
  private Class<? extends ContentObject> implementation;

  /** the list of direct sub-classes */
  private final List<ContentClass> subClasses;

  /** @see #getSubClasses() */
  private final List<ContentClass> subClassesView;

  /** @see #getDeclaredFields() (map of content-fields by name) */
  private final Map<String, ContentField> declaredFields;

  /** @see #getDeclaredFields() */
  private final SizedIterable<ContentField> declaredFieldsIterable;

  /** @see #getDeclaredFields() */
  private final SizedIterable<ContentField> fieldsIterable;

  /**
   * The constructor.
   */
  public AbstractContentClass() {

    super();
    this.subClasses = new ArrayList<ContentClass>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.declaredFields = new HashMap<String, ContentField>();
    this.declaredFieldsIterable = new CollectionIterable<ContentField>(this.declaredFields.values());
    this.fieldsIterable = new FieldsIterable();
    if (this.superClass != null) {
      if (this.superClass.getModifiers().isFinal()) {
        // TODO: NLS
        throw new ContentModelException("Can NOT extend final class!");
      }
    }
  }

  /**
   * This method sets the {@link #getSuperClass() super-class}.
   * 
   * @param superClass the super-class to set.
   */
  protected void setSuperClass(ContentClass superClass) {

    this.superClass = superClass;
  }

  /**
   * {@inheritDoc}
   */
  public SizedIterable<ContentField> getDeclaredFields() {

    return this.declaredFieldsIterable;
  }

  /**
   * {@inheritDoc}
   */
  public ContentField getDeclaredField(String name) {

    return this.declaredFields.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public ContentField getField(String name) {

    ContentField field = this.declaredFields.get(name);
    if ((field == null) && (this.superClass != null)) {
      field = this.superClass.getField(name);
    }
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public SizedIterable<ContentField> getFields() {

    return this.fieldsIterable;
  }

  /**
   * {@inheritDoc}
   */
  public ClassModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(ClassModifiers modifiers) {

    this.modifiers = modifiers;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getSuperClass() {

    return this.superClass;
  }

  /**
   * {@inheritDoc}
   */
  public List<ContentClass> getSubClasses() {

    return this.subClassesView;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSubClassOf(ContentClass contentClass) {

    if (this.superClass == null) {
      // root-class can NOT be a sub-class
      return false;
    } else if (this.superClass == contentClass) {
      // given class is direct super-class
      return true;
    } else {
      // check recursive
      return this.superClass.isSubClassOf(contentClass);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSuperClassOf(ContentClass contentClass) {

    return contentClass.isSubClassOf(this);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isClass() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDeleted() {

    if (getDeletedFlag()) {
      return true;
    } else if (this.superClass == null) {
      return false;
    } else {
      return this.superClass.isDeleted();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getFieldValue(ContentField field, String fieldName)
      throws PermissionDeniedException, ContentException {

    if (fieldName.equals(FIELD_NAME_MODIFIERS)) {
      return getModifiers();
    } else if (fieldName.equals(FIELD_NAME_SUPER_CLASS)) {
      return getSuperClass();
    } else if (fieldName.equals(FIELD_NAME_SUB_CLASSES)) {
      return getSubClasses();
    } else if (fieldName.equals(FIELD_NAME_FIELDS)) {
      return getFields();
    } else if (fieldName.equals(FIELD_NAME_DECLARED_FIELDS)) {
      return getDeclaredFields();
    } else {
      return super.getFieldValue(field, fieldName);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setValue(ContentField field, String fieldName, Object value)
      throws PermissionDeniedException, ContentException {

    if (fieldName.equals(FIELD_NAME_MODIFIERS)) {
      // this.modifiers = (ClassModifiers) value;
    } else {
      super.setValue(field, fieldName, value);
    }
  }

  /**
   * This method adds a {@link #getSubClasses() sub-class} to this class.
   * 
   * @param subClass is the sub-class to add.
   * @throws ContentModelException if the operation fails.
   */
  public void addSubClass(ContentClass subClass) throws ContentModelException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new ContentModelException("Class can not extend itself!");
    }
    if (subClass.getModifiers().isSystem() && !getModifiers().isSystem()) {
      // TODO: NLS
      throw new ContentModelException("System-class can NOT extend user-class!");
    }
    if (getModifiers().isFinal()) {
      // TODO: NLS
      throw new ContentModelException("Can NOT extend final class!");
    }
    assert (!this.subClasses.contains(subClass));
    this.subClasses.add(subClass);
  }

  /**
   * This method adds the given <code>field</code> to this class.
   * 
   * @param field is the field to add.
   * @throws ContentModelException if the field could NOT be added.
   */
  public void addField(ContentField field) throws ContentModelException {

    if (field.getDeclaringClass() != this) {
      throw new ContentModelException("Can NOT add field if NOT declared by this class!");
    }
    ContentField duplicate = this.declaredFields.get(field.getName());
    if (duplicate != null) {
      throw new DuplicateFieldException(field.getName());
    }
    this.declaredFields.put(field.getName(), field);
  }

  /**
   * This method gets the class reflecting the closest type of this
   * content-class.
   * 
   * @return the "implementation".
   */
  public Class<? extends ContentObject> getImplementation() {

    return this.implementation;
  }

  /**
   * This method sets the {@link #getImplementation() implementation} of this
   * content-class.
   * 
   * @param implementation is the implementation to set.
   */
  protected void setImplementation(Class<? extends ContentObject> implementation) {

    this.implementation = implementation;
  }

  /**
   * @see ContentClass#getFields()
   */
  private class FieldsIterable implements SizedIterable<ContentField> {

    /**
     * {@inheritDoc}
     */
    public int getSize() {

      int result = 0;
      Iterator<ContentField> fieldsIterator = AbstractContentClass.this.declaredFields.values()
          .iterator();
      while (fieldsIterator.hasNext()) {
        ContentField myField = fieldsIterator.next();
        if (myField.getSuperField() == null) {
          result++;
        }
      }
      // the calculation until here should be stored as member.
      // the only problem is that the value may change if a super-class
      // changes.
      if (AbstractContentClass.this.superClass != null) {
        result += AbstractContentClass.this.superClass.getFields().getSize();
      }
      return result;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<ContentField> iterator() {

      return new ContentFieldIterator(AbstractContentClass.this);
    }

  }

}
