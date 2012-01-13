/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.base.AbstractContentTypeManager;
import net.sf.mmm.util.contenttype.base.ContentTypeListLoader;

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

    ContentTypeListLoader loader = new ContentTypeListLoader();
    loader.initialize();
    this.rootType = loader.loadXml("classpath:net/sf/mmm/util/contenttype/contenttypes.xml")
        .getRoot();
    addContentTypeRecursively(this.rootType);
  }

  /**
   * This method walks down the tree of {@link ContentType}s recursively and
   * {@link #addContentType(ContentType) adds} them to this manager.
   * 
   * @param contentType is the {@link ContentType} to register recursively (via
   *        its {@link ContentType#getChildren() children}).
   */
  private void addContentTypeRecursively(ContentType contentType) {

    addContentType(contentType);
    for (ContentType child : contentType.getChildren()) {
      addContentTypeRecursively(child);
    }
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
