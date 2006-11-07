/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentFieldImpl extends AbstractContentField {

  // public static final ContentFieldImpl FIELD_ID = new
  // ContentFieldImpl(NAME_ID,
  // ContentClassImpl.CLASS_ROOT, Id.class,
  // FieldModifiersImpl.SYSTEM_FINAL_IMMUTABLE, IdImpl.ID_FIELD_ID);

  /** TODO */
  private static final long serialVersionUID = -7652076416516015944L;
  /** @see #getId() */
  private final IdImpl id;

  /**
   * The constructor.
   * 
   * @param fieldName
   * @param declaringClass
   * @param fieldType
   * @param fieldModifiers
   * @param fieldId
   */
  public ContentFieldImpl(String fieldName, ContentClass declaringClass, Class fieldType,
      FieldModifiers fieldModifiers, IdImpl fieldId) {

    super(fieldName, declaringClass, fieldType, fieldModifiers);
    this.id = fieldId;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   */
  public ContentClass getContentClass() {

    return ContentClassImpl.CLASS_FIELD;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getId()
   */
  public Id getId() {

    return this.id;
  }

}
