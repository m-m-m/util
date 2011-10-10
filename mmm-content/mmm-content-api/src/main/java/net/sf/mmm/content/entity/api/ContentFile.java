/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.entity.api;

import net.sf.mmm.content.api.ContentClassAnnotation;
import net.sf.mmm.content.datatype.api.Blob;

/**
 * This is the interface for a {@link ContentEntity} that represents a file.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentFile.CLASS_ID, isFinal = true)
public interface ContentFile extends ContentEntity {

  /**
   * The {@link net.sf.mmm.content.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.content.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 10;

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
