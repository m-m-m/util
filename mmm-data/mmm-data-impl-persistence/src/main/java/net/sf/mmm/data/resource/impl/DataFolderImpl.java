/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource.impl;

import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.resource.api.ContentFolder;
import net.sf.mmm.data.resource.base.AbstractDataResource;

/**
 * This is the abstract base implementation of the {@link ContentFolder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = ContentFolder.CLASS_ID, title = ContentFolder.CLASS_NAME)
public final class DataFolderImpl extends AbstractDataResource implements ContentFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -5634240442894182641L;

  /**
   * The constructor.
   */
  public DataFolderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getDataClassId() {

    return DataFolder.CLASS_ID;
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getTitle() name}.
   * @param parent is the {@link #getParent() parent}.
   */
  public DataFolderImpl(String name, DataFolderImpl parent) {

    super(name, parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @DataFieldAnnotation(id = 70)
  public DataFolderImpl getParent() {

    return (DataFolderImpl) super.getParent();
  }

}
