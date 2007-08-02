/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.security.api.PermissionDeniedException;
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

  /** UID for serialization. */
  private static final long serialVersionUID = -4670093180071079593L;

  /** @see #getDeclaringClass() */
  private ContentClass declaringClass;

  /** @see #getFieldTypeSpecification() */
  private String fieldTypeSpecification;

  /** @see #getFieldClass() */
  private Class fieldClass;

  /** @see #getFieldType() */
  private Type fieldType;

  /** @see #getModifiers() */
  private FieldModifiers modifiers;

  /** @see #getConstraint() */
  private ValueValidator constraint;

  /**
   * The constructor.
   */
  public AbstractContentField() {

    super();
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
  public ContentClass getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @param declaringClass the declaringClass to set
   */
  protected void setDeclaringClass(ContentClass declaringClass) {

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
    this.fieldClass = ReflectionUtil.toClass(type);
    if (this.fieldTypeSpecification == null) {
      if (type instanceof Class) {
        this.fieldTypeSpecification = ((Class) type).getName();
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
  public ContentField getSuperField() {

    ContentField superField = null;
    ContentClass superClass = getContentClass().getSuperClass();
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

    if (isDeletedFlagSet()) {
      return true;
    } else if (getSuperField() != null) {
      return getSuperField().isDeleted();
    } else {
      return false;
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
    } else if (fieldName.equals(FIELD_NAME_FIELD_CLASS)) {
      return getFieldClass();
    } else if (fieldName.equals(FIELD_NAME_FIELD_TYPE)) {
      return getFieldType();
    } else if (fieldName.equals(FIELD_NAME_DECLARING_CLASS)) {
      return getDeclaringClass();
    } else if (fieldName.equals(FIELD_NAME_INITIALLY_DEFINING_CLASS)) {
      return getInitiallyDefiningClass();
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
      this.modifiers = (FieldModifiers) value;
    } else {
      super.setValue(field, fieldName, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append("Field:");
    result.append(getName());
    result.append("[");
    result.append(getModifiers());
    result.append("]");
    return result.toString();
  }

}
