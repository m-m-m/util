/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a {@link DataSelection selection} that is chosen
 * from a flat list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionList.CLASS_ID)
public abstract interface DataSelectionList extends DataSelection {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
   * this type.
   */
  int CLASS_ID = 4;

}
