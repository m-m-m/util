/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.util.io.SizedIterable;

/**
 * This is the abstract base implementation of the {@link ContentClass}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClass extends AbstractContentObject implements ContentClass {

  /** the super-class of this class */
  private ContentClass superClass;

  /** the list of direct sub-classes */
  private final List<AbstractContentClass> subClasses;

  /** @see #getSubClasses() */
  private final List<AbstractContentClass> subClassesView;

  /** @see #getDeclaredFields() (map of content-fields by name) */
  private final Map<String, AbstractContentField> declaredFields;

  /** @see #getDeclaredFields() */
  private final SizedIterable<AbstractContentField> declaredFieldsIterable;

  /** @see #getDeclaredFields() */
  private final SizedIterable<AbstractContentField> fieldsIterable;

  /** @see #getModifiers() */
  private final ClassModifiers modifiers;

  /**
   * The constructor.
   * 
   * @param classId
   *        is the {@link #getId() ID} of the class.
   * @param className
   *        is the {@link #getName() name} of the class.
   * @param parentClass
   *        is the {@link #getSuperClass() super-class} of the class or
   *        <code>null</code> for creating the root-class.
   * @param classModifiers
   *        are the {@link #getModifiers() modifiers}.
   */
  public AbstractContentClass(IdImpl classId, String className, ContentClass parentClass,
      ClassModifiers classModifiers) {

    super(classId, className);
    assert (classId.getObjectId() == IdImpl.OID_CLASS);
    this.superClass = parentClass;
    this.modifiers = classModifiers;
    this.subClasses = new ArrayList<AbstractContentClass>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.declaredFields = new HashMap<String, AbstractContentField>();
    this.declaredFieldsIterable = new DeclaredFieldsIterable(Collections
        .unmodifiableCollection(this.declaredFields.values()));
    this.fieldsIterable = new FieldsIterable();
    if (this.superClass != null) {
      if (this.superClass.getModifiers().isFinal()) {
        // TODO: NLS
        throw new ContentModelRuntimeException("Can NOT extend final class!");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public SizedIterable<AbstractContentField> getDeclaredFields() {

    return this.declaredFieldsIterable;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField getDeclaredField(String name) {

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
  public SizedIterable<AbstractContentField> getFields() {

    return this.fieldsIterable;
  }

  /**
   * {@inheritDoc}
   */
  public ClassModifiers getModifiers() {

    return this.modifiers;
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
  public List<AbstractContentClass> getSubClasses() {

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
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
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
  protected void setFieldValue(ContentField field, String fieldName, Object value)
      throws PermissionDeniedException, ContentException {

    if (fieldName.equals(FIELD_NAME_MODIFIERS)) {
      // this.modifiers = (ClassModifiers) value;
    }
  }

  /**
   * This method adds a {@link #getSubClasses() sub-class} to this class.
   * 
   * @param subClass
   *        is the sub-class to add.
   * @throws ContentModelRuntimeException
   *         if the operation fails.
   */
  public void addSubClass(AbstractContentClass subClass) throws ContentModelRuntimeException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new ContentModelRuntimeException("Internal error!");
    }
    if (subClass.getModifiers().isSystem() && !getModifiers().isSystem()) {
      // TODO: NLS
      throw new ContentModelRuntimeException("System-class can NOT extend user-class!");
    }
    if (getModifiers().isFinal()) {
      // TODO: NLS
      throw new ContentModelRuntimeException("Can NOT extend final class!");
    }
    assert (!this.subClasses.contains(subClass));
    this.subClasses.add(subClass);
  }

  /**
   * This method adds the given <code>field</code> to this class.
   * 
   * @param field
   *        is the field to add.
   * @throws ContentModelRuntimeException
   *         if the field could NOT be added.
   */
  public void addField(AbstractContentField field) throws ContentModelRuntimeException {

    if (field.getDeclaringClass() != this) {
      throw new ContentModelRuntimeException("Internal error!");
    }
    ContentField duplicate = this.declaredFields.get(field.getName());
    if (duplicate != null) {
      throw new DuplicateFieldException(field.getName());
    }
    this.declaredFields.put(field.getName(), field);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getName();
    /*
     * StringBuffer result = new StringBuffer(); result.append("Class:");
     * result.append(getId()); result.append("["); if
     * (getModifiers().isAbstract()) { result.append("A"); } if
     * (getModifiers().isFinal()) { result.append("F"); } if
     * (getModifiers().isSystem()) { result.append("S"); } if (isDeleted()) {
     * result.append("D"); } result.append("]"); return result.toString();
     */
  }

  /**
   * @see ContentClass#getDeclaredFields()
   */
  private static class DeclaredFieldsIterable implements SizedIterable<AbstractContentField> {

    /** @see #getDeclaredFields() */
    private final Collection<AbstractContentField> fieldCollection;

    /**
     * The constructor
     * 
     * @param fields
     *        is the unmodifiable collection of the declared fields.
     */
    public DeclaredFieldsIterable(Collection<AbstractContentField> fields) {

      super();
      this.fieldCollection = fields;
    }

    /**
     * {@inheritDoc}
     */
    public int getSize() {

      return this.fieldCollection.size();
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<AbstractContentField> iterator() {

      return this.fieldCollection.iterator();
    }

  }

  /**
   * @see ContentClass#getFields()
   */
  private class FieldsIterable implements SizedIterable<AbstractContentField> {

    /**
     * {@inheritDoc}
     */
    public int getSize() {

      int result = 0;
      Iterator<AbstractContentField> fieldsIterator = AbstractContentClass.this.declaredFields
          .values().iterator();
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
    public Iterator<AbstractContentField> iterator() {

      // TODO Auto-generated method stub
      return null;
    }

  }

}
