/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassCachingStrategy;
import net.sf.mmm.data.api.reflection.DataClassGroupVersion;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ReadOnlyException;

/**
 * This is the abstract base implementation of the {@link DataClass} interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass() class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataClass<CLASS extends DataObject> extends AbstractDataReflectionObject<CLASS> implements
    DataClass<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -8444545908272830489L;

  /** the super-class of this class */
  private AbstractDataClass<? extends DataObject> superClass;

  /** @see #getModifiers() */
  private DataClassModifiers modifiers;

  /** @see #getJavaClass() */
  private Class<CLASS> javaClass;

  /** @see #getGroupVersion() */
  private DataClassGroupVersion groupVersion;

  /** @see #getCachingStrategy() */
  private DataClassCachingStrategy cachingStrategy;

  /** @see #getDeclaredFields() (map of content-fields by name) */
  private final Map<String, AbstractDataField<CLASS, ?>> declaredFields;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractDataField<CLASS, ?>> declaredFieldsView;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractDataField<? extends DataObject, ?>> fieldsView;

  // /** @see #isRevisionControlled() */
  // private boolean revisionControlled;

  /**
   * The constructor.
   */
  public AbstractDataClass() {

    super();
    this.declaredFields = new HashMap<String, AbstractDataField<CLASS, ?>>();
    this.declaredFieldsView = Collections.unmodifiableCollection(this.declaredFields.values());
    this.fieldsView = new FieldsCollection();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getDataClassId() {

    return DataClass.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public AbstractDataClass<? extends DataObject> getParent() {

    return getSuperClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataClass<? extends DataObject> getSuperClass() {

    return this.superClass;
  }

  /**
   * This method sets the {@link #getSuperClass() super-class}.
   * 
   * @param superClass the super-class to set.
   */
  protected void setSuperClass(AbstractDataClass<? extends DataObject> superClass) {

    this.superClass = superClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(DataClass<? extends DataObject> parent) {

    throw new ReadOnlyException(this, "parent");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends DataClass<? extends DataObject>> getChildren() {

    return getSubClasses();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract List<? extends AbstractDataClass<? extends CLASS>> getSubClasses();

  // /**
  // * {@inheritDoc}
  // */
  // public boolean isRevisionControlled() {
  //
  // return this.revisionControlled;
  // }

  // /**
  // * @param revisionControlled the revisionControlled to set
  // */
  // public void setRevisionControlled(boolean revisionControlled) {
  //
  // this.revisionControlled = revisionControlled;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataClassGroupVersion getGroupVersion() {

    return this.groupVersion;
  }

  /**
   * @param groupVersion is the groupVersion to set
   */
  protected void setGroupVersion(DataClassGroupVersion groupVersion) {

    this.groupVersion = groupVersion;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataClassCachingStrategy getCachingStrategy() {

    return this.cachingStrategy;
  }

  /**
   * @param cachingStrategy is the cachingStrategy to set
   */
  protected void setCachingStrategy(DataClassCachingStrategy cachingStrategy) {

    this.cachingStrategy = cachingStrategy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<AbstractDataField<CLASS, ?>> getDeclaredFields() {

    return this.declaredFieldsView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataField<CLASS, ?> getDeclaredField(String title) {

    return this.declaredFields.get(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataField<? extends DataObject, ?> getField(String title) {

    AbstractDataField<? extends DataObject, ?> field = this.declaredFields.get(title);
    if ((field == null) && (this.superClass != null)) {
      field = this.superClass.getField(title);
    }
    return field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<AbstractDataField<? extends DataObject, ?>> getFields() {

    return this.fieldsView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataClassModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(DataClassModifiers modifiers) {

    this.modifiers = modifiers;
  }

  /**
   * This method gets the class reflecting the closest type of this content-class.
   * 
   * @return the "implementation".
   */
  @Override
  public Class<CLASS> getJavaClass() {

    return this.javaClass;
  }

  /**
   * This method sets the {@link #getJavaClass() Java-class} of this content-class.
   * 
   * @param javaClass is the class realizing the entity.
   */
  protected void setJavaClass(Class<CLASS> javaClass) {

    this.javaClass = javaClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDataClass() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelectable() {

    return this.modifiers.isAbstract();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSubClassOf(DataClass<? extends DataObject> dataClass) {

    if (this.superClass == null) {
      // root-class can NOT be a sub-class
      return false;
    } else if (this.superClass == dataClass) {
      // given class is direct super-class
      return true;
    } else {
      // check recursive
      return this.superClass.isSubClassOf(dataClass);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSuperClassOf(DataClass<? extends DataObject> dataClass) {

    return dataClass.isSubClassOf(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAncestor(DataClass<? extends DataObject> dataClass) {

    return isSuperClassOf(dataClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDescendant(DataClass<? extends DataObject> dataClass) {

    return isSubClassOf(dataClass);
  }

  /**
   * This method adds a {@link #getSubClasses() sub-class} to this class.<br>
   * It is an idempotent operation. Therefore it will have no effect if the given <code>subClass</code> is
   * already a {@link #getSubClasses() registered sub-class} of this class.
   * 
   * @param subClass is the sub-class to add.
   * @throws DataReflectionException if the operation fails.
   */
  protected abstract void addSubClass(AbstractDataClass<? extends CLASS> subClass) throws DataReflectionException;

  /**
   * This method adds the given <code>field</code> to this class.
   * 
   * @param field is the field to add.
   * @throws DataReflectionException if the field could NOT be added.
   */
  protected void addDeclaredField(AbstractDataField<CLASS, ?> field) throws DataReflectionException {

    NlsNullPointerException.checkNotNull(DataField.class, field);
    if (field.getDeclaringClass() != this) {
      // TODO: I18N
      throw new DataReflectionException("Can NOT add field if NOT declared by this class!");
    }
    DataField<CLASS, ?> duplicate = this.declaredFields.get(field.getTitle());
    if (duplicate == null) {
      this.declaredFields.put(field.getTitle(), field);
    } else if (!field.equals(duplicate)) {
      throw new DuplicateObjectException(field.getTitle());
    }
  }

  /**
   * @see DataClass#getFields()
   */
  private class FieldsCollection extends AbstractCollection<AbstractDataField<? extends DataObject, ?>> {

    /**
     * {@inheritDoc}
     * 
     * TODO: This implementation is extremely expensive. Is there a better way to do this?
     */
    @Override
    public int size() {

      int result = 0;
      for (DataField<CLASS, ?> field : AbstractDataClass.this.declaredFields.values()) {
        if (field.getSuperField() == null) {
          result++;
        }
      }
      // the calculation until here should be stored as member.
      // the only problem is that the value may change if a super-class
      // changes.
      if (AbstractDataClass.this.superClass != null) {
        result += AbstractDataClass.this.superClass.getFields().size();
      }
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<AbstractDataField<? extends DataObject, ?>> iterator() {

      return new DataFieldIterator(AbstractDataClass.this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {

      if (o instanceof DataField) {
        DataField<? extends DataObject, ?> field = (DataField<? extends DataObject, ?>) o;
        DataClass<? extends DataObject> declaringClass = field.getDeclaringClass();
        if ((declaringClass == AbstractDataClass.this) || (declaringClass.isSuperClassOf(AbstractDataClass.this))) {
          return true;
        }
      }
      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(AbstractDataField<? extends DataObject, ?> e) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends AbstractDataField<? extends DataObject, ?>> c) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {

      throw new UnsupportedOperationException();
    }

  }

}
