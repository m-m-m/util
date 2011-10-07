/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.impl.statically;

import net.sf.mmm.content.api.ContentClassAnnotation;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.base.AbstractContentClass;
import net.sf.mmm.content.reflection.base.AbstractContentField;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.reflection.api.ContentField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentField.CLASS_ID, name = ContentField.CLASS_NAME)
public final class ContentFieldImpl extends AbstractContentField {

  /** UID for serialization. */
  private static final long serialVersionUID = -2021919603941862430L;

  /**
   * The constructor.
   */
  public ContentFieldImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param id is the {@link #getContentId() id}.
   */
  public ContentFieldImpl(String name, ContentId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractContentClass getParent() {

    return getDeclaringClass();
  }

}
