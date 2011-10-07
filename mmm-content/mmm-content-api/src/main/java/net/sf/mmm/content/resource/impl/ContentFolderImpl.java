/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.impl;

import net.sf.mmm.content.api.ContentClassAnnotation;
import net.sf.mmm.content.api.ContentFieldAnnotation;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.base.AbstractContentResource;
import net.sf.mmm.content.datatype.api.ContentId;

/**
 * This is the abstract base implementation of the {@link ContentFolder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentFolder.CLASS_ID, name = ContentFolder.CLASS_NAME)
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
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   * @param id is the {@link #getContentId() ID}.
   */
  public ContentFolderImpl(String name, ContentFolderImpl parent, ContentId id) {

    super(name, parent, id);
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public ContentFolderImpl(String name, ContentFolderImpl parent) {

    super(name, parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ContentFieldAnnotation(id = 70)
  public ContentFolderImpl getParent() {

    return (ContentFolderImpl) super.getParent();
  }

}
