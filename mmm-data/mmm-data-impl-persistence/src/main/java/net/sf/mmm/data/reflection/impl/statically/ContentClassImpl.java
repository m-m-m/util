/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.impl.statically;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.data.base.reflection.AbstractDataClass;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.reflection.DataClass} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataClass.CLASS_ID, title = DataClass.CLASS_NAME)
public final class ContentClassImpl extends AbstractDataClass {

  /** UID for serialization. */
  private static final long serialVersionUID = -6926223109885122995L;

  /**
   * The constructor.
   */
  public ContentClassImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param id is the {@link #getContentId() ID}.
   */
  public ContentClassImpl(String name, DataId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractDataObject getParent() {

    AbstractDataClass parentClass = getSuperClass();
    if (parentClass != null) {
      return parentClass;
    } else {
      // TODO
      return null;
    }
  }

}
