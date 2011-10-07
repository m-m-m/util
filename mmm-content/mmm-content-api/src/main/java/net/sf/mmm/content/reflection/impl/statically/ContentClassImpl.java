/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.impl.statically;

import net.sf.mmm.content.api.ContentClassAnnotation;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.base.AbstractContentClass;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.reflection.api.ContentClass} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentClass.CLASS_ID, name = ContentClass.CLASS_NAME)
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
   * @param name is the {@link #getTitle() name}.
   * @param id is the {@link #getContentId() ID}.
   */
  public ContentClassImpl(String name, ContentId id) {

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
