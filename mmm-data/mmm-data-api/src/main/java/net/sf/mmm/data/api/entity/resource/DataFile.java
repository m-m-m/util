/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.datatype.Blob;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a {@link DataEntity} that represents a file.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFile.CLASS_ID, isFinal = true)
public interface DataFile extends DataResource {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
   * this type.
   */
  int CLASS_ID = 13;

  /**
   * This method gets the mimetype of this file.
   * 
   * @return the mimetype or <code>null</code> if undefined.
   */
  String getMimetype();

  /**
   * This method gets the {@link Blob} representing the actual file content.
   * 
   * @return the {@link Blob}.
   */
  Blob getFile();

}
