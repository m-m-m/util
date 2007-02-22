/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.validator.api.ValueValidator;
import net.sf.mmm.content.value.api.Id;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentFieldImpl extends AbstractContentField {

  /** UID for serialization */
  private static final long serialVersionUID = -7652076416516015944L;

  /**
   * The constructor.
   * 
   * @see AbstractContentField#AbstractContentField(Id, String, ContentClass,
   *      Class, FieldModifiers)
   */
  public ContentFieldImpl(Id fieldId, String fieldName, ContentClass declaringClass,
      Class fieldType, FieldModifiers fieldModifiers) {

    super(fieldId, fieldName, declaringClass, fieldType, fieldModifiers);
  }

  /**
   * The constructor.
   * 
   * @see AbstractContentField#AbstractContentField(Id, String, ContentClass,
   *      Class, FieldModifiers, ValueValidator)
   */
  public ContentFieldImpl(Id fieldId, String fieldName, ContentClass declaringContentClass,
      Class fieldType, FieldModifiers fieldModifiers, ValueValidator validator) {

    super(fieldId, fieldName, declaringContentClass, fieldType, fieldModifiers, validator);
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   */
  public ContentClass getContentClass() {

    return ContentClassImpl.CLASS_FIELD;
  }

}
