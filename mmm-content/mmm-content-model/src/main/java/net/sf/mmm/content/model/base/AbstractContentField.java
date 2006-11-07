/* $Id$ */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.FieldModifiers;

/**
 * This is the abstract base implementation of the ContentField interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentField extends AbstractContentObject implements ContentField {

  /** @see #getDeclaringClass() */
  private final ContentClass declaringClass;

  /** @see #getFieldType() */
  private Class type;

  /** @see #getModifiers() */
  private FieldModifiers modifiers;

  /**
   * The constructor.
   * 
   * @see net.sf.mmm.content.base.AbstractContentObject#AbstractContentObject(String)
   * 
   * @param declaringContentClass
   *        is the content-class that {@link #getDeclaringClass() declares} (or
   *        overrides) this field.
   */
  public AbstractContentField(String fieldName, ContentClass declaringContentClass,
      Class fieldType, FieldModifiers fieldModifiers) {

    super(fieldName);
    this.declaringClass = declaringContentClass;
    this.type = fieldType;
    this.modifiers = fieldModifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObject#isClass()
   */
  public boolean isClass() {

    return false;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentField#getDeclaringClass()
   */
  public ContentClass getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentField#getInitiallyDefiningClass()
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
   * @see net.sf.mmm.content.model.api.ContentField#getFieldType()
   */
  public Class getFieldType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentField#getModifiers()
   */
  public FieldModifiers getModifiers() {

    return this.modifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentField#getSuperField()
   */
  public ContentField getSuperField() {

    ContentField superField = null;
    ContentClass superClass = getContentClass().getSuperClass();
    if (superClass != null) {
      superField = superClass.getField(getName());
    }
    return superField;
  }

  /**
   * @see net.sf.mmm.content.base.AbstractContentObject#isDeleted()
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
   * @see net.sf.mmm.content.model.api.ContentReflectionObject#isDeletedFlagSet()
   */
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append("Field:");
    result.append(getName());
    result.append("[");
    if (getModifiers().isFinal()) {
      result.append("F");
    }
    if (getModifiers().isSystem()) {
      result.append("S");
    }
    if (getModifiers().isStatic()) {
      result.append("s");
    }
    if (getModifiers().isImmutable()) {
      result.append("I");
    }
    if (getModifiers().isTransient()) {
      result.append("T");
    }
    if (isDeleted()) {
      result.append("D");
    }
    result.append("]");
    return result.toString();
  }

}
