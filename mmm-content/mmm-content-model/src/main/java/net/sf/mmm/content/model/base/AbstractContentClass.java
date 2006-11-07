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
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.security.PermissionDeniedException;

/**
 * This is the abstract base implementation of the {@link ContentClass}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentClass extends AbstractContentObject implements ContentClass {

  /** the super-class of this class */
  private ContentClass superClass;

  /** the list of direct sub-classes */
  private final List<ContentClass> subClasses;

  /** @see #getSubClasses() */
  private final List<ContentClass> subClassesView;

  /** the map of content fields by name */
  private final Map<String, ContentField> fields;

  /** @see #getDeclatedFields() */
  private final Collection<ContentField> fieldCollection;

  /** @see #getModifiers() */
  private final ClassModifiers modifiers;

  /**
   * The constructor.
   * 
   * @param className
   * @param parentClass
   * @param classModifiers
   */
  public AbstractContentClass(String className, ContentClass parentClass,
      ClassModifiers classModifiers) {

    super(className);
    this.superClass = parentClass;
    this.modifiers = classModifiers;
    this.subClasses = new ArrayList<ContentClass>();
    this.subClassesView = Collections.unmodifiableList(this.subClasses);
    this.fields = new HashMap<String, ContentField>();
    this.fieldCollection = Collections.unmodifiableCollection(this.fields.values());
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getDeclaredFieldCount()
   */
  public int getDeclaredFieldCount() {

    return this.fields.size();
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getDeclatedField(java.lang.String)
   */
  public ContentField getDeclatedField(String name) {

    return this.fields.get(name);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getDeclatedFields()
   */
  public Iterator<ContentField> getDeclatedFields() {

    return this.fieldCollection.iterator();
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getField(java.lang.String)
   */
  public ContentField getField(String name) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getFieldCount()
   */
  public int getFieldCount() {

    int result = 0;
    Iterator<ContentField> declaredFields = this.fields.values().iterator();
    while (declaredFields.hasNext()) {
      ContentField myField = declaredFields.next();
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
   * @see net.sf.mmm.content.model.api.ContentClass#getFields()
   */
  public Iterator<ContentField> getFields() {

    return new ContentFieldIterator(this);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getModifiers()
   */
  public ClassModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getSuperClass()
   */
  public ContentClass getSuperClass() {

    return this.superClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#getSubClasses()
   */
  public List<ContentClass> getSubClasses() {

    return this.subClassesView;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentClass#isSubClassOf(net.sf.mmm.content.model.api.ContentClass)
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
   * @see net.sf.mmm.content.model.api.ContentClass#isSuperClassOf(net.sf.mmm.content.model.api.ContentClass)
   */
  public boolean isSuperClassOf(ContentClass contentClass) {

    return contentClass.isSubClassOf(this);
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObject#isClass()
   */
  public boolean isClass() {

    return true;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObject#isDeletedFlagSet()
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
   * @see net.sf.mmm.content.api.ContentObject#getFieldValue(java.lang.String)
   */
  @Override
  public Object getFieldValue(String fieldName) throws NoSuchFieldException,
      PermissionDeniedException, ContentException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see java.lang.Object#toString()
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
