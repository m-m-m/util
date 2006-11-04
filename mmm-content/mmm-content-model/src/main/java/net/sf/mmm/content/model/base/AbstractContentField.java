/* $Id$ */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClassIF;
import net.sf.mmm.content.model.api.ContentFieldIF;
import net.sf.mmm.content.model.api.FieldModifiersIF;

/**
 * This is the abstract base implementation of the ContentFieldIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentField extends AbstractContentObject implements ContentFieldIF {

  /** @see #getDeclaringClass() */
  private final ContentClassIF declaringClass;

  /** @see #getFieldType() */
  private Class type;

  /** @see #getModifiers() */
  private FieldModifiersIF modifiers;

  /**
   * The constructor.
   * 
   * @see net.sf.mmm.content.base.AbstractContentObject#AbstractContentObject(String)
   * 
   * @param declaringContentClass
   *        is the content-class that {@link #getDeclaringClass() declares} (or
   *        overrides) this field.
   */
  public AbstractContentField(String fieldName, ContentClassIF declaringContentClass,
      Class fieldType, FieldModifiersIF fieldModifiers) {

    super(fieldName);
    this.declaringClass = declaringContentClass;
    this.type = fieldType;
    this.modifiers = fieldModifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObjectIF#isClass()
   */
  public boolean isClass() {

    return false;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentFieldIF#getDeclaringClass()
   */
  public ContentClassIF getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentFieldIF#getInitiallyDefiningClass()
   */
  public ContentClassIF getInitiallyDefiningClass() {

    ContentClassIF definingClass = this.declaringClass;
    ContentClassIF superClass = definingClass.getSuperClass();
    while (superClass != null) {
      if (superClass.getField(getName()) != null) {
        definingClass = superClass;
      }
      superClass = superClass.getSuperClass();
    }
    return definingClass;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentFieldIF#getFieldType()
   */
  public Class getFieldType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentFieldIF#getModifiers()
   */
  public FieldModifiersIF getModifiers() {

    return this.modifiers;
  }

  /**
   * @see net.sf.mmm.content.model.api.ContentFieldIF#getSuperField()
   */
  public ContentFieldIF getSuperField() {

    ContentFieldIF superField = null;
    ContentClassIF superClass = getContentClass().getSuperClass();
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
   * @see net.sf.mmm.content.model.api.ContentReflectionObjectIF#isDeletedFlagSet()
   */
  public boolean isDeletedFlagSet() {

    return getDeletedFlag();
  }

  /**
   * @see java.lang.Object#toString()
   * 
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
