/* $Id$ */
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
import net.sf.mmm.content.model.api.ClassModifiersIF;
import net.sf.mmm.content.model.api.ContentClassIF;
import net.sf.mmm.content.model.api.ContentFieldIF;
import net.sf.mmm.content.security.PermissionDeniedException;

/**
 * This is the abstract base implementation of the {@link ContentClassIF}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClass extends AbstractContentObject implements ContentClassIF {

  /** the super-class of this class */
  private ContentClassIF superClass;

  /** the list of direct sub-classes */
  private final List<ContentClassIF> subClasses;

  /** @see #getSubClasses() */
  private final List<ContentClassIF> subClassesView;

  /** the map of content fields by name */
  private final Map<String, ContentFieldIF> fields;

  /** @see #getDeclatedFields() */
  private final Collection<ContentFieldIF> fieldCollection;

  /** @see #getModifiers() */
  private final ClassModifiersIF modifiers;

  /**
   * The constructor.
   * 
   * @param className
   * @param parentClass
   * @param classModifiers
   */
  public AbstractContentClass(String className, ContentClassIF parentClass,
      ClassModifiersIF classModifiers) {

    super(className);
    this.superClass = parentClass;
    this.modifiers = classModifiers;
    this.subClasses = new ArrayList<ContentClassIF>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.fields = new HashMap<String, ContentFieldIF>();
    this.fieldCollection = Collections.unmodifiableCollection(this.fields.values());
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getDeclaredFieldCount()
   */
  public int getDeclaredFieldCount() {

    return this.fields.size();
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getDeclatedField(java.lang.String)
   */
  public ContentFieldIF getDeclatedField(String name) {

    return this.fields.get(name);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getDeclatedFields()
   */
  public Iterator<ContentFieldIF> getDeclatedFields() {

    return this.fieldCollection.iterator();
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getField(java.lang.String)
   */
  public ContentFieldIF getField(String name) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getFieldCount()
   */
  public int getFieldCount() {

    int result = 0;
    Iterator<ContentFieldIF> declaredFields = this.fields.values().iterator();
    while (declaredFields.hasNext()) {
      ContentFieldIF myField = declaredFields.next();
      if (myField.getSuperField() == null) {
        result++;
      }
    }
    // the calcuation until here should be stored as member.
    // the only problem is that the value may change if a super-class
    // changes.
    if (this.superClass != null) {
      result += this.superClass.getFieldCount();
    }
    return result;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getFields()
   */
  public Iterator<ContentFieldIF> getFields() {

    return new ContentFieldIterator(this);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getModifiers()
   */
  public ClassModifiersIF getModifiers() {

    return this.modifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getSuperClass()
   */
  public ContentClassIF getSuperClass() {

    return this.superClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#getSubClasses()
   */
  public List<ContentClassIF> getSubClasses() {

    return this.subClassesView;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClassIF#isSubClassOf(net.sf.mmm.content.model.api.ContentClassIF)
   */
  public boolean isSubClassOf(ContentClassIF contentClass) {

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
   * @see net.sf.mmm.content.model.api.ContentClassIF#isSuperClassOf(net.sf.mmm.content.model.api.ContentClassIF)
   */
  public boolean isSuperClassOf(ContentClassIF contentClass) {

    return contentClass.isSubClassOf(this);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObjectIF#isClass()
   */
  public boolean isClass() {

    return true;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObjectIF#isDeletedFlagSet()
   */
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
  }

  /**
   * @see net.sf.mmm.content.base.AbstractContentObject#isDeleted()
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
   * @see net.sf.mmm.content.api.ContentObjectIF#getFieldValue(java.lang.String)
   */
  @Override
  public Object getFieldValue(String fieldName) throws NoSuchFieldException,
      PermissionDeniedException, ContentException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see java.lang.Object#toString()
   * 
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append("Class:");
    result.append(getId());
    result.append("[");
    if (getModifiers().isAbstract()) {
      result.append("A");
    }
    if (getModifiers().isFinal()) {
      result.append("F");
    }
    if (getModifiers().isSystem()) {
      result.append("S");
    }
    if (isDeleted()) {
      result.append("D");
    }
    result.append("]");
    return result.toString();
  }

}
