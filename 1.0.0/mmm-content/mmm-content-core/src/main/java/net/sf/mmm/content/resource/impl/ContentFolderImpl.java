/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.impl;

import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.base.FieldAnnotation;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.base.AbstractContentResource;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the abstract base implementation of the {@link ContentFolder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentFolder.CLASS_ID, name = ContentFolder.CLASS_NAME)
public final class ContentFolderImpl extends AbstractContentResource implements ContentFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -5634240442894182641L;

  /**
   * The constructor.
   */
  public ContentFolderImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name}.
   * @param parent is the {@link #getParent() parent}.
   * @param id is the {@link #getId() ID}.
   */
  public ContentFolderImpl(String name, ContentFolderImpl parent, SmartId id) {

    super(name, parent, id);
  }

  /**
   * The constructor.
   *
   * @param name is the {@link #getName() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public ContentFolderImpl(String name, ContentFolderImpl parent) {

    super(name, parent);
  }

  /**
   * {@inheritDoc}
   */
  @FieldAnnotation(id = 70)
  public ContentFolderImpl getParent() {

    return (ContentFolderImpl) super.getParent();
  }

}
