/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.base;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.reflection.api.ContentClassModifiers;
import net.sf.mmm.data.reflection.api.ContentField;
import net.sf.mmm.data.reflection.api.ContentReflectionException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This is the abstract base implementation of the {@link ContentClass}
 * interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentClass<CLASS extends ContentObject> extends
    AbstractContentReflectionObject<CLASS> implements ContentClass<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = -8444545908272830489L;

  /** the super-class of this class */
  private AbstractContentClass<? super CLASS> superClass;

  /** @see #getContentModifiers() */
  private ContentClassModifiers modifiers;

  /** @see #getJavaClass() */
  private Class<CLASS> javaClass;

  /** the list of direct sub-classes */
  private final List<AbstractContentClass<? extends CLASS>> subClasses;

  /** @see #getSubClasses() */
  private final List<AbstractContentClass<? extends CLASS>> subClassesView;

  /** @see #getDeclaredFields() (map of content-fields by name) */
  private final Map<String, AbstractContentField<CLASS, ?>> declaredFields;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractContentField<CLASS, ?>> declaredFieldsView;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractContentField<? super CLASS, ?>> fieldsView;

  /** @see #isRevisionControlled() */
  private boolean revisionControlled;

  /**
   * The constructor.
   */
  public AbstractContentClass() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   */
  public AbstractContentClass(String name) {

    super(name);
    this.subClasses = new ArrayList<AbstractContentClass<? extends CLASS>>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.declaredFields = new HashMap<String, AbstractContentField<CLASS, ?>>();
    this.declaredFieldsView = Collections.unmodifiableCollection(this.declaredFields.values());
    this.fieldsView = new FieldsCollection();
    if (this.superClass != null) {
      if (this.superClass.getContentModifiers().isFinal()) {
        // TODO: NLS
        throw new ContentReflectionException("Can NOT extend final class!");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass<? extends ContentObject> getParent() {

    return getSuperClass();
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass<? super CLASS> getSuperClass() {

    return this.superClass;
  }

  /**
   * This method sets the {@link #getSuperClass() super-class}.
   * 
   * @param superClass the super-class to set.
   */
  protected void setSuperClass(AbstractContentClass superClass) {

    this.superClass = superClass;
  }

  /**
   * {@inheritDoc}
   */
  public List<? extends ContentClass<? extends ContentObject>> getChildren() {

    return getSubClasses();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRevisionControlled() {

    return this.revisionControlled;
  }

  /**
   * @param revisionControlled the revisionControlled to set
   */
  public void setRevisionControlled(boolean revisionControlled) {

    this.revisionControlled = revisionControlled;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractContentField<CLASS, ?>> getDeclaredFields() {

    return this.declaredFieldsView;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField<CLASS, ?> getDeclaredField(String name) {

    return this.declaredFields.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField<? super CLASS, ?> getField(String name) {

    AbstractContentField<? super CLASS, ?> field = this.declaredFields.get(name);
    if ((field == null) && (this.superClass != null)) {
      field = this.superClass.getField(name);
    }
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractContentField<? super CLASS, ?>> getFields() {

    return this.fieldsView;
  }

  /**
   * {@inheritDoc}
   */
  public ContentClassModifiers getContentModifiers() {

    return this.modifiers;
  }

  /**
   * @param modifiers the modifiers to set
   */
  protected void setModifiers(ContentClassModifiers modifiers) {

    this.modifiers = modifiers;
  }

  /**
   * {@inheritDoc}
   */
  public List<AbstractContentClass<? extends CLASS>> getSubClasses() {

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
   * This method adds a {@link #getSubClasses() sub-class} to this class.<br>
   * It is an idem-potent operation. Therefore it will have no effect if the
   * given <code>subClass</code> is already a {@link #getSubClasses() registered
   * sub-class} of this class.
   * 
   * @param subClass is the sub-class to add.
   * @throws ContentReflectionException if the operation fails.
   */
  public void addSubClass(AbstractContentClass subClass) throws ContentReflectionException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new ContentReflectionException("Sub-Class must have this class as super-class!");
    }
    if (subClass.getContentModifiers().isSystem() && !getContentModifiers().isSystem()) {
      // TODO: NLS
      throw new ContentReflectionException("System-class can NOT extend user-class!");
    }
    if (getContentModifiers().isFinal()) {
      // TODO: NLS
      throw new ContentReflectionException("Can NOT extend final class!");
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
   * @throws ContentReflectionException if the field could NOT be added.
   */
  public void addField(AbstractContentField field) throws ContentReflectionException {

    if (field.getDeclaringClass() != this) {
      throw new ContentReflectionException("Can NOT add field if NOT declared by this class!");
    }
    ContentField duplicate = this.declaredFields.get(field.getTitle());
    if (duplicate != field) {
      this.declaredFields.put(field.getTitle(), field);
    } else {
      throw new DuplicateObjectException(field.getTitle());
    }
  }

  /**
   * @see ContentClass#getFields()
   */
  private class FieldsCollection extends AbstractCollection<AbstractContentField<? super CLASS, ?>> {

    /**
     * {@inheritDoc}
     * 
     * TODO: This implementation is extremely expensive. Is there a better way
     * to do this?
     */
    @Override
    public int size() {

      int result = 0;
      for (ContentField<CLASS, ?> field : AbstractContentClass.this.declaredFields.values()) {
        if (field.getSuperField() == null) {
          result++;
        }
      }
      // the calculation until here should be stored as member.
      // the only problem is that the value may change if a super-class
      // changes.
      if (AbstractContentClass.this.superClass != null) {
        result += AbstractContentClass.this.superClass.getFields().size();
      }
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<AbstractContentField<? super CLASS, ?>> iterator() {

      return new ContentFieldIterator(AbstractContentClass.this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {

      if (o instanceof ContentField) {
        ContentField field = (ContentField) o;
        ContentClass declaringClass = field.getDeclaringClass();
        if ((declaringClass == AbstractContentClass.this)
            || (declaringClass.isSuperClassOf(AbstractContentClass.this))) {
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
    public boolean add(AbstractContentField e) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public boolean addAll(Collection<? extends AbstractContentField<? super CLASS, ?>> c) {

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
