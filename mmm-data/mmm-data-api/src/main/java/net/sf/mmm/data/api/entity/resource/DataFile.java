/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.datatype.Checksum;
import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataFileView file}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFileView.CLASS_ID, title = DataFileView.CLASS_TITLE)
public interface DataFile extends DataFileView {

  /**
   * {@inheritDoc}
   */
  MutableBlob getFile();

  /**
   * This method sets the {@link #getChecksum() checksum}.
   * 
   * @param checksum is the {@link Checksum} to set.
   */
  void setChecksum(Checksum checksum);

  /**
   * This method sets the {@link #getFiletype() filetype}.
   * 
   * @param filetype is the filetype to set.
   */
  void setFiletype(String filetype);

}
