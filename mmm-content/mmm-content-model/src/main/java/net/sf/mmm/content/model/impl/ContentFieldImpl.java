/* $Id$ */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ContentClassIF;
import net.sf.mmm.content.model.api.FieldModifiersIF;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.value.api.IdIF;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentFieldIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentFieldImpl extends AbstractContentField {

  // public static final ContentFieldImpl FIELD_ID = new
  // ContentFieldImpl(NAME_ID,
  // ContentClassImpl.CLASS_ROOT, IdIF.class,
  // FieldModifiers.SYSTEM_FINAL_IMMUTABLE, IdImpl.ID_FIELD_ID);

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
  public ContentFieldImpl(String fieldName, ContentClassIF declaringClass, Class fieldType,
      FieldModifiersIF fieldModifiers, IdImpl fieldId) {

    super(fieldName, declaringClass, fieldType, fieldModifiers);
    this.id = fieldId;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getContentClass()
   */
  public ContentClassIF getContentClass() {

    return ContentClassImpl.CLASS_FIELD;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getId()
   */
  public IdIF getId() {

    return this.id;
  }

}
