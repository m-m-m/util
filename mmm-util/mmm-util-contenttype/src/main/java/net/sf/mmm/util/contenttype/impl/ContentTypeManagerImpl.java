/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.base.AbstractContentTypeManager;
import net.sf.mmm.util.contenttype.base.ContentTypeLoader;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.contenttype.api.ContentTypeManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentTypeManagerImpl extends AbstractContentTypeManager {

  /** @see #getRootType() */
  private ContentType rootType;

  /** @see #getTechnicalRootType() */
  private ContentType technicalRootType;

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

    this.technicalRootType = null;

    ContentTypeLoader loader = new ContentTypeLoader();
    loader.initialize();
    this.rootType = loader.loadXml("net/sf/mmm/util/contenttype/root.xml");
  }

  /**
   * {@inheritDoc}
   */
  public ContentType getRootType() {

    return this.rootType;
  }

  /**
   * {@inheritDoc}
   */
  public ContentType getTechnicalRootType() {

    return this.technicalRootType;
  }

}
