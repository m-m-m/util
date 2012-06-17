/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.resource;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import net.sf.mmm.data.api.datatype.Checksum;
import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.api.entity.resource.DataFileView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.datatype.ChecksumImpl;

/**
 * This is the implementation of {@link DataFileView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataFileView.CLASS_TITLE)
@DataClassAnnotation(id = DataFileView.CLASS_ID)
@DiscriminatorValue("" + DataFileView.CLASS_ID)
public class DataFileImpl extends AbstractDataEntityResource implements DataFile {

  /** UID for serialization. */
  private static final long serialVersionUID = 5935203134499898990L;

  /** @see #getFiletype() */
  private String filetype;

  /** @see #getChecksum() */
  private ChecksumImpl checksum;

  /**
   * The constructor.
   */
  public DataFileImpl() {

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
  public void setFiletype(String filetype) {

    this.filetype = filetype;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public MutableBlob getFile() {

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
  public void setChecksum(Checksum checksum) {

    this.checksum = (ChecksumImpl) checksum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataFileView.CLASS_ID;
  }

}
