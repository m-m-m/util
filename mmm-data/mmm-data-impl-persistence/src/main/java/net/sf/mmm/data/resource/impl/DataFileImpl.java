/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.resource.impl;

import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.resource.api.ContentFile;
import net.sf.mmm.data.resource.base.AbstractDataDocument;

/**
 * This is the implementation of the {@link ContentFile} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataFileImpl extends AbstractDataDocument implements ContentFile {

  /** UID for serialization. */
  private static final long serialVersionUID = -6514585506345939308L;

  /** @see #getBlob() */
  private MutableBlob blob;

  /**
   * The constructor.
   */
  public DataFileImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getDataClassId() {

    return DataFile.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public MutableBlob getBlob() {

    return this.blob;
  }

  /**
   * This method sets the {@link #getBlob() blob}.
   * 
   * @param blob is the blob to set.
   */
  protected void setBlob(MutableBlob blob) {

    this.blob = blob;
  }

}
