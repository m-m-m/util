/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;

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
  private AbstractContentClass superClass;

  /** @see #getModifiers() */
  private ClassModifiers modifiers;

  /** @see #getJavaClass() */
  private Class<? extends ContentObject> javaClass;

  /** the list of direct sub-classes */
  private final List<AbstractContentClass> subClasses;

  /** @see #getSubClasses() */
  private final List<AbstractContentClass> subClassesView;

  /** @see #getDeclaredFields() (map of content-fields by name) */
  private final Map<String, AbstractContentField> declaredFields;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractContentField> declaredFieldsView;

  /** @see #getDeclaredFields() */
  private final Collection<AbstractContentField> fieldsView;

  /**
   * The constructor.
   */
  public AbstractContentClass() {

    super();
    this.subClasses = new ArrayList<AbstractContentClass>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.declaredFields = new HashMap<String, AbstractContentField>();
    this.declaredFieldsView = Collections.unmodifiableCollection(this.declaredFields.values());
    this.fieldsView = new FieldsCollection();
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
  protected void setSuperClass(AbstractContentClass superClass) {

    this.superClass = superClass;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractContentField> getDeclaredFields() {

    return this.declaredFieldsView;
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
  public Collection<AbstractContentField> getFields() {

    return this.fieldsView;
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
  public AbstractContentClass getSuperClass() {

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
   * This method adds a {@link #getSubClasses() sub-class} to this class.<br>
   * It is an idem-potent operation. Therefore it will have no effect if the
   * given <code>subClass</code> is already a
   * {@link #getSubClasses() registered sub-class} of this class.
   * 
   * @param subClass is the sub-class to add.
   * @throws ContentModelException if the operation fails.
   */
  public void addSubClass(AbstractContentClass subClass) throws ContentModelException {

    if (subClass.getSuperClass() != this) {
      // TODO: NLS
      throw new ContentModelException("Sub-Class must have this class as super-class!");
    }
    if (subClass.getModifiers().isSystem() && !getModifiers().isSystem()) {
      // TODO: NLS
      throw new ContentModelException("System-class can NOT extend user-class!");
    }
    if (getModifiers().isFinal()) {
      // TODO: NLS
      throw new ContentModelException("Can NOT extend final class!");
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
   * @throws ContentModelException if the field could NOT be added.
   */
  public void addField(AbstractContentField field) throws ContentModelException {

    if (field.getDeclaringClass() != this) {
      throw new ContentModelException("Can NOT add field if NOT declared by this class!");
    }
    ContentField duplicate = this.declaredFields.get(field.getName());
    if (duplicate != field) {
      this.declaredFields.put(field.getName(), field);
    } else if (duplicate != null) {
      throw new DuplicateFieldException(field.getName());
    }
  }

  /**
   * This method gets the class reflecting the closest type of this
   * content-class.
   * 
   * @return the "implementation".
   */
  public Class<? extends ContentObject> getJavaClass() {

    return this.javaClass;
  }

  /**
   * This method sets the {@link #getJavaClass() Java-class} of this
   * content-class.
   * 
   * @param javaClass is the class realizing the entity.
   */
  protected void setJavaClass(Class<? extends ContentObject> javaClass) {

    this.javaClass = javaClass;
  }

  /**
   * @see ContentClass#getFields()
   */
  private class FieldsCollection extends AbstractCollection<AbstractContentField> {

    /**
     * {@inheritDoc}
     * 
     * TODO: This implementation is extremely expensive. Is there a better way
     * to do this?
     */
    public int size() {

      int result = 0;
      for (ContentField field : AbstractContentClass.this.declaredFields.values()) {
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
    public Iterator<AbstractContentField> iterator() {

      return new ContentFieldIterator(AbstractContentClass.this);
    }

    /**
     * {@inheritDoc}
     */
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
    public boolean isEmpty() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean add(AbstractContentField e) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends AbstractContentField> c) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(Object o) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeAll(Collection<?> c) {

      throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public boolean retainAll(Collection<?> c) {

      throw new UnsupportedOperationException();
    }

  }

}
