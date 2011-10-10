/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.entity.api;

/**
 * This is the interface for a {@link ContentEntity} that {@link #getFile() has}
 * a {@link ContentFile}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface ContentEntityWithFile extends ContentEntity {

  /**
   * This method gets the associated file.
   * 
   * @return the {@link ContentFile}.
   */
  ContentFile getFile();

}
