/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

/**
 * TODO: this class ...
 * 
 * @author hohwille
 * @since 1.0.0
 */
public abstract interface DataArtistView {

  /**
   * This method determines if this artist is a {@link DataArtistGroupView group} .
   * 
   * @return <code>true</code> if this object is a {@link DataArtistGroupView}.
   */
  boolean isGroup();

}
