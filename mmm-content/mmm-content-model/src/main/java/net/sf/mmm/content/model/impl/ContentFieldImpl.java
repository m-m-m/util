/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.value.api.Id;

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
  
  /**
   * The constructor.
   * 
   * @param fieldId
   * @param fieldName
   * @param declaringClass
   * @param fieldType
   * @param fieldModifiers
   */
  public ContentFieldImpl(Id fieldId, String fieldName, ContentClass declaringClass, Class fieldType,
      FieldModifiers fieldModifiers) {

    super(fieldId, fieldName, declaringClass, fieldType, fieldModifiers);
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   */
  public ContentClass getContentClass() {

    return ContentClassImpl.CLASS_FIELD;
  }

}
