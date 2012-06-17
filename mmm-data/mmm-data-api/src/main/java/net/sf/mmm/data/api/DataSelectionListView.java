/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;

/**
 * This is the interface for a {@link DataSelectionView selection} that is
 * chosen from a flat list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionListView.CLASS_ID, title = DataSelectionListView.CLASS_TITLE)
public abstract interface DataSelectionListView extends DataSelectionView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_SELECTIONLIST;

  /**
   * The {@link DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataSelectionList";

}
