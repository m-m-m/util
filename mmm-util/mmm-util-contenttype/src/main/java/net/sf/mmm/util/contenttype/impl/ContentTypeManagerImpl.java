/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import net.sf.mmm.util.contenttype.base.AbstractContentTypeManager;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.contenttype.api.ContentTypeManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentTypeManagerImpl extends AbstractContentTypeManager<ContentTypeImpl> {

  /** @see #getRootType() */
  private ContentTypeImpl rootType;

  /** @see #getTechnicalRootType() */
  private ContentTypeImpl technicalRootType;

  /**
   * The constructor.
   */
  public ContentTypeManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();

    this.rootType = null;
    this.technicalRootType = null;
  }

  /**
   * {@inheritDoc}
   */
  public ContentTypeImpl getRootType() {

    return this.rootType;
  }

  /**
   * {@inheritDoc}
   */
  public ContentTypeImpl getTechnicalRootType() {

    return this.technicalRootType;
  }

}
