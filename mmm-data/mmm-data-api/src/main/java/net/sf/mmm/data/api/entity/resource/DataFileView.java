/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.datatype.Blob;
import net.sf.mmm.data.api.datatype.Checksum;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataEntityResourceView} that represents a
 * file.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFileView.CLASS_ID, title = DataFileView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataFileView extends DataEntityResourceView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 13;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataFile";

  /**
   * This method gets the mimetype of this file.
   * 
   * @return the mimetype or <code>null</code> if undefined.
   */
  String getFiletype();

  /**
   * This method gets the {@link Blob} representing the actual file content.
   * 
   * @return the {@link Blob}.
   */
  Blob getFile();

  /**
   * This method gets the checksum of the {@link #getFile() file}.
   * 
   * @return the {@link Checksum}.
   */
  Checksum getChecksum();

}
