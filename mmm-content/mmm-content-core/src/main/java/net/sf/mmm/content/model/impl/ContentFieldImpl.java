/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentField.CLASS_ID, name = ContentField.CLASS_NAME)
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
   * @param name is the {@link #getName() name}.
   * @param id is the {@link #getId() id}.
   */
  public ContentFieldImpl(String name, SmartId id) {

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
