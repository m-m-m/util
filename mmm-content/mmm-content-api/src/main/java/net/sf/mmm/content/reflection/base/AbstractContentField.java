/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.util.List;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.api.access.ContentFieldAccessor;
import net.sf.mmm.content.reflection.base.statically.AbstractContentReflectionObject;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueValidator;

/**
 * This is the abstract base implementation of the {@link ContentField}
 * interface.
 * 
 * @param <FIELD> is the generic type of the value reflected by this field.
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentField<CLASS extends ContentObject, FIELD> extends
    AbstractContentReflectionObject<CLASS> implements ContentField<CLASS, FIELD> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8457521410109028533L;

  /** @see #getDeclaringClass() */
  private AbstractContentClass<CLASS> declaringClass;

  /** @see #getFieldType() */
  private GenericType<FIELD> fieldType;

  /** @see #getContentModifiers() */
  private ContentFieldModifiers modifiers;

  /** @see #getConstraint() */
  private ValueValidator constraint;

  /** @see #getAccessor() */
  private ContentFieldAccessor accessor;

  /**
   * The constructor.
   */
  public AbstractContentField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public abstract AbstractContentClass getParent();

  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public List<? extends AbstractContentField> getChildren() {
  //
  // List<AbstractContentField<?, ?>> fields = new
  // ArrayList<AbstractContentField<?, ?>>();
  // collectSubFieldsRecursive(getDeclaringClass(), fields);
  // return fields;
  // }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField getChild(String childName) {

    if (getTitle().equals(childName)) {
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
  private void collectSubFieldsRecursive(AbstractContentClass<?> contentClass,
      List<AbstractContentField<?, ?>> fields) {

    for (AbstractContentClass<?> subClass : contentClass.getSubClasses()) {
      AbstractContentField<?, ?> declaredField = subClass.getDeclaredField(getTitle());
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
  private AbstractContentField<?, ?> findSubFieldRecursive(AbstractContentClass<?> contentClass) {

    for (AbstractContentClass<?> subClass : contentClass.getSubClasses()) {
      AbstractContentField<?, ?> declaredField = subClass.getDeclaredField(getTitle());
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
  public boolean isContentClass() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  /**
   * {@inheritDoc}
   */
  public AbstractContentClass<CLASS> getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @param declaringClass the declaringClass to set
   */
  protected void setDeclaringClass(AbstractContentClass<CLASS> declaringClass) {

    this.declaringClass = declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getInitiallyDefiningClass() {

    ContentClass definingClass = this.declaringClass;
    ContentClass superClass = definingClass.getSuperClass();
    while (superClass != null) {
      if (superClass.getField(getTitle()) != null) {
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

    return this.fieldType.toString();
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<FIELD> getFieldType() {

    return this.fieldType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getFieldClass() {

    return this.fieldType.getRetrievalClass();
  }

  /**
   * This method sets the fields {@link #getFieldType() type} and
   * {@link #getFieldClass() class}.
   * 
   * @param type is the fields type.
   */
  protected void setFieldTypeAndClass(GenericType<FIELD> type) {

    this.fieldType = type;
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
  public ContentFieldModifiers getContentModifiers() {

    return this.modifiers;
  }

  /**
   * This method sets the {@link #getContentModifiers() modifiers}.
   * 
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(ContentFieldModifiers modifiers) {

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
      superField = superClass.getField(getTitle());
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
  public ContentFieldAccessor getAccessor() {

    return this.accessor;
  }

  /**
   * @param accessor the accessor to set
   */
  protected void setAccessor(ContentFieldAccessor accessor) {

    this.accessor = accessor;
  }

}
