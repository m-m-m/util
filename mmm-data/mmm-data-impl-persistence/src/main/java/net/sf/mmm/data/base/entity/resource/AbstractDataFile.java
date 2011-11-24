/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.entity.resource;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.data.api.datatype.Blob;
import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.base.datatype.ChecksumImpl;

/**
 * This is the abstract base implementation of {@link DataFile}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractDataFile extends AbstractDataEntityResource implements DataFile {

  /** UID for serialization. */
  private static final long serialVersionUID = 5935203134499898990L;

  /** @see #getFiletype() */
  private String filetype;

  /** @see #getChecksum() */
  private ChecksumImpl checksum;

  /**
   * The constructor.
   */
  public AbstractDataFile() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getFiletype() {

    return this.filetype;
  }

  /**
   * @param filetype is the filetype to set
   */
  protected void setFiletype(String filetype) {

    this.filetype = filetype;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public Blob getFile() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public ChecksumImpl getChecksum() {

    return this.checksum;
  }

  /**
   * @param checksum is the checksum to set
   */
  protected void setChecksum(ChecksumImpl checksum) {

    this.checksum = checksum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public long getDataClassId() {

    return DataFile.CLASS_ID;
  }

}
