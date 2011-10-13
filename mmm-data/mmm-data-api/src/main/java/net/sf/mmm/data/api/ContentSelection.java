/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This is the interface for a {@link ContentObject} that acts as selection.
 * Such selection will allow to choose out of all instances of this
 * {@link net.sf.mmm.data.reflection.api.ContentClass type}. This is used for
 * relations between {@link ContentObject}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentSelection.CLASS_ID)
public abstract interface ContentSelection extends ContentObject {

  /**
   * The {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 3;

  /**
   * This method determines if the entire selection options of the concrete
   * implementing type should be cached by the system in main memory. This
   * option has to be be static for a concrete type.
   * 
   * @return <code>true</code> if the selections shall be cached,
   *         <code>false</code> otherwise.
   */
  @ContentFieldAnnotation(id = ContentFieldIds.ID_SELECTION_CACHEABLE, isStatic = true, isFinal = true)
  boolean isCacheable();

}
