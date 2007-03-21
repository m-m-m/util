/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.lang.reflect.Type;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.validator.api.ValueValidator;
import net.sf.mmm.content.validator.impl.ValueTypeValidator;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the abstract base implementation of the ContentField interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentField extends AbstractContentObject implements ContentField {

  /** @see #getDeclaringClass() */
  private final ContentClass declaringClass;

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
   * 
   * @see net.sf.mmm.content.base.AbstractContentObject#AbstractContentObject(IdImpl,
   *      String)
   * 
   * @param declaringContentClass
   *        is the content-class that {@link #getDeclaringClass() declares} (or
   *        overrides) this field.
   * @param type
   *        is the {@link #getFieldType() field-type}.
   * @param fieldModifiers
   *        are the {@link #getModifiers() modifiers}.
   */
  public AbstractContentField(IdImpl fieldId, String fieldName, ContentClass declaringContentClass,
      Type type, FieldModifiers fieldModifiers) {

    // TODO
    this(fieldId, fieldName, declaringContentClass, type, fieldModifiers, new ValueTypeValidator(
        ReflectionUtil.toClass(type)));
  }

  /**
   * The constructor.
   * 
   * @see net.sf.mmm.content.base.AbstractContentObject#AbstractContentObject(IdImpl,
   *      String)
   * 
   * @param declaringContentClass
   *        is the content-class that {@link #getDeclaringClass() declares} (or
   *        overrides) this field.
   * @param type
   *        is the {@link #getFieldType() field-type}.
   * @param fieldModifiers
   *        are the {@link #getModifiers() modifiers}.
   * @param validator
   *        is the {@link #getConstraint() constraint}.
   */
  public AbstractContentField(IdImpl fieldId, String fieldName, ContentClass declaringContentClass,
      Type type, FieldModifiers fieldModifiers, ValueValidator validator) {

    super(fieldId, fieldName);
    assert (fieldId.getObjectId() == IdImpl.OID_FIELD);
    this.declaringClass = declaringContentClass;
    this.fieldType = type;
    this.fieldClass = ReflectionUtil.toClass(type);
    this.modifiers = fieldModifiers;
    this.constraint = validator;
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
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
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
  protected void setFieldValue(ContentField field, String fieldName, Object value)
      throws PermissionDeniedException, ContentException {

    if (fieldName.equals(FIELD_NAME_MODIFIERS)) {
      this.modifiers = (FieldModifiers) value;
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
