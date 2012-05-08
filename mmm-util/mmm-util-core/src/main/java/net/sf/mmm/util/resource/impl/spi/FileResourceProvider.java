/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spi;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.base.spi.AbstractDataResourceProvider;

/**
 * This is the implementation of {@link net.sf.mmm.util.resource.api.spi.DataResourceProvider} for
 * {@link FileResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class FileResourceProvider extends AbstractDataResourceProvider<FileResource> {

  /** @see #createResource(ResourceUri) */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public FileResourceProvider() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.fileUtil == null) {
      this.fileUtil = FileUtilImpl.getInstance();
    }
  }

  /**
   * @param fileUtil is the fileUtil to set
   */
  @Inject
  public void setFileUtil(FileUtil fileUtil) {

    getInitializationState().requireNotInitilized();
    this.fileUtil = fileUtil;
  }

  /**
   * {@inheritDoc}
   */
  public String[] getSchemePrefixes() {

    return new String[] { FileResource.SCHEME_PREFIX };
  }

  /**
   * {@inheritDoc}
   */
  public Class<FileResource> getResourceType() {

    return FileResource.class;
  }

  /**
   * {@inheritDoc}
   */
  public FileResource createResource(ResourceUri resourceUri) {

    String path = this.fileUtil.normalizePath(resourceUri.getPath());
    return new FileResource(path);
  }
}
