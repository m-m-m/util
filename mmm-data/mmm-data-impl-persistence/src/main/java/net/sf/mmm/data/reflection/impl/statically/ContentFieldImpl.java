/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.impl.statically;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.reflection.AbstractDataClass;
import net.sf.mmm.data.base.reflection.AbstractDataField;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataField.CLASS_ID, title = DataField.CLASS_NAME)
public final class ContentFieldImpl extends AbstractDataField {

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
  public ContentFieldImpl(String name, DataId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataClass getParent() {

    return getDeclaringClass();
  }

}
