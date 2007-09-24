/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentClass} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentClass.CLASS_ID, name = ContentClass.CLASS_NAME)
public final class ContentClassImpl extends AbstractContentClass {

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
   * @param name is the {@link #getName() name}.
   * @param id is the {@link #getId() ID}.
   */
  public ContentClassImpl(String name, SmartId id) {

    super(name, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractContentObject getParent() {
  
    AbstractContentClass parentClass = getSuperClass();
    if (parentClass != null) {
      return parentClass;      
    } else {
      // TODO
      return null;
    }
  }

}
