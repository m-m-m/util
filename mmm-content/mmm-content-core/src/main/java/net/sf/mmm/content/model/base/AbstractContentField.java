/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.content.model.api.ContentAccessor;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.value.validator.api.ValueValidator;

/**
 * This is the abstract base implementation of the {@link ContentField}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentField extends AbstractContentReflectionObject implements
    ContentField {

  /** @see #getDeclaringClass() */
  private AbstractContentClass declaringClass;

  /** @see #getFieldTypeSpecification() */
  private String fieldTypeSpecification;

  /** @see #getFieldClass() */
  private Class<?> fieldClass;

  /** @see #getFieldType() */
  private Type fieldType;

  /** @see #getModifiers() */
  private FieldModifiers modifiers;

  /** @see #getConstraint() */
  private ValueValidator constraint;

  /** @see #getAccessor() */
  private ContentAccessor accessor;

  /**
   * The constructor.
   */
  public AbstractContentField() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   * @param id is the {@link #getId() ID}.
   */
  public AbstractContentField(String name, SmartId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract AbstractContentClass getParent();

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends AbstractContentField> getChildren() {

    List<AbstractContentField> fields = new ArrayList<AbstractContentField>();
    collectSubFieldsRecursive(getDeclaringClass(), fields);
    return fields;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractContentField getChild(String childName) {

    if (getName().equals(childName)) {
      return findSubFieldRecursive(this.declaringClass);
    }
    return null;
  }

  /**
   * @see #getChildren()
   * 
   * @param contentClass is the current content-class.
   * @param fields is the list where the fields are added.
   */
  private void collectSubFieldsRecursive(AbstractContentClass contentClass,
      List<AbstractContentField> fields) {

    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      AbstractContentField declaredField = subClass.getDeclaredField(getName());
      if (declaredField != null) {
        fields.add(declaredField);
      }
      collectSubFieldsRecursive(subClass, fields);
    }
  }

  /**
   * @see #getChild(String)
   * 
   * @param contentClass is the current content-class.
   * @return the content-field with the given name or <code>null</code> if NOT
   *         found.
   */
  private AbstractContentField findSubFieldRecursive(AbstractContentClass contentClass) {

    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      AbstractContentField declaredField = subClass.getDeclaredField(getName());
      if (declaredField != null) {
        return declaredField;
      }
      findSubFieldRecursive(subClass);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isClass() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @param declaringClass the declaringClass to set
   */
  protected void setDeclaringClass(AbstractContentClass declaringClass) {

    this.declaringClass = declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getInitiallyDefiningClass() {

    ContentClass definingClass = this.declaringClass;
    ContentClass superClass = definingClass.getSuperClass();
    while (superClass != null) {
      if (superClass.getField(getName()) != null) {
        definingClass = superClass;
      }
      superClass = superClass.getSuperClass();
    }
    return definingClass;
  }

  /**
   * {@inheritDoc}
   */
  public String getFieldTypeSpecification() {

    return this.fieldTypeSpecification;
  }

  /**
   * @param fieldTypeSpecification the fieldTypeSpecification to set
   */
  protected void setFieldTypeSpecification(String fieldTypeSpecification) {

    this.fieldTypeSpecification = fieldTypeSpecification;
  }

  /**
   * {@inheritDoc}
   */
  public Type getFieldType() {

    return this.fieldType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getFieldClass() {

    return this.fieldClass;
  }

  /**
   * This method sets the fields {@link #getFieldType() type} and
   * {@link #getFieldClass() class}.
   * 
   * @param type is the fields type.
   */
  protected void setFieldTypeAndClass(Type type) {

    this.fieldType = type;
    this.fieldClass = ReflectionUtil.getInstance().getClass(type, false);
    if (this.fieldTypeSpecification == null) {
      if (type instanceof Class) {
        this.fieldTypeSpecification = ((Class<?>) type).getName();
      } else {
        this.fieldTypeSpecification = type.toString();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public ValueValidator getConstraint() {

    return this.constraint;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FieldModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * This method sets the {@link #getModifiers() modifiers}.
   * 
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(FieldModifiers modifiers) {

    this.modifiers = modifiers;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField getSuperField() {

    AbstractContentField superField = null;
    // ATTENTION: if class-access is NOT set, this can cause infinity-loop!
    AbstractContentClass superClass = getDeclaringClass().getSuperClass();
    if (superClass != null) {
      superField = superClass.getField(getName());
      if (superField == this) {
        superField = null;
      }
    }
    return superField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDeleted() {

    if (getDeletedFlag()) {
      return true;
    } else if (getDeclaringClass().isDeleted()) {
      return true;
    } else {
      ContentField superField = getSuperField();
      if (superField != null) {
        return superField.isDeleted();
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public ContentAccessor getAccessor() {

    return this.accessor;
  }

  /**
   * @param accessor the accessor to set
   */
  protected void setAccessor(ContentAccessor accessor) {

    this.accessor = accessor;
  }

}
