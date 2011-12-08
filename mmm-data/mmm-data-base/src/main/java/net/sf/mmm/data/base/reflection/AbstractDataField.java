/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.access.DataFieldAccessor;
import net.sf.mmm.util.nls.api.ReadOnlyException;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of the {@link DataField} interface.
 * 
 * @param <FIELD> is the generic type of the
 *        {@link #getFieldValue(DataObjectView) value} reflected by this field.
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataField<CLASS extends DataObjectView, FIELD> extends
    AbstractDataReflectionObject<CLASS> implements DataField<CLASS, FIELD> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8457521410109028533L;

  /** @see #getDeclaringClass() */
  private AbstractDataClass<CLASS> declaringClass;

  /** @see #getFieldType() */
  private GenericType<FIELD> fieldType;

  /** @see #getModifiers() */
  private DataFieldModifiers modifiers;

  // /** @see #getConstraint() */
  // private ValueValidator<FIELD> constraint;

  /** @see #getAccessor() */
  private DataFieldAccessor<CLASS, FIELD> accessor;

  /**
   * The constructor.
   */
  public AbstractDataField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public long getDataClassId() {

    return DataField.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public Class<CLASS> getJavaClass() {

    return this.declaringClass.getJavaClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataClass<CLASS> getParent() {

    return this.declaringClass;
  }

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

  // /**
  // * {@inheritDoc}
  // */
  // public AbstractDataField<? extends DataObject, ?> getChild(String
  // childName) {
  //
  // if (getTitle().equals(childName)) {
  // return findSubFieldRecursive(this.declaringClass);
  // }
  // return null;
  // }

  // /**
  // * @see #getChildren()
  // *
  // * @param contentClass is the current content-class.
  // * @param fields is the list where the fields are added.
  // */
  // private void collectSubFieldsRecursive(AbstractDataClass<?> contentClass,
  // List<AbstractDataField<?, ?>> fields) {
  //
  // for (AbstractDataClass<?> subClass : contentClass.getSubClasses()) {
  // AbstractDataField<?, ?> declaredField =
  // subClass.getDeclaredField(getTitle());
  // if (declaredField != null) {
  // fields.add(declaredField);
  // }
  // collectSubFieldsRecursive(subClass, fields);
  // }
  // }

  // /**
  // * @see #getChild(String)
  // *
  // * @param contentClass is the current content-class.
  // * @return the content-field with the given name or <code>null</code> if NOT
  // * found.
  // */
  // private AbstractDataField<? extends DataObject, ?> findSubFieldRecursive(
  // AbstractDataClass<? extends DataObject> contentClass) {
  //
  // for (AbstractDataClass<? extends DataObject> subClass :
  // contentClass.getSubClasses()) {
  // AbstractDataField<? extends DataObject, ?> field =
  // subClass.getDeclaredField(getTitle());
  // if (field == null) {
  // field = findSubFieldRecursive(subClass);
  // }
  // if (field != null) {
  // return field;
  // }
  // }
  // return null;
  // }

  /**
   * {@inheritDoc}
   */
  public boolean isDataClass() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<CLASS> getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @param declaringClass the declaringClass to set
   */
  protected void setDeclaringClass(AbstractDataClass<CLASS> declaringClass) {

    this.declaringClass = declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  public DataClass<? extends DataObjectView> getInitiallyDefiningClass() {

    DataClass<? extends DataObjectView> definingClass = this.declaringClass;
    DataClass<? extends DataObjectView> superClass = definingClass.getSuperClass();
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
   * This method sets the fields {@link #getFieldType() type}.
   * 
   * @param type is the fields type.
   */
  protected void setFieldType(GenericType<FIELD> type) {

    this.fieldType = type;
  }

  // /**
  // * {@inheritDoc}
  // */
  // public ValueValidator getConstraint() {
  //
  // return this.constraint;
  // }

  /**
   * {@inheritDoc}
   */
  public DataFieldModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * This method sets the {@link #getModifiers() modifiers}.
   * 
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(DataFieldModifiers modifiers) {

    this.modifiers = modifiers;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public AbstractDataField<? extends DataObjectView, ? super FIELD> getSuperField() {

    AbstractDataField<? extends DataObjectView, ?> superField = null;
    AbstractDataClass<? extends DataObjectView> superClass = getDeclaringClass().getSuperClass();
    if (superClass != null) {
      superField = superClass.getField(getTitle());
      if (superField == this) {
        superField = null;
      }
    }
    return (AbstractDataField<? extends DataObjectView, ? super FIELD>) superField;
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
      DataField<? extends DataObjectView, ?> superField = getSuperField();
      if (superField != null) {
        return superField.isDeleted();
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public FIELD getFieldValue(CLASS object) {

    // TODO: security?
    return this.accessor.getFieldValue(object);
  }

  /**
   * {@inheritDoc}
   */
  public void setFieldValue(CLASS object, FIELD value) {

    if (this.modifiers.isReadOnly()) {
      throw new ReadOnlyException(this.declaringClass.getTitle() + "." + getTitle());
    }
    // TODO: security?
    this.accessor.setFieldValue(object, value);
  }

  /**
   * This method gets the {@link DataFieldAccessor} this field delegates to.
   * 
   * @return the {@link DataFieldAccessor}.
   */
  public DataFieldAccessor<CLASS, FIELD> getAccessor() {

    return this.accessor;
  }

  /**
   * @param accessor the accessor to set
   */
  protected void setAccessor(DataFieldAccessor<CLASS, FIELD> accessor) {

    this.accessor = accessor;
  }

}
