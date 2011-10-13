/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;

/**
 * This is the interface for a {@link DataObject} that acts as selection.
 * Such selection will allow to choose out of all instances of this
 * {@link net.sf.mmm.data.api.reflection.DataClass type}. This is used for
 * relations between {@link DataObject}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelection.CLASS_ID)
public abstract interface DataSelection extends DataObject {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
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
  @DataFieldAnnotation(id = DataFieldIds.ID_SELECTION_CACHEABLE, isStatic = true, isFinal = true)
  boolean isCacheable();

}
