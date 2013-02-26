/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.datatype.Checksum;
import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataEntityResource} that represents a file.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFile.CLASS_ID, title = DataFile.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataFile extends DataEntityResource {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 13;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataFile";

  /**
   * This method gets the {@link net.sf.mmm.data.api.datatype.Blob} representing the actual file content.
   * 
   * @return the {@link MutableBlob}.
   */
  MutableBlob getFile();

  /**
   * This method gets the checksum of the {@link #getFile() file}.
   * 
   * @return the {@link Checksum}.
   */
  Checksum getChecksum();

  /**
   * This method sets the {@link #getChecksum() checksum}.
   * 
   * @param checksum is the {@link Checksum} to set.
   */
  void setChecksum(Checksum checksum);

  /**
   * This method gets the mimetype of this file.
   * 
   * @return the mimetype or <code>null</code> if undefined.
   */
  String getFiletype();

  /**
   * This method sets the {@link #getFiletype() filetype}.
   * 
   * @param filetype is the filetype to set.
   */
  void setFiletype(String filetype);

}
