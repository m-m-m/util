/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the abstract base implementation of the {@link DataClass} interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataClass<CLASS extends DataObject> extends
    AbstractDataReflectionObject<CLASS> implements DataClass<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -8444545908272830489L;

  /** the super-class of this class */
  private AbstractDataClass<? super CLASS> superClass;

  /** @see #getModifiers() */
  private DataClassModifiers modifiers;

  /** @see #getJavaClass() */
  private Class<CLASS> javaClass;

  /** the list of direct sub-classes */
  private final List<AbstractDataClass<? extends CLASS>> subClasses;

  /** @see #getSubClasses() */
  private final List<AbstractDataClass<? extends CLASS>> subClassesView;

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

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   */
  public AbstractDataClass(String name) {

    super(name);
    this.subClasses = new ArrayList<AbstractDataClass<? extends CLASS>>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.declaredFields = new HashMap<String, AbstractDataField<CLASS, ?>>();
    this.declaredFieldsView = Collections.unmodifiableCollection(this.declaredFields.values());
    this.fieldsView = new FieldsCollection();
    if (this.superClass != null) {
      if (this.superClass.getModifiers().isFinal()) {
        // TODO: NLS
        throw new DataReflectionException("Can NOT extend final class!");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? extends DataObject> getParent() {

    return getSuperClass();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass<? super CLASS> getSuperClass() {

    return this.superClass;
  }

  /**
   * This method sets the {@link #getSuperClass() super-class}.
   * 
   * @param superClass the super-class to set.
   */
  protected void setSuperClass(AbstractDataClass<? super CLASS> superClass) {

    this.superClass = superClass;
  }

  /**
   * {@inheritDoc}
   */
  public List<? extends DataClass<? extends DataObject>> getChildren() {

    return getSubClasses();
  }

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
  public Collection<AbstractDataField<CLASS, ?>> getDeclaredFields() {

    return this.declaredFieldsView;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField<CLASS, ?> getDeclaredField(String name) {

    return this.declaredFields.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataField<? super CLASS, ?> getField(String name) {

    AbstractDataField<? super CLASS, ?> field = this.declaredFields.get(name);
    if ((field == null) && (this.superClass != null)) {
      field = this.superClass.getField(name);
    }
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractDataField<? extends DataObject, ?>> getFields() {

    return this.fieldsView;
  }

  /**
   * {@inheritDoc}
   */
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
   * {@inheritDoc}
   */
  public List<AbstractDataClass<? extends CLASS>> getSubClasses() {

    return this.subClassesView;
  }

  /**
   * This method gets the class reflecting the closest type of this
   * content-class.
   * 
   * @return the "implementation".
   */
  public Class<CLASS> getJavaClass() {

    return this.javaClass;
  }

  /**
   * This method sets the {@link #getJavaClass() Java-class} of this
   * content-class.
   * 
   * @param javaClass is the class realizing the entity.
   */
  protected void setJavaClass(Class<CLASS> javaClass) {

    this.javaClass = javaClass;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isContentClass() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSubClassOf(DataClass<? extends DataObject> contentClass) {

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
  public boolean isSuperClassOf(DataClass<? extends DataObject> contentClass) {

    return contentClass.isSubClassOf(this);
  }

  /**
   * This method adds a {@link #getSubClasses() sub-class} to this class.<br>
   * It is an idem-potent operation. Therefore it will have no effect if the
   * given <code>subClass</code> is already a {@link #getSubClasses() registered
   * sub-class} of this class.
   * 
   * @param subClass is the sub-class to add.
   * @throws DataReflectionException if the operation fails.
   */
  public void addSubClass(AbstractDataClass<? extends CLASS> subClass)
      throws DataReflectionException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new DataReflectionException("Sub-Class must have this class as super-class!");
    }
    if (subClass.getModifiers().isSystem() && !getModifiers().isSystem()) {
      // TODO: NLS
      throw new DataReflectionException("System-class can NOT extend user-class!");
    }
    if (getModifiers().isFinal()) {
      // TODO: NLS
      throw new DataReflectionException("Can NOT extend final class!");
    }
    // idem-potent operation
    if (!this.subClasses.contains(subClass)) {
      this.subClasses.add(subClass);
    }
  }

  /**
   * This method adds the given <code>field</code> to this class.
   * 
   * @param field is the field to add.
   * @throws DataReflectionException if the field could NOT be added.
   */
  public void addField(AbstractDataField<CLASS, ?> field) throws DataReflectionException {

    if (field.getDeclaringClass() != this) {
      throw new DataReflectionException("Can NOT add field if NOT declared by this class!");
    }
    DataField<CLASS, ?> duplicate = this.declaredFields.get(field.getTitle());
    if (duplicate != field) {
      this.declaredFields.put(field.getTitle(), field);
    } else {
      throw new DuplicateObjectException(field.getTitle());
    }
  }

  /**
   * @see DataClass#getFields()
   */
  private class FieldsCollection extends
      AbstractCollection<AbstractDataField<? extends DataObject, ?>> {

    /**
     * {@inheritDoc}
     * 
     * TODO: This implementation is extremely expensive. Is there a better way
     * to do this?
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
        if ((declaringClass == AbstractDataClass.this)
            || (declaringClass.isSuperClassOf(AbstractDataClass.this))) {
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
