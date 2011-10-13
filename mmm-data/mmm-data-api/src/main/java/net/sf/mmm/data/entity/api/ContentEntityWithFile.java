/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.entity.api;

import net.sf.mmm.data.api.ContentClassAnnotation;

/**
 * This is the interface for a {@link ContentEntity} that {@link #getFile() has}
 * a {@link ContentFile}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentEntityWithFile.CLASS_ID, isFinal = true)
public abstract interface ContentEntityWithFile extends ContentEntity {

  /**
   * The {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 14;

  /**
   * This method gets the associated file.
   * 
   * @return the {@link ContentFile}.
   */
  ContentFile getFile();

}
